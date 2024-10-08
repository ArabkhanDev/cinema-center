package group.aist.cinema.service.impl;

import group.aist.cinema.dto.common.HallDTO;
import group.aist.cinema.mapper.HallMapper;
import group.aist.cinema.model.Hall;
import group.aist.cinema.repository.HallRepository;
import group.aist.cinema.service.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HallServiceImpl implements HallService {

    private final HallRepository hallRepository;
    private final HallMapper hallMapper;


    @Override
    public Page<HallDTO> getAllHalls(Pageable pageable) {
        Page<Hall> halls = hallRepository.findAll(pageable);
        return halls.map(HallDTO::new);
    }

    @Override
    public HallDTO getHallById(Long id) {
        return hallMapper.toDTO(hallRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hall not found with id " + id)));
    }

    @Override
    public HallDTO createHall(HallDTO hallDTO) {
        Hall hall = hallMapper.toEntity(hallDTO);
        return hallMapper.toDTO(hallRepository.save(hall));
    }

    @Override
    public HallDTO updateHall(Long id, HallDTO hallDTO) {
        Hall hall = hallRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Hall not found with id " + id));
        hallMapper.updateHallFromDTO(hallDTO, hall);
        return hallMapper.toDTO(hallRepository.save(hall));
    }

    @Override
    public void deleteHall(Long id) {
        hallRepository.deleteById(id);
    }
}
