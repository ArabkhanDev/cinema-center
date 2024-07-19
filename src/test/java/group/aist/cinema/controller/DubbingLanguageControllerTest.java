package group.aist.cinema.controller;

import group.aist.cinema.dto.common.DubbingLanguageDTO;
import group.aist.cinema.model.base.BaseResponse;
import group.aist.cinema.service.DubbingLanguageService;
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

@WebMvcTest(DubbingLanguageController.class)
class DubbingLanguageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DubbingLanguageService dubbingLanguageService;

    private DubbingLanguageDTO dubbingLanguageDTO;
    private Page<DubbingLanguageDTO> dubbingLanguageDTOPage;

    @BeforeEach
    void setUp() {
        dubbingLanguageDTO = new DubbingLanguageDTO();
        dubbingLanguageDTO.setId(1L);

        dubbingLanguageDTOPage = new PageImpl<>(Collections.singletonList(dubbingLanguageDTO));
    }

    @Test
    void getDubbingLanguages() throws Exception {
        when(dubbingLanguageService.getAllDubbingLanguages(any(Pageable.class))).thenReturn(dubbingLanguageDTOPage);

        mockMvc.perform(get("/v1/api/dubbing-languages")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].id").value(dubbingLanguageDTO.getId()));
    }

    @Test
    void getDubbingLanguageById() throws Exception {
        when(dubbingLanguageService.getDubbingLanguage(anyLong())).thenReturn(dubbingLanguageDTO);

        mockMvc.perform(get("/v1/api/dubbing-languages/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(dubbingLanguageDTO.getId()));
    }

    @Test
    void createDubbingLanguage() throws Exception {
        when(dubbingLanguageService.createDubbingLanguage(any(DubbingLanguageDTO.class))).thenReturn(dubbingLanguageDTO);

        mockMvc.perform(post("/v1/api/dubbing-languages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(dubbingLanguageDTO.getId()));
    }

    @Test
    void updateDubbingLanguage() throws Exception {
        when(dubbingLanguageService.updateDubbingLanguage(anyLong(), any(DubbingLanguageDTO.class))).thenReturn(dubbingLanguageDTO);

        mockMvc.perform(put("/v1/api/dubbing-languages/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(dubbingLanguageDTO.getId()));
    }

    @Test
    void deleteDubbingLanguage() throws Exception {
        mockMvc.perform(delete("/v1/api/dubbing-languages/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
