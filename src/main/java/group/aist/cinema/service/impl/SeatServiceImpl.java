package group.aist.cinema.service.impl;

import group.aist.cinema.dto.request.SeatRequestDTO;
import group.aist.cinema.dto.response.SeatResponseDTO;
import group.aist.cinema.enums.SeatType;
import group.aist.cinema.mapper.SeatMapper;
import group.aist.cinema.model.Seat;
import group.aist.cinema.model.Sector;
import group.aist.cinema.repository.SeatRepository;
import group.aist.cinema.repository.SectorRepository;
import group.aist.cinema.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private static final String SEAT_NOT_FOUND = "Seat not found with id: ";
    private static final String SECTOR_NOT_FOUND = "Sector not found with id: ";


    private final SeatRepository seatRepository;
    private final SectorRepository sectorRepository;
    private final SeatMapper seatMapper;


    @Override
    @Transactional
    public Page<SeatResponseDTO> getAllSeats(Pageable pageable) {
        Page<Seat> seats = seatRepository.findAll(pageable);
        return seats.map(seatMapper::toDTO);
    }

    @Override
    @Transactional
    public SeatResponseDTO getSeatById(Long id) {
        return seatMapper.toDTO(seatRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, SEAT_NOT_FOUND + id)));
    }

    @Override
    @Transactional
    public SeatResponseDTO createSeat(SeatRequestDTO seatRequestDTO) {
        return seatMapper.toDTO(seatRepository.save(getSeat(seatRequestDTO)));
    }

    @Override
    @Transactional
    public SeatResponseDTO updateSeat(Long id, SeatRequestDTO seatRequestDTO) {
        seatMapper.updateSeatFromDTO(seatRequestDTO, getSeat(id, seatRequestDTO));
        return seatMapper.toDTO(seatRepository.save(getSeat(id, seatRequestDTO)));
    }

    @Override
    public void deleteSeat(Long id) {
        seatRepository.deleteById(id);
    }

    private Seat getSeat(Long id, SeatRequestDTO seatRequestDTO) {
        Seat seat = seatRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, SEAT_NOT_FOUND + id));

        Sector sector = sectorRepository.findById(seatRequestDTO.getSectorId())
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, SEAT_NOT_FOUND + seatRequestDTO.getSectorId()));

        seat.setSector(sector);
        seat.setType(SeatType.fromString(seatRequestDTO.getType()));
        return seat;
    }

    private Seat getSeat(SeatRequestDTO seatRequestDTO) {
        Seat seat = seatMapper.toEntity(seatRequestDTO);

        Sector sector = sectorRepository.findById(seatRequestDTO.getSectorId())
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, SECTOR_NOT_FOUND + seatRequestDTO.getSectorId()));

        seat.setSector(sector);
        seat.setType(SeatType.fromString(seatRequestDTO.getType()));
        return seat;
    }
}

