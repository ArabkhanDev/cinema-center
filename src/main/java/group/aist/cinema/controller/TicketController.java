package group.aist.cinema.controller;

import com.google.zxing.WriterException;
import group.aist.cinema.dto.request.TicketRequestDTO;
import group.aist.cinema.dto.response.TicketResponseDTO;
import group.aist.cinema.model.Ticket;
import group.aist.cinema.model.base.BaseResponse;
import group.aist.cinema.service.TicketService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/v1/api/ticket")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @GetMapping("/sendPurchaseLink/{ticketId}")
    public BaseResponse<String> sendPurchaseLink(@PathVariable Long ticketId) {
        ticketService.sendPurchaseLink(ticketId);
        return BaseResponse.success("Email send successfully! Please check your email and confirm your ticket");
    }

    @GetMapping("/confirmPurchase/{ticketId}")
    public Ticket confirmPurchase(@PathVariable Long ticketId) {
        return ticketService.confirmPurchase(ticketId);
    }

    @PostMapping("/return/{ticketId}")
    public BaseResponse<String>  returnTicket(@PathVariable Long ticketId) throws MessagingException, IOException, WriterException {
        ticketService.returnTicketLink(ticketId);
        return BaseResponse.success("Email send successfully! Please check your email to return your ticket");
    }

    @GetMapping("/confirmReturn/{ticketId}")
    public BaseResponse<String>  confirmReturn(@PathVariable Long ticketId) {
        ticketService.confirmReturn(ticketId);
        return BaseResponse.success("You returned ticket successfully!");
    }

    @GetMapping("/generateQrCode/{ticketId}")
    public BaseResponse<String> generateQrCode(@PathVariable Long ticketId) {
        try {
            ticketService.generateQrCode(ticketId);
            return BaseResponse.success("Ticket is marked as returned");
        } catch (RuntimeException e) {
            return BaseResponse.success("Bad Request");
        } catch (IOException | WriterException e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/scanQrCode/{ticketId}")
    public BaseResponse<String> scanQrCode(@PathVariable Long ticketId){
        String scan = ticketService.scanQrCode(ticketId);
        return BaseResponse.success(scan);
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

    @GetMapping()
    public List<TicketResponseDTO> getAvailableTickets() {
        return ticketService.getAvailableTickets();
    }

    @GetMapping("/byPrice")
    public List<TicketResponseDTO> getTicketByPrice(@RequestParam BigDecimal price) {
        return ticketService.getTicketByPrice(price);
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
