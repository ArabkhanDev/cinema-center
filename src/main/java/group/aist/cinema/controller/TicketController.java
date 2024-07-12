package group.aist.cinema.controller;

import group.aist.cinema.service.TicketService;
import lombok.RequiredArgsConstructor;
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


}
