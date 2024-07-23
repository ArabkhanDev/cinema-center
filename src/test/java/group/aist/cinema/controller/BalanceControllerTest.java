package group.aist.cinema.controller;

import group.aist.cinema.dto.common.BalanceDTO;
import group.aist.cinema.model.base.BaseResponse;
import group.aist.cinema.service.BalanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BalanceControllerTest {

    @Mock
    private BalanceService balanceService;

    @InjectMocks
    private BalanceController balanceController;

    private BalanceDTO balanceDTO;
    private Page<BalanceDTO> balanceDTOPage;

    @BeforeEach
    void setUp() {
        balanceDTO = new BalanceDTO();
        balanceDTO.setId(1L);

        balanceDTOPage = new PageImpl<>(Collections.singletonList(balanceDTO));
    }

    @Test
    void getAllBalances() {
        when(balanceService.getAllBalances(any(Pageable.class))).thenReturn(balanceDTOPage);

        BaseResponse<Page<BalanceDTO>> response = balanceController.getAllBalances(null);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(1, response.getData().getContent().size());
        assertEquals(balanceDTO.getId(), response.getData().getContent().get(0).getId());
    }

    @Test
    void getBalanceById() {
        when(balanceService.getBalanceById(anyLong())).thenReturn(balanceDTO);

        BaseResponse<BalanceDTO> response = balanceController.getBalanceById(1L);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(balanceDTO.getId(), response.getData().getId());
    }

    @Test
    void createBalance() {
        when(balanceService.createBalance(any(BalanceDTO.class))).thenReturn(balanceDTO);

        BaseResponse<BalanceDTO> response = balanceController.createBalance(balanceDTO);

        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertEquals(balanceDTO.getId(), response.getData().getId());
    }

    @Test
    void updateBalance() {
        when(balanceService.updateBalance(anyLong(), any(BalanceDTO.class))).thenReturn(balanceDTO);

        BaseResponse<BalanceDTO> response = balanceController.updateBalance(1L, balanceDTO);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(balanceDTO.getId(), response.getData().getId());
    }

    @Test
    void deleteBalance() {
        BaseResponse<Void> response = balanceController.deleteBalance(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatus());
    }
}
