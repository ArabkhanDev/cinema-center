package group.aist.cinema.service;

import com.google.zxing.WriterException;
import group.aist.cinema.dto.request.TicketRequestDTO;
import group.aist.cinema.dto.response.TicketResponseDTO;
import group.aist.cinema.model.Ticket;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;


public interface TicketService {
    void sendPurchaseLink(Long ticketId);
    Ticket confirmPurchase(Long ticketId) throws IOException, WriterException, MessagingException;
    void returnTicketLink(Long ticketId) throws WriterException, MessagingException, IOException;
    void confirmReturn(Long ticketId);
    String generateQrCode(Long ticketId) throws IOException, WriterException;
    String scanQrCode(Long ticketId);
    Page<TicketResponseDTO> getAllTickets(Pageable pageable);
    TicketResponseDTO getTicketById(Long id);
    List<TicketResponseDTO> getAvailableTickets();
    List<TicketResponseDTO> getTicketByPrice(BigDecimal price);
    TicketResponseDTO createTicket(TicketRequestDTO ticketRequestDTO);
    TicketResponseDTO updateTicket(Long id, TicketRequestDTO ticketRequestDTO);
    void deleteTicket(Long id);
}
