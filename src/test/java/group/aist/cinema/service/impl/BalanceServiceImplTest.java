package group.aist.cinema.service.impl;

import group.aist.cinema.dto.common.BalanceDTO;
import group.aist.cinema.mapper.BalanceMapper;
import group.aist.cinema.model.Balance;
import group.aist.cinema.repository.BalanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class BalanceServiceImplTest {

    @InjectMocks
    private BalanceServiceImpl balanceService;

    @Mock
    private BalanceRepository balanceRepository;

    @Mock
    private BalanceMapper balanceMapper;

    private Balance balance;
    private BalanceDTO balanceDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        balance = new Balance();
        balance.setId(1L);
        balanceDTO = new BalanceDTO();
        balanceDTO.setId(1L);
    }

    @Test
    void getAllBalances() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Balance> balancePage = new PageImpl<>(List.of(balance));
        when(balanceRepository.findAll(pageable)).thenReturn(balancePage);
        when(balanceMapper.toDTO(balance)).thenReturn(balanceDTO);

        Page<BalanceDTO> result = balanceService.getAllBalances(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(balanceRepository, times(1)).findAll(pageable);
    }

    @Test
    void getBalanceById() {
        when(balanceRepository.findById(anyLong())).thenReturn(Optional.of(balance));
        when(balanceMapper.toDTO(any(Balance.class))).thenReturn(balanceDTO);

        BalanceDTO result = balanceService.getBalanceById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(balanceRepository, times(1)).findById(anyLong());
    }

    @Test
    void getBalanceById_NotFound() {
        when(balanceRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            balanceService.getBalanceById(1L);
        });

        String expectedMessage = "Balance not found with id 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void createBalance() {
        when(balanceMapper.toEntity(any(BalanceDTO.class))).thenReturn(balance);
        when(balanceRepository.save(any(Balance.class))).thenReturn(balance);
        when(balanceMapper.toDTO(any(Balance.class))).thenReturn(balanceDTO);

        BalanceDTO result = balanceService.createBalance(balanceDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(balanceRepository, times(1)).save(any(Balance.class));
    }

    @Test
    void updateBalance() {
        when(balanceRepository.findById(anyLong())).thenReturn(Optional.of(balance));
        when(balanceRepository.save(any(Balance.class))).thenReturn(balance);
        when(balanceMapper.toDTO(any(Balance.class))).thenReturn(balanceDTO);

        BalanceDTO result = balanceService.updateBalance(1L, balanceDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(balanceRepository, times(1)).findById(anyLong());
        verify(balanceRepository, times(1)).save(any(Balance.class));
    }

    @Test
    void updateBalance_NotFound() {
        when(balanceRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            balanceService.updateBalance(1L, balanceDTO);
        });

        String expectedMessage = "Balance not found with id 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void deleteBalance() {
        doNothing().when(balanceRepository).deleteById(anyLong());

        balanceService.deleteBalance(1L);

        verify(balanceRepository, times(1)).deleteById(anyLong());
    }
}
