package group.aist.cinema.controller;

import group.aist.cinema.dto.common.SectorDTO;
import group.aist.cinema.model.base.BaseResponse;
import group.aist.cinema.service.SectorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/sectors")
@RequiredArgsConstructor
public class SectorController {

    private final SectorService sectorService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public BaseResponse<Page<SectorDTO>> getAllSectors(Pageable pageable) {
        return BaseResponse.success(sectorService.getAllSectors(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public BaseResponse<SectorDTO> getSectorById(@PathVariable Long id) {
        return BaseResponse.success(sectorService.getSectorById(id));
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<SectorDTO> createSector(@Valid @RequestBody SectorDTO sectorDTO) {
        return BaseResponse.created(sectorService.createSector(sectorDTO));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<SectorDTO> updateSector(@PathVariable Long id,
                                                @Valid @RequestBody SectorDTO sectorDTO) {
        return BaseResponse.success(sectorService.updateSector(id, sectorDTO));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<Void> deleteSector(@PathVariable Long id) {
        sectorService.deleteSector(id);
        return BaseResponse.noContent();
    }

}
