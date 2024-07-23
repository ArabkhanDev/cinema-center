package group.aist.cinema.controller;

import group.aist.cinema.dto.common.DubbingLanguageDTO;
import group.aist.cinema.model.base.BaseResponse;
import group.aist.cinema.service.DubbingLanguageService;
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
class DubbingLanguageControllerTest {

    @Mock
    private DubbingLanguageService dubbingLanguageService;

    @InjectMocks
    private DubbingLanguageController dubbingLanguageController;

    private DubbingLanguageDTO dubbingLanguageDTO;
    private Page<DubbingLanguageDTO> dubbingLanguageDTOPage;

    @BeforeEach
    void setUp() {
        dubbingLanguageDTO = new DubbingLanguageDTO();
        dubbingLanguageDTO.setId(1L);

        dubbingLanguageDTOPage = new PageImpl<>(Collections.singletonList(dubbingLanguageDTO));
    }

    @Test
    void getDubbingLanguages() {
        when(dubbingLanguageService.getAllDubbingLanguages(any(Pageable.class))).thenReturn(dubbingLanguageDTOPage);

        BaseResponse<Page<DubbingLanguageDTO>> response = dubbingLanguageController.getDubbingLanguages(null);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(1, response.getData().getContent().size());
        assertEquals(dubbingLanguageDTO.getId(), response.getData().getContent().get(0).getId());
    }

    @Test
    void getDubbingLanguageById() {
        when(dubbingLanguageService.getDubbingLanguage(anyLong())).thenReturn(dubbingLanguageDTO);

        BaseResponse<DubbingLanguageDTO> response = dubbingLanguageController.dubbingLanguageById(1L);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(dubbingLanguageDTO.getId(), response.getData().getId());
    }

    @Test
    void createDubbingLanguage() {
        when(dubbingLanguageService.createDubbingLanguage(any(DubbingLanguageDTO.class))).thenReturn(dubbingLanguageDTO);

        BaseResponse<DubbingLanguageDTO> response = dubbingLanguageController.createDubbingLanguage(dubbingLanguageDTO);

        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertEquals(dubbingLanguageDTO.getId(), response.getData().getId());
    }

    @Test
    void updateDubbingLanguage() {
        when(dubbingLanguageService.updateDubbingLanguage(anyLong(), any(DubbingLanguageDTO.class))).thenReturn(dubbingLanguageDTO);

        BaseResponse<DubbingLanguageDTO> response = dubbingLanguageController.updateDubbingLanguage(dubbingLanguageDTO,1L);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(dubbingLanguageDTO.getId(), response.getData().getId());
    }

    @Test
    void deleteDubbingLanguage() {
        BaseResponse<Void> response = dubbingLanguageController.deleteDubbingLanguage(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatus());
    }
}
