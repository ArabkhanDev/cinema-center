package group.aist.cinema.service.impl;


import com.google.zxing.WriterException;
import group.aist.cinema.dto.request.TicketRequestDTO;
import group.aist.cinema.dto.response.TicketResponseDTO;
import group.aist.cinema.enums.AvailableType;
import group.aist.cinema.mapper.TicketMapper;
import group.aist.cinema.mapper.UserMapper;
import group.aist.cinema.model.*;
import group.aist.cinema.repository.HallRepository;
import group.aist.cinema.repository.MovieSessionRepository;
import group.aist.cinema.repository.TicketRepository;
import group.aist.cinema.repository.UserRepository;
import group.aist.cinema.service.EmailService;
import group.aist.cinema.service.QrCodeService;
import group.aist.cinema.service.TicketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final TicketMapper ticketMapper;
    private final QrCodeService qrCodeService;
    private final MovieSessionRepository movieSessionRepository;

    @Transactional
    @Override
    public void sendPurchaseLink(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: "+ticketId));

        if (ticket.getAvailableType() != AvailableType.AVAILABLE){
            throw new RuntimeException("Ticket is not available");
        }

        User user = ticket.getUser();

        ticket.setAvailableType(AvailableType.ORDERED);

        String purchaseLink = "http://localhost:8080/v1/api/tickets/confirmPurchase/" + ticketId;

        emailService.sendSimpleMessage(user.getEmail(), "Confirm your Ticket Purchase",
                "Please confirm your ticket purchase by clicking the following link: " + purchaseLink);
    }

    @Transactional
    @Override
    public Ticket confirmPurchase(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + ticketId));

        User user = ticket.getUser();

        BigDecimal balance = user.getBalance().getAmount();
        BigDecimal price = ticket.getPrice();
        if (balance.compareTo(price) >= 0) {
            BigDecimal newBalance = balance.subtract(price);
            Balance lastBalance = Balance.builder()
                    .currency(ticket.getCurrency())
                    .amount(newBalance)
                    .build();
            user.setBalance(lastBalance);
            ticket.setUser(user);
            ticket.setAvailableType(AvailableType.RESERVED);
        } else {
            throw new RuntimeException("Insufficient balance");
        }
        ticket.setAvailableType(AvailableType.ORDERED);

        return ticketRepository.save(ticket);
    }

    @Override
    @Transactional
    public void returnTicketLink(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + ticketId));

        User user = ticket.getUser();


        String returnLink = "http://localhost:8080/v1/api/tickets/confirmReturn/" + ticket.getId();

        emailService.sendSimpleMessage(user.getEmail(), "Confirm your Ticket Return",
                "Please confirm returning your ticket by clicking the following link: " + returnLink);
    }

    @Override
    @Transactional
    public void confirmReturn(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id" + ticketId));

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
    public byte[] generateQrCode(Long ticketId) throws IOException, WriterException {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + ticketId));

        byte[] bytes = qrCodeService.generatePdfWithQrCode(ticketId);
        String qr = Arrays.toString(bytes);
        ticket.setQrCode(qr);
        return bytes;
    }

    @Override
    @Transactional
    public String scanQrCode(Long ticketId){
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + ticketId));
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
                .orElseThrow(() -> new RuntimeException("Ticket not find: " + id));
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
            throw new RuntimeException("There are not available ticket!");
        }

        return allAvailableTickets.stream().map(ticketMapper::toDTO).toList();
    }

    @Override
    @Transactional
    public TicketResponseDTO createTicket(TicketRequestDTO ticketRequestDTO) {
        MovieSession movieSession = movieSessionRepository.findById(ticketRequestDTO.getMovieSessionId())
                .orElseThrow(() -> new RuntimeException("Movie Session not found with id: " + ticketRequestDTO.getMovieSessionId()));
        User user = userRepository.findById(ticketRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + ticketRequestDTO.getUserId()));

        Ticket ticket = ticketMapper.toEntity(ticketRequestDTO);
        Hall hall = movieSession.getHall();

        if(hall.getAvailableSeat()<1){
            throw new RuntimeException("There are not available seat");
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
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));

        MovieSession movieSession = movieSessionRepository.findById(ticketRequestDTO.getMovieSessionId())
                .orElseThrow(() -> new RuntimeException("Movie Session not found with id: " + ticketRequestDTO.getMovieSessionId()));
        ticket.setMovieSession(movieSession);

        User user = userRepository.findById(ticketRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + ticketRequestDTO.getUserId()));
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