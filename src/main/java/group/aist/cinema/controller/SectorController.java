package group.aist.cinema.controller;

import group.aist.cinema.dto.common.SectorDTO;
import group.aist.cinema.model.base.BaseResponse;
import group.aist.cinema.service.SectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/sectors")
@RequiredArgsConstructor
public class SectorController {

    private final SectorService sectorService;

    @GetMapping
    public BaseResponse<Page<SectorDTO>> getAllSectors(Pageable pageable) {
        return BaseResponse.success(sectorService.getAllSectors(pageable));
    }

    @GetMapping("/{id}")
    public BaseResponse<SectorDTO> getSectorById(@PathVariable Long id) {
        return BaseResponse.success(sectorService.getSectorById(id));
    }

    @PostMapping("/create")
    public BaseResponse<SectorDTO> createSector(SectorDTO sectorDTO) {
        return BaseResponse.created(sectorService.createSector(sectorDTO));
    }

    @PutMapping("/update/{id}")
    public BaseResponse<SectorDTO> updateSector(@PathVariable Long id, SectorDTO sectorDTO) {
        return BaseResponse.success(sectorService.updateSector(id, sectorDTO));
    }

    @DeleteMapping("/delete/{id}")
    public BaseResponse<Void> deleteSector(@PathVariable Long id) {
        sectorService.deleteSector(id);
        return BaseResponse.noContent();
    }

}
