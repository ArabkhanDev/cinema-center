package group.aist.cinema.service.impl;

import group.aist.cinema.dto.common.SectorDTO;
import group.aist.cinema.mapper.SectorMapper;
import group.aist.cinema.model.Hall;
import group.aist.cinema.model.Sector;
import group.aist.cinema.repository.HallRepository;
import group.aist.cinema.repository.SectorRepository;
import group.aist.cinema.service.SectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static group.aist.cinema.util.ExceptionMessages.HALL_NOT_FOUND;
import static group.aist.cinema.util.ExceptionMessages.SECTOR_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class SectorServiceImpl implements SectorService {

    private final SectorRepository sectorRepository;
    private final HallRepository hallRepository;
    private final SectorMapper sectorMapper;

    @Override
    public Page<SectorDTO> getAllSectors(Pageable pageable) {
        Page<Sector> sectors = sectorRepository.findAll(pageable);
        return sectors.map(SectorDTO::new);
    }

    @Override
    public SectorDTO getSectorById(Long id) {
        return sectorMapper.toDTO(sectorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,SECTOR_NOT_FOUND + id)));
    }

    @Override
    public SectorDTO createSector(SectorDTO sectorDTO) {
        return sectorMapper.toDTO(sectorRepository.save(setHallAndGetSector(sectorDTO, sectorMapper.toEntity(sectorDTO))));
    }

    @Override
    public SectorDTO updateSector(Long id, SectorDTO sectorDTO) {
        Sector sector = sectorRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(NOT_FOUND,SECTOR_NOT_FOUND + id));
        sectorMapper.updateSectorFromDTO(sectorDTO, setHallAndGetSector(sectorDTO, sector));
        return sectorMapper.toDTO(sectorRepository.save(sector));
    }

    @Override
    public void deleteSector(Long id) {
        sectorRepository.deleteById(id);
    }


    private Sector setHallAndGetSector(SectorDTO sectorDTO, Sector sector) {
        Hall hall = hallRepository.findById(sectorDTO.getHallId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,HALL_NOT_FOUND+ sectorDTO.getHallId()));
        sector.setHall(hall);
        return sector;
    }
}
