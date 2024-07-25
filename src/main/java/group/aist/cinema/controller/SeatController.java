package group.aist.cinema.controller;

import group.aist.cinema.dto.request.SeatRequestDTO;
import group.aist.cinema.dto.response.SeatResponseDTO;
import group.aist.cinema.model.base.BaseResponse;
import group.aist.cinema.service.SeatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @GetMapping
    public BaseResponse<Page<SeatResponseDTO>> getAllSeats(Pageable pageable) {
        return BaseResponse.success(seatService.getAllSeats(pageable));
    }

    @GetMapping("/{id}")
    public BaseResponse<SeatResponseDTO> getSectorById(@PathVariable Long id) {
        return BaseResponse.success(seatService.getSeatById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public BaseResponse<SeatResponseDTO> createSector(@Valid @RequestBody SeatRequestDTO seatRequestDTO) {
        return BaseResponse.created(seatService.createSeat(seatRequestDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public BaseResponse<SeatResponseDTO> updateSector(@PathVariable Long id,
                                                      @Valid @RequestBody SeatRequestDTO seatRequestDTO) {
        return BaseResponse.success(seatService.updateSeat(id, seatRequestDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public BaseResponse<Void> deleteSector(@PathVariable Long id) {
        seatService.deleteSeat(id);
        return BaseResponse.noContent();
    }
}
