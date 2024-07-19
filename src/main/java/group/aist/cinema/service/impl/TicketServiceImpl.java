package group.aist.cinema.service.impl;


import com.google.zxing.WriterException;
import com.itextpdf.io.codec.Base64;
import group.aist.cinema.dto.request.TicketRequestDTO;
import group.aist.cinema.dto.response.TicketResponseDTO;
import group.aist.cinema.enums.AvailableType;
import group.aist.cinema.mapper.TicketMapper;
import group.aist.cinema.model.*;
import group.aist.cinema.repository.*;
import group.aist.cinema.service.EmailService;
import group.aist.cinema.service.QrCodeService;
import group.aist.cinema.service.TicketService;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static group.aist.cinema.util.TicketUtil.CONFIRM_PURCHASE_LINK;
import static group.aist.cinema.util.TicketUtil.CONFIRM_RETURN_LINK;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final String TICKET_NOT_FOUND= "Ticket not found with id: ";

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final BalanceRepository balanceRepository;
    private final EmailService emailService;
    private final TicketMapper ticketMapper;
    private final QrCodeService qrCodeService;
    private final MovieSessionRepository movieSessionRepository;

    @Transactional
    @Override
    public void sendPurchaseLink(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, TICKET_NOT_FOUND + ticketId));

        if (ticket.getAvailableType() != AvailableType.AVAILABLE){
            throw new ResponseStatusException(BAD_REQUEST, "Ticket is not available");
        }

        User user = ticket.getUser();

        ticket.setAvailableType(AvailableType.ORDERED);

        String purchaseLink = CONFIRM_PURCHASE_LINK + ticketId;

        emailService.sendSimpleMessage(user.getEmail(), "Confirm your Ticket Purchase",
                "Please confirm your ticket purchase by clicking the following link: " + purchaseLink);
    }

    @Transactional
    @Override
    public Ticket confirmPurchase(Long ticketId) throws IOException, WriterException, MessagingException {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, TICKET_NOT_FOUND + ticketId));

        User user = ticket.getUser();

        BigDecimal balance = user.getBalance().getAmount();
        BigDecimal price = ticket.getPrice();
        if (balance.compareTo(price) >= 0) {
            BigDecimal newBalance = balance.subtract(price);
            Balance lastBalance = Balance.builder()
                    .id(user.getBalance().getId())
                    .currency(ticket.getCurrency())
                    .amount(newBalance)
                    .build();

            lastBalance = balanceRepository.save(lastBalance);

            user.setBalance(lastBalance);
            ticket.setUser(user);
            ticket.setAvailableType(AvailableType.ORDERED);
        } else {
            throw new ResponseStatusException(BAD_REQUEST, "Insufficient balance");
        }

        byte[] qrCodePdf = qrCodeService.generatePdfWithQrCode(ticketId);

        emailService.sendMessageWithQrCode(
                user.getEmail(),
                "Ticket Purchase Confirmation",
                "Your ticket has been confirmed",
                qrCodePdf
        );

        return ticketRepository.save(ticket);
    }


    @Override
    @Transactional
    public void returnTicketLink(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, TICKET_NOT_FOUND + ticketId));

        Balance balance = balanceRepository.findById(ticket.getUser().getBalance().getId())
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Balance not found with id: " + ticket.getUser().getBalance().getId()));

        BigDecimal newBalance = balance.getAmount().add(ticket.getPrice().multiply(BigDecimal.valueOf(0.8)));
        balance.setAmount(newBalance);
        User user = ticket.getUser();
        balanceRepository.save(balance);

        String returnLink = CONFIRM_RETURN_LINK + ticket.getId();

        emailService.sendSimpleMessage(user.getEmail(), "Confirm your Ticket Return",
                "Please confirm returning your ticket by clicking the following link: " + returnLink);
    }

    @Override
    @Transactional
    public void confirmReturn(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, TICKET_NOT_FOUND + ticketId));

        User user = ticket.getUser();

        BigDecimal balance = user.getBalance().getAmount();
        BigDecimal price = ticket.getPrice();
            BigDecimal newBalance = balance.add(price);
            Balance lastBalance = Balance.builder()
                    .currency(ticket.getCurrency())
                    .amount(newBalance)
                    .build();
            user.setBalance(lastBalance);

        ticketRepository.deleteById(ticketId);
    }

    @Override
    @Transactional
    public String generateQrCode(Long ticketId) throws IOException, WriterException {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, TICKET_NOT_FOUND + ticketId));
        return Base64.encodeBytes(qrCodeService.generatePdfWithQrCode(ticketId));
    }

    @Override
    @Transactional
    public String scanQrCode(Long ticketId){
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, TICKET_NOT_FOUND + ticketId));
        if (ticket.getIsScanned())
            return "Ticket already scanned!!!";
        else
            ticket.setIsScanned(true);

        return "Ticket scanned successfully!";
    }


    @Override
    @Transactional(readOnly = true)
    public Page<TicketResponseDTO> getAllTickets(Pageable pageable) {
        Page<Ticket> allTickets = ticketRepository.findAll(pageable);
        return allTickets.map(ticketMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public TicketResponseDTO getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, TICKET_NOT_FOUND + id));
        return ticketMapper.toDTO(ticket);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketResponseDTO> getTicketByPrice(BigDecimal price) {
        List<Ticket> tickets = ticketRepository.findByPriceOrderByPrice(price);
        return tickets.stream().map(ticketMapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketResponseDTO> getAvailableTickets() {

        List<Ticket> allAvailableTickets = ticketRepository.findByAvailableType(AvailableType.AVAILABLE);
        if (allAvailableTickets.isEmpty()){
            throw new ResponseStatusException(BAD_REQUEST, "There are no available tickets!");
        }

        return allAvailableTickets.stream().map(ticketMapper::toDTO).toList();
    }

    @Override
    @Transactional
    public TicketResponseDTO createTicket(TicketRequestDTO ticketRequestDTO) {
        MovieSession movieSession = movieSessionRepository.findById(ticketRequestDTO.getMovieSessionId())
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Movie Session not found with id: " + ticketRequestDTO.getMovieSessionId()));

        User user = userRepository.findById(ticketRequestDTO.getUserId())
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "User not found with id: " + ticketRequestDTO.getUserId()));


        Ticket ticket = ticketMapper.toEntity(ticketRequestDTO);
        Hall hall = movieSession.getHall();

        if(hall.getAvailableSeat() < 1){
            throw new ResponseStatusException(BAD_REQUEST, "There are no available seats");
        }

        ticket.setAvailableType(AvailableType.AVAILABLE);
        ticket.setUser(user);
        ticket.setMovieSession(movieSession);
        ticket.setIsScanned(false);


        Ticket saved = ticketRepository.save(ticket);

        return ticketMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public TicketResponseDTO updateTicket(Long id, TicketRequestDTO ticketRequestDTO) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, TICKET_NOT_FOUND + id));

        MovieSession movieSession = movieSessionRepository.findById(ticketRequestDTO.getMovieSessionId())
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Movie Session not found with id: " + ticketRequestDTO.getMovieSessionId()));

        ticket.setMovieSession(movieSession);

        User user = userRepository.findById(ticketRequestDTO.getUserId())
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "User not found with id: " + ticketRequestDTO.getUserId()));
        ticket.setUser(user);
        ticketMapper.updateTicketFromDTO(ticketRequestDTO,ticket);

        Ticket saved = ticketRepository.save(ticket);

        return ticketMapper.toDTO(saved);
    }

    @Override
    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }

}