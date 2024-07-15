package group.aist.cinema.controller;

import group.aist.cinema.dto.request.TicketRequestDTO;
import group.aist.cinema.dto.response.TicketResponseDTO;
import group.aist.cinema.model.Ticket;
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

    @PostMapping("/sendPurchaseLink")
    public ResponseEntity<String> sendPurchaseLink(@RequestBody TicketRequestDTO ticketRequestDTO) {
        ticketService.sendPurchaseLink(ticketRequestDTO);
        return ResponseEntity.ok("Email send successfully! Please check your email and confirm your ticket");
    }

    @GetMapping("/confirmPurchase/{ticketId}")
    public Ticket confirmPurchase(@PathVariable Long ticketId) {
        return ticketService.confirmPurchase(ticketId);
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
