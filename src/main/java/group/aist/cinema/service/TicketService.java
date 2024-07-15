package group.aist.cinema.service;

import group.aist.cinema.dto.request.TicketRequestDTO;
import group.aist.cinema.dto.response.TicketResponseDTO;
import group.aist.cinema.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface TicketService {
    void sendPurchaseLink(TicketRequestDTO ticketRequestDTO);
    Ticket confirmPurchase(Long ticketId);
    void returnTicket(Long ticketId);
    Page<TicketResponseDTO> getAllTickets(Pageable pageable);
    TicketResponseDTO getTicketById(Long id);
    TicketResponseDTO createTicket(TicketRequestDTO ticketRequestDTO);
    TicketResponseDTO updateTicket(Long id, TicketRequestDTO ticketRequestDTO);
    void deleteTicket(Long id);
}
