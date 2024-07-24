package group.aist.cinema.controller;

import group.aist.cinema.dto.common.HallDTO;
import group.aist.cinema.model.base.BaseResponse;
import group.aist.cinema.service.HallService;
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
class HallControllerTest {

    @Mock
    private HallService hallService;

    @InjectMocks
    private HallController hallController;

    private HallDTO hallDTO;
    private Page<HallDTO> hallDTOPage;

    @BeforeEach
    void setUp() {
        hallDTO = new HallDTO();
        hallDTO.setId(1L);

        hallDTOPage = new PageImpl<>(Collections.singletonList(hallDTO));
    }

    @Test
    void getAllHalls() {
        when(hallService.getAllHalls(any(Pageable.class))).thenReturn(hallDTOPage);

        BaseResponse<Page<HallDTO>> response = hallController.getAllHalls(null);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(1, response.getData().getContent().size());
        assertEquals(hallDTO.getId(), response.getData().getContent().get(0).getId());
    }

    @Test
    void getHallById() {
        when(hallService.getHallById(anyLong())).thenReturn(hallDTO);

        BaseResponse<HallDTO> response = hallController.getHallById(1L);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(hallDTO.getId(), response.getData().getId());
    }

    @Test
    void createHall() {
        when(hallService.createHall(any(HallDTO.class))).thenReturn(hallDTO);

        HallDTO newHallDTO = new HallDTO();
        newHallDTO.setId(1L);

        BaseResponse<HallDTO> response = hallController.createHall(newHallDTO);

        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertEquals(hallDTO.getId(), response.getData().getId());
    }

    @Test
    void updateHall() {
        when(hallService.updateHall(anyLong(), any(HallDTO.class))).thenReturn(hallDTO);

        HallDTO updatedHallDTO = new HallDTO();
        updatedHallDTO.setId(1L);

        BaseResponse<HallDTO> response = hallController.updateHall(1L, updatedHallDTO);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(hallDTO.getId(), response.getData().getId());
    }

    @Test
    void deleteHall() {
        BaseResponse<Void> response = hallController.deleteHall(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatus());
    }
}
