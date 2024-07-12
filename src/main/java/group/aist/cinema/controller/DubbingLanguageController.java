package group.aist.cinema.controller;

import group.aist.cinema.dto.common.DubbingLanguageDTO;
import group.aist.cinema.service.DubbingLanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/api/dubbing-languages")
public class DubbingLanguageController {
    private final DubbingLanguageService dubbingLanguageService;
    @GetMapping
    public Page<DubbingLanguageDTO> getDubbingLanguages(Pageable pageable) {
        return dubbingLanguageService.getAllDubbingLanguages(pageable);
    }

    @GetMapping("/{id}")
    public DubbingLanguageDTO dubbingLanguageDTOById(@PathVariable Long id) {
        return dubbingLanguageService.getDubbingLanguage(id);
    }

    @PostMapping
    public DubbingLanguageDTO createDubbingLanguage(@RequestBody DubbingLanguageDTO dubbingLanguageDTO) {
        return dubbingLanguageService.createDubbingLanguage(dubbingLanguageDTO);
    }

    @PutMapping("/{id}")
    public DubbingLanguageDTO updateDubbingLanguage(@RequestBody DubbingLanguageDTO dubbingLanguageDTO, @PathVariable Long id) {
        return dubbingLanguageService.updateDubbingLanguage(id, dubbingLanguageDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteDubbingLanguage(@PathVariable Long id) {
        dubbingLanguageService.deleteDubbingLanguage(id);
    }
}
