package group.aist.cinema.service;

import group.aist.cinema.dto.common.HallDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HallService {

    Page<HallDTO> getAllHalls(Pageable pageable);

    HallDTO getHallById(Long id);

    HallDTO createHall(HallDTO hallDTO);

    HallDTO updateHall(Long id, HallDTO hallDTO);

    void deleteHall(Long id);

}
