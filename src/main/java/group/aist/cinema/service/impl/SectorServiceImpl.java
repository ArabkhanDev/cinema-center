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
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new RuntimeException("Sector not found with id " + id)));
    }

    @Override
    public SectorDTO createSector(SectorDTO sectorDTO) {
        Sector sector = sectorMapper.toEntity(sectorDTO);

        Hall hall = hallRepository.findById(sectorDTO.getHallId())
                .orElseThrow(() -> new RuntimeException("Hall not found with id " + sectorDTO.getHallId()));

        sector.setHall(hall);
        return sectorMapper.toDTO(sectorRepository.save(sector));
    }

    @Override
    public SectorDTO updateSector(Long id, SectorDTO sectorDTO) {
        Sector sector = sectorRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Sector not found with id " + id));

        Hall hall = hallRepository.findById(sectorDTO.getHallId())
                .orElseThrow(() -> new RuntimeException("Hall not found with id " + sectorDTO.getHallId()));

        sector.setHall(hall);
        sectorMapper.updateSectorFromDTO(sectorDTO, sector);
        return sectorMapper.toDTO(sectorRepository.save(sector));
    }

    @Override
    public void deleteSector(Long id) {
        sectorRepository.deleteById(id);
    }
}
