package group.aist.cinema.service.impl;

import group.aist.cinema.dto.common.HallDTO;
import group.aist.cinema.enums.SeatType;
import group.aist.cinema.mapper.HallMapper;
import group.aist.cinema.model.Hall;
import group.aist.cinema.model.Seat;
import group.aist.cinema.repository.HallRepository;
import group.aist.cinema.repository.SeatRepository;
import group.aist.cinema.service.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static group.aist.cinema.util.ExceptionMessages.HALL_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class HallServiceImpl implements HallService {

    private final HallRepository hallRepository;
    private final HallMapper hallMapper;
    private final SeatRepository seatRepository;

    @Override
    public Page<HallDTO> getAllHalls(Pageable pageable) {
        Page<Hall> halls = hallRepository.findAll(pageable);
        return halls.map(HallDTO::new);
    }

    @Override
    public HallDTO getHallById(Long id) {
        return hallMapper.toDTO(hallRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,HALL_NOT_FOUND + id)));
    }

    @Override
    @Transactional
    public HallDTO createHall(HallDTO hallDTO) {
        Hall hall = hallMapper.toEntity(hallDTO);
        List<Seat> seats = initializeSeatsInHall(hall);
        hall.setSeats(seats);
        hall.setSeatCount(70);
        hall.setAvailableSeat(70);
        return hallMapper.toDTO(hallRepository.save(hall));
    }

    @Override
    public HallDTO updateHall(Long id, HallDTO hallDTO) {
        Hall hall = hallRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(NOT_FOUND, HALL_NOT_FOUND + id));
        hallMapper.updateHallFromDTO(hallDTO, hall);
        return hallMapper.toDTO(hallRepository.save(hall));
    }

    @Override
    public void deleteHall(Long id) {
        hallRepository.deleteById(id);
    }

    public List<Seat> initializeSeatsInHall(Hall hall){
        List<Seat> seats = new ArrayList<>();

        String[] rows = {"G", "F", "E", "D", "C", "B", "A"};
        int[] seatCounts = {6, 8, 10, 12, 12, 12, 12};

        for (int i = 0; i < rows.length; i++) {
            addSeats(seats, rows[i], seatCounts[i], hall);
        }

        return hall.getSeats();
    }

    private void addSeats(List<Seat> seats, String row, int seatCount, Hall hall) {
        for (int i = 1; i <= seatCount; i++) {
            Seat seat = new Seat();
            seat.setRow(row);
            seat.setNumber(i);
            seat.setType(SeatType.AVAILABLE);
            seats.add(seat);
        }
        hall.setSeats(seats);
    }
}
