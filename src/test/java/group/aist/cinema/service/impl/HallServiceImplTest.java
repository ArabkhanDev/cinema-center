package group.aist.cinema.service.impl;

import group.aist.cinema.dto.common.HallDTO;
import group.aist.cinema.mapper.HallMapper;
import group.aist.cinema.model.Hall;
import group.aist.cinema.repository.HallRepository;
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

class HallServiceImplTest {

    @InjectMocks
    private HallServiceImpl hallService;

    @Mock
    private HallRepository hallRepository;

    @Mock
    private HallMapper hallMapper;

    private Hall hall;
    private HallDTO hallDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        hall = new Hall();
        hall.setId(1L);
        hallDTO = new HallDTO();
        hallDTO.setId(1L);
    }

    @Test
    void getAllHalls() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Hall> hallPage = new PageImpl<>(List.of(hall));
        when(hallRepository.findAll(pageable)).thenReturn(hallPage);
        when(hallMapper.toDTO(any(Hall.class))).thenReturn(hallDTO);

        Page<HallDTO> result = hallService.getAllHalls(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(hallRepository, times(1)).findAll(pageable);
    }

    @Test
    void getHallById() {
        when(hallRepository.findById(anyLong())).thenReturn(Optional.of(hall));
        when(hallMapper.toDTO(any(Hall.class))).thenReturn(hallDTO);

        HallDTO result = hallService.getHallById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(hallRepository, times(1)).findById(anyLong());
    }

    @Test
    void getHallById_NotFound() {
        when(hallRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            hallService.getHallById(1L);
        });

        String expectedMessage = "Hall not found with id 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void createHall() {
        when(hallMapper.toEntity(any(HallDTO.class))).thenReturn(hall);
        when(hallRepository.save(any(Hall.class))).thenReturn(hall);
        when(hallMapper.toDTO(any(Hall.class))).thenReturn(hallDTO);

        HallDTO result = hallService.createHall(hallDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(hallRepository, times(1)).save(any(Hall.class));
    }

    @Test
    void updateHall() {
        when(hallRepository.findById(anyLong())).thenReturn(Optional.of(hall));
        when(hallRepository.save(any(Hall.class))).thenReturn(hall);
        when(hallMapper.toDTO(any(Hall.class))).thenReturn(hallDTO);

        HallDTO result = hallService.updateHall(1L, hallDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(hallRepository, times(1)).findById(anyLong());
        verify(hallRepository, times(1)).save(any(Hall.class));
    }

    @Test
    void updateHall_NotFound() {
        when(hallRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            hallService.updateHall(1L, hallDTO);
        });

        String expectedMessage = "Hall not found with id 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void deleteHall() {
        doNothing().when(hallRepository).deleteById(anyLong());

        hallService.deleteHall(1L);

        verify(hallRepository, times(1)).deleteById(anyLong());
    }
}