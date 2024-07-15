package group.aist.cinema.repository;

import group.aist.cinema.enums.AvailableType;
import group.aist.cinema.model.Ticket;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Long> {

    @EntityGraph(attributePaths = {"users", "movies"})
    List<Ticket> findByPriceOrderByPrice(BigDecimal price);

    List<Ticket> findByAvailableType(AvailableType availableType);
}
