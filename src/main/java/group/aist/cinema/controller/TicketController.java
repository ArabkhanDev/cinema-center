package group.aist.cinema.controller;

import group.aist.cinema.dto.request.TicketRequestDTO;
import group.aist.cinema.dto.request.UserRequestDTO;
import group.aist.cinema.dto.response.TicketResponseDTO;
import group.aist.cinema.dto.response.UserResponseDTO;
import group.aist.cinema.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/ticket")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseTicket(@RequestParam Long userId, @RequestBody Long ticketId) {
        ticketService.purchaseTicket(userId, ticketId);
        return ResponseEntity.ok("Ticket purchased successfully!");
    }

    @PostMapping("/return/{ticketId}")
    public ResponseEntity<String> returnTicket(@PathVariable Long ticketId) {
        ticketService.returnTicket(ticketId);
        return ResponseEntity.ok("Ticket returned successfully!");
    }

    @PostMapping
    public TicketResponseDTO createTicket(@RequestBody TicketRequestDTO ticketRequestDTO){
        return ticketService.createTicket(ticketRequestDTO);
    }

    @GetMapping
    public Page<TicketResponseDTO> getAllTickets(Pageable pageable) {
        return ticketService.getAllTickets(pageable);
    }

    @GetMapping("/{id}")
    public TicketResponseDTO getTicketById(@PathVariable Long id) {
        return ticketService.getTicketById(id);
    }

    @PutMapping("/{id}")
    public TicketResponseDTO updateTicket(@PathVariable Long id, @RequestBody TicketRequestDTO ticketRequestDTO) {
        return ticketService.updateTicket(id, ticketRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
    }


}
