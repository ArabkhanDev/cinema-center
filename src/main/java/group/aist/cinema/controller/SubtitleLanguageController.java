package group.aist.cinema.controller;

import group.aist.cinema.dto.common.SubtitleLanguageDTO;
import group.aist.cinema.service.SubtitleLanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/subtitle-languages")
@RequiredArgsConstructor
public class SubtitleLanguageController {

    private final SubtitleLanguageService subtitleLanguageService;

    @GetMapping
    public Page<SubtitleLanguageDTO> getAllSubtitleLanguages(Pageable pageable) {
        return subtitleLanguageService.getAllSubtitleLanguages(pageable);
    }

    @GetMapping("/{id}")
    public SubtitleLanguageDTO getSubtitleLanguageById(@PathVariable Long id) {
        return subtitleLanguageService.getSubtitleLanguageById(id);
    }


    @PostMapping
    public SubtitleLanguageDTO createSubtitleLanguage(@RequestBody SubtitleLanguageDTO subtitleLanguageDTO) {
        return subtitleLanguageService.createSubtitleLanguage(subtitleLanguageDTO);
    }

    @PutMapping("/{id}")
    public SubtitleLanguageDTO updateSubtitleLanguage(@PathVariable Long id, @RequestBody SubtitleLanguageDTO subtitleLanguageDTO) {
        return subtitleLanguageService.updateSubtitleLanguage(id, subtitleLanguageDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteSubtitleLanguage(@PathVariable Long id) {
        subtitleLanguageService.deleteSubtitleLanguage(id);
    }
}
