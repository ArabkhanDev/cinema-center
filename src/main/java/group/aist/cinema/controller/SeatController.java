package group.aist.cinema.controller;

import group.aist.cinema.dto.request.SeatRequestDTO;
import group.aist.cinema.dto.response.SeatResponseDTO;
import group.aist.cinema.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @GetMapping
    public Page<SeatResponseDTO> getAllSeats(Pageable pageable) {
        return seatService.getAllSeats(pageable);
    }

    @GetMapping("/{id}")
    public SeatResponseDTO getSectorById(@PathVariable Long id) {
        return seatService.getSeatById(id);
    }

    @PostMapping("/create")
    public SeatResponseDTO createSector(SeatRequestDTO seatRequestDTO) {
        return seatService.createSeat(seatRequestDTO);
    }

    @PutMapping("/update/{id}")
    public SeatResponseDTO updateSector(@PathVariable Long id, SeatRequestDTO seatRequestDTO) {
        return seatService.updateSeat(id, seatRequestDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSector(@PathVariable Long id) {
        seatService.deleteSeat(id);
    }
}
