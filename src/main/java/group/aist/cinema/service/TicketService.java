package group.aist.cinema.service;

import group.aist.cinema.dto.request.TicketRequestDTO;
import group.aist.cinema.dto.response.TicketResponseDTO;
import group.aist.cinema.model.Ticket;
import group.aist.cinema.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface TicketService {
    void purchaseTicket(Long userId, Long ticketId);
    void returnTicket(Long ticketId);
    Page<TicketResponseDTO> getAllTickets(Pageable pageable);
    TicketResponseDTO getTicketById(Long id);
    TicketResponseDTO createTicket(TicketRequestDTO ticketRequestDTO);
    TicketResponseDTO updateTicket(Long id, TicketRequestDTO ticketRequestDTO);
    void deleteTicket(Long id);
}
