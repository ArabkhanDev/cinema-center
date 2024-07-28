package group.aist.cinema.repository;

import group.aist.cinema.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat,Long> {

    Optional<Seat> findSeatByRowAndNumber(String row, int number);

    Optional<Seat> findSeatByRow(String row);
}
