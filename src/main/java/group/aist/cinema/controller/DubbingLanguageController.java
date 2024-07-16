package group.aist.cinema.controller;

import group.aist.cinema.dto.common.DubbingLanguageDTO;
import group.aist.cinema.model.base.BaseResponse;
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
    public BaseResponse<Page<DubbingLanguageDTO>> getDubbingLanguages(Pageable pageable) {
        return BaseResponse.success(dubbingLanguageService.getAllDubbingLanguages(pageable));
    }

    @GetMapping("/{id}")
    public BaseResponse<DubbingLanguageDTO> dubbingLanguageDTOById(@PathVariable Long id) {
        return BaseResponse.success(dubbingLanguageService.getDubbingLanguage(id));
    }

    @PostMapping
    public BaseResponse<DubbingLanguageDTO> createDubbingLanguage(@RequestBody DubbingLanguageDTO dubbingLanguageDTO) {
        return BaseResponse.created(dubbingLanguageService.createDubbingLanguage(dubbingLanguageDTO));
    }

    @PutMapping("/{id}")
    public BaseResponse<DubbingLanguageDTO> updateDubbingLanguage(@RequestBody DubbingLanguageDTO dubbingLanguageDTO, @PathVariable Long id) {
        return BaseResponse.success(dubbingLanguageService.updateDubbingLanguage(id, dubbingLanguageDTO));
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteDubbingLanguage(@PathVariable Long id) {
        dubbingLanguageService.deleteDubbingLanguage(id);
        return BaseResponse.noContent();
    }
}
