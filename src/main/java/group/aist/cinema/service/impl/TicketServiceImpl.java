package group.aist.cinema.service.impl;

import group.aist.cinema.dto.request.TicketRequestDTO;
import group.aist.cinema.dto.response.TicketResponseDTO;
import group.aist.cinema.mapper.TicketMapper;
import group.aist.cinema.mapper.UserMapper;
import group.aist.cinema.model.Balance;
import group.aist.cinema.model.MovieSession;
import group.aist.cinema.model.Ticket;
import group.aist.cinema.model.User;
import group.aist.cinema.repository.MovieSessionRepository;
import group.aist.cinema.repository.TicketRepository;
import group.aist.cinema.repository.UserRepository;
import group.aist.cinema.service.EmailService;
import group.aist.cinema.service.TicketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final TicketMapper ticketMapper;
    private final MovieSessionRepository movieSessionRepository;

    @Override
    public void purchaseTicket(Long userId, Long ticketId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
        Ticket ticket = ticketOpt.get();
        if (userOpt.isPresent()) {
            User user = userOpt.get();
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
                ticketRepository.save(ticket);

                emailService.sendTicketEmail(user.getEmail(), ticket);
            } else {
                throw new RuntimeException("Insufficient balance");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    @Transactional
    public void returnTicket(Long ticketId) {
        Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
        if (ticketOpt.isPresent()) {
            Ticket ticket = ticketOpt.get();
            User user = ticket.getUser();
            BigDecimal lastBalance = user.getBalance().getAmount().add(ticket.getPrice());
            Balance balance = Balance.builder()
                    .amount(lastBalance)
                    .currency(ticket.getCurrency())
                    .build();
            user.setBalance(balance);
            ticketRepository.delete(ticket);
        } else {
            throw new RuntimeException("Ticket not found");
        }
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
    @Transactional
    public TicketResponseDTO createTicket(TicketRequestDTO ticketRequestDTO) {
        Ticket ticket = ticketMapper.toEntity(ticketRequestDTO);

        MovieSession movieSession = movieSessionRepository.findById(ticketRequestDTO.getMovieSessionId())
                .orElseThrow(() -> new RuntimeException("Movie Session not found with id: " + ticketRequestDTO.getMovieSessionId()));
        ticket.setMovieSession(movieSession);

        User user = userRepository.findById(ticketRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + ticketRequestDTO.getUserId()));
        ticket.setUser(user);

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