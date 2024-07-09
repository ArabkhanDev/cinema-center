package group.aist.cinema.service;

import group.aist.cinema.dto.common.SectorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SectorService {

    Page<SectorDTO> getAllSectors(Pageable pageable);

    SectorDTO getSectorById(Long id);

    SectorDTO createSector(SectorDTO sectorDTO);

    SectorDTO updateSector(Long id, SectorDTO sectorDTO);

    void deleteSector(Long id);

}
