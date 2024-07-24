package group.aist.cinema.service.impl;

import group.aist.cinema.dto.common.DubbingLanguageDTO;
import group.aist.cinema.mapper.DubbingLanguageMapper;
import group.aist.cinema.model.DubbingLanguage;
import group.aist.cinema.repository.DubbingLanguageRepository;
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

class DubbingLanguageImplTest {

    @InjectMocks
    private DubbingLanguageImpl dubbingLanguageService;

    @Mock
    private DubbingLanguageRepository dubbingLanguageRepository;

    @Mock
    private DubbingLanguageMapper dubbingLanguageMapper;

    private DubbingLanguage dubbingLanguage;
    private DubbingLanguageDTO dubbingLanguageDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dubbingLanguage = new DubbingLanguage();
        dubbingLanguage.setId(1L);
        dubbingLanguageDTO = new DubbingLanguageDTO();
        dubbingLanguageDTO.setId(1L);
    }

    @Test
    void getAllDubbingLanguages() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<DubbingLanguage> dubbingLanguagePage = new PageImpl<>(List.of(dubbingLanguage));
        when(dubbingLanguageRepository.findAll(pageable)).thenReturn(dubbingLanguagePage);
        when(dubbingLanguageMapper.toDTO(dubbingLanguage)).thenReturn(dubbingLanguageDTO);

        Page<DubbingLanguageDTO> result = dubbingLanguageService.getAllDubbingLanguages(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(dubbingLanguageRepository, times(1)).findAll(pageable);
    }

    @Test
    void getDubbingLanguage() {
        when(dubbingLanguageRepository.findById(anyLong())).thenReturn(Optional.of(dubbingLanguage));
        when(dubbingLanguageMapper.toDTO(any(DubbingLanguage.class))).thenReturn(dubbingLanguageDTO);

        DubbingLanguageDTO result = dubbingLanguageService.getDubbingLanguage(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(dubbingLanguageRepository, times(1)).findById(anyLong());
    }

    @Test
    void getDubbingLanguage_NotFound() {
        when(dubbingLanguageRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dubbingLanguageService.getDubbingLanguage(1L);
        });

        String expectedMessage = "Dubbing language not found with id 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void createDubbingLanguage() {
        when(dubbingLanguageMapper.toEntity(any(DubbingLanguageDTO.class))).thenReturn(dubbingLanguage);
        when(dubbingLanguageRepository.save(any(DubbingLanguage.class))).thenReturn(dubbingLanguage);
        when(dubbingLanguageMapper.toDTO(any(DubbingLanguage.class))).thenReturn(dubbingLanguageDTO);

        DubbingLanguageDTO result = dubbingLanguageService.createDubbingLanguage(dubbingLanguageDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(dubbingLanguageRepository, times(1)).save(any(DubbingLanguage.class));
    }

    @Test
    void updateDubbingLanguage() {
        when(dubbingLanguageRepository.findById(anyLong())).thenReturn(Optional.of(dubbingLanguage));
        when(dubbingLanguageRepository.save(any(DubbingLanguage.class))).thenReturn(dubbingLanguage);
        when(dubbingLanguageMapper.toDTO(any(DubbingLanguage.class))).thenReturn(dubbingLanguageDTO);

        DubbingLanguageDTO result = dubbingLanguageService.updateDubbingLanguage(1L, dubbingLanguageDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(dubbingLanguageRepository, times(1)).findById(anyLong());
        verify(dubbingLanguageRepository, times(1)).save(any(DubbingLanguage.class));
    }

    @Test
    void updateDubbingLanguage_NotFound() {
        when(dubbingLanguageRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dubbingLanguageService.updateDubbingLanguage(1L, dubbingLanguageDTO);
        });

        String expectedMessage = "Dubbing language not found with id 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void deleteDubbingLanguage() {
        doNothing().when(dubbingLanguageRepository).deleteById(anyLong());

        dubbingLanguageService.deleteDubbingLanguage(1L);

        verify(dubbingLanguageRepository, times(1)).deleteById(anyLong());
    }
}
