package group.aist.cinema.repository;

import group.aist.cinema.enums.AvailableType;
import group.aist.cinema.model.Ticket;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket,Long> {

    @Override
    @EntityGraph(attributePaths = {
            "user",
            "movieSession",
            "movieSession.hall",
            "movieSession.hall.seats",
            "movieSession.movie",
            "movieSession.movieStream",
            "movieSession.time",
            "user.balance",
            "movieSession.movieStream.dubbingLanguages",
            "movieSession.movieStream.subtitleLanguages",
            "movieSession.movie.languages"
    })
    Optional<Ticket> findById(Long id);


    @EntityGraph(attributePaths = {"user", "movieSession"})
    List<Ticket> findByPriceOrderByPrice(BigDecimal price);

    List<Ticket> findByAvailableType(AvailableType availableType);
}
