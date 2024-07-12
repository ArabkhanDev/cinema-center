package group.aist.cinema.service;

import group.aist.cinema.dto.request.TicketRequestDTO;
import group.aist.cinema.model.Ticket;
import group.aist.cinema.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface TicketService {
    void purchaseTicket(Long userId, Long ticketId);
    void returnTicket(Long ticketId);
    List<Ticket> getAllTickets();
    Optional<Ticket> getTicketById(Long id);
    Ticket createTicket(TicketRequestDTO ticketRequestDTO);
    Ticket updateTicket(Long id, TicketRequestDTO ticketRequestDTO);
    void deleteTicket(Long id);
}
