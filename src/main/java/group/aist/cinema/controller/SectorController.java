package group.aist.cinema.controller;

import group.aist.cinema.dto.common.SectorDTO;
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
    public Page<SectorDTO> getAllSectors(Pageable pageable) {
        return sectorService.getAllSectors(pageable);
    }

    @GetMapping("/{id}")
    public SectorDTO getSectorById(@PathVariable Long id) {
        return sectorService.getSectorById(id);
    }

    @PostMapping("/create")
    public SectorDTO createSector(SectorDTO sectorDTO) {
        return sectorService.createSector(sectorDTO);
    }

    @PutMapping("/update/{id}")
    public SectorDTO updateSector(@PathVariable Long id, SectorDTO sectorDTO) {
        return sectorService.updateSector(id, sectorDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSector(@PathVariable Long id) {
        sectorService.deleteSector(id);
    }

}
