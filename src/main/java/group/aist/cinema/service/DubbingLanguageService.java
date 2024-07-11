package group.aist.cinema.service;

import group.aist.cinema.dto.common.DubbingLanguageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DubbingLanguageService {

    Page<DubbingLanguageDTO> getAllDubbingLanguages(Pageable pageable);

    DubbingLanguageDTO getDubbingLanguage(Long id);

    DubbingLanguageDTO createDubbingLanguage(DubbingLanguageDTO dubbingLanguage);

    DubbingLanguageDTO updateDubbingLanguage(Long id, DubbingLanguageDTO dubbingLanguage);

    void deleteDubbingLanguage(Long id);
}
