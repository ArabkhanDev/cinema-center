package group.aist.cinema.controller;

import group.aist.cinema.dto.common.HallDTO;
import group.aist.cinema.model.base.BaseResponse;
import group.aist.cinema.service.HallService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HallController.class)
class HallControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HallService hallService;

    private HallDTO hallDTO;
    private Page<HallDTO> hallDTOPage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        hallDTO = new HallDTO();
        hallDTO.setId(1L);

        hallDTOPage = new PageImpl<>(Collections.singletonList(hallDTO));
    }

    @Test
    void getAllHalls() throws Exception {
        when(hallService.getAllHalls(any(Pageable.class))).thenReturn(hallDTOPage);

        mockMvc.perform(get("/v1/api/halls")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].id").value(hallDTO.getId()));
    }

    @Test
    void getHallById() throws Exception {
        when(hallService.getHallById(anyLong())).thenReturn(hallDTO);

        mockMvc.perform(get("/v1/api/halls/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(hallDTO.getId()));
    }

    @Test
    void createHall() throws Exception {
        when(hallService.createHall(any(HallDTO.class))).thenReturn(hallDTO);

        mockMvc.perform(post("/v1/api/halls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(hallDTO.getId()));
    }

    @Test
    void updateHall() throws Exception {
        when(hallService.updateHall(anyLong(), any(HallDTO.class))).thenReturn(hallDTO);

        mockMvc.perform(put("/v1/api/halls/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(hallDTO.getId()));
    }

    @Test
    void deleteHall() throws Exception {
        mockMvc.perform(delete("/v1/api/halls/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
