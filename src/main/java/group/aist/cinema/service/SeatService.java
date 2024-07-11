package group.aist.cinema.service;

import group.aist.cinema.dto.request.SeatRequestDTO;
import group.aist.cinema.dto.response.SeatResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SeatService {

    Page<SeatResponseDTO> getAllSeats(Pageable pageable);

    SeatResponseDTO getSeatById(Long id);

    SeatResponseDTO createSeat(SeatRequestDTO seatRequestDTO);

    SeatResponseDTO updateSeat(Long id, SeatRequestDTO seatRequestDTO);

    void deleteSeat(Long id);

}
