package group.aist.cinema.service;

import group.aist.cinema.dto.common.SubtitleLanguageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SubtitleLanguageService {

    Page<SubtitleLanguageDTO> getAllSubtitleLanguages(Pageable pageable);

    SubtitleLanguageDTO getSubtitleLanguageById(Long id);

    SubtitleLanguageDTO createSubtitleLanguage(SubtitleLanguageDTO subtitleLanguageDto);

    SubtitleLanguageDTO updateSubtitleLanguage(Long id, SubtitleLanguageDTO subtitleLanguageDto);

    void deleteSubtitleLanguage(Long id);
}
