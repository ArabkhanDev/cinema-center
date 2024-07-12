package group.aist.cinema.service.impl;

import group.aist.cinema.dto.request.TicketRequestDTO;
import group.aist.cinema.model.Balance;
import group.aist.cinema.model.Ticket;
import group.aist.cinema.model.User;
import group.aist.cinema.repository.TicketRepository;
import group.aist.cinema.repository.UserRepository;
import group.aist.cinema.service.EmailService;
import group.aist.cinema.service.TicketService;
import jakarta.transaction.Transactional;
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
    public List<Ticket> getAllTickets() {
        return null;
    }

    @Override
    public Optional<Ticket> getTicketById(Long id) {
        return Optional.empty();
    }

    @Override
    public Ticket createTicket(TicketRequestDTO ticketRequestDTO) {
        return null;
    }

    @Override
    public Ticket updateTicket(Long id, TicketRequestDTO ticketRequestDTO) {
        return null;
    }

    @Override
    public void deleteTicket(Long id) {

    }
}
