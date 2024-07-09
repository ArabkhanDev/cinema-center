package group.aist.cinema.repository;

import group.aist.cinema.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    List<Ticket> findByPriceOrderByPrice(BigDecimal price);
}
