package group.aist.cinema.repository;

import group.aist.cinema.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    Optional<Ticket> findTicketByPriceOrderByPrice(Integer price);
}
