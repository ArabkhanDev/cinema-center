package group.aist.cinema.controller;

import group.aist.cinema.dto.common.SubtitleLanguageDTO;
import group.aist.cinema.model.base.BaseResponse;
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
    public BaseResponse<Page<SubtitleLanguageDTO>> getAllSubtitleLanguages(Pageable pageable) {
        return BaseResponse.success(subtitleLanguageService.getAllSubtitleLanguages(pageable));
    }

    @GetMapping("/{id}")
    public BaseResponse<SubtitleLanguageDTO> getSubtitleLanguageById(@PathVariable Long id) {
        return BaseResponse.success(subtitleLanguageService.getSubtitleLanguageById(id));
    }


    @PostMapping
    public BaseResponse<SubtitleLanguageDTO> createSubtitleLanguage(@RequestBody SubtitleLanguageDTO subtitleLanguageDTO) {
        return BaseResponse.success(subtitleLanguageService.createSubtitleLanguage(subtitleLanguageDTO));
    }

    @PutMapping("/{id}")
    public BaseResponse<SubtitleLanguageDTO> updateSubtitleLanguage(@PathVariable Long id, @RequestBody SubtitleLanguageDTO subtitleLanguageDTO) {
        return BaseResponse.success(subtitleLanguageService.updateSubtitleLanguage(id, subtitleLanguageDTO));
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteSubtitleLanguage(@PathVariable Long id) {
        subtitleLanguageService.deleteSubtitleLanguage(id);
        return BaseResponse.noContent();
    }
}
