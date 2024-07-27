package group.aist.cinema.controller;

import com.google.zxing.WriterException;
import group.aist.cinema.dto.request.TicketPurchaseConfirmRequestDTO;
import group.aist.cinema.dto.request.TicketRequestDTO;
import group.aist.cinema.dto.request.TicketReturnConfirmRequestDTO;
import group.aist.cinema.dto.response.TicketResponseDTO;
import group.aist.cinema.model.Ticket;
import group.aist.cinema.model.base.BaseResponse;
import group.aist.cinema.service.TicketService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/v1/api/ticket")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/send-purchase-link/{ticketId}")
    public BaseResponse<String> sendPurchaseLink(@PathVariable Long ticketId) {
        ticketService.sendPurchaseLink(ticketId);
        return BaseResponse.success("Email send successfully! Please check your email and confirm your ticket");
    }

    @PostMapping("/confirm-purchase")
    public Ticket confirmPurchase(@RequestBody TicketPurchaseConfirmRequestDTO requestDTO) throws MessagingException, IOException, WriterException {
        return ticketService.confirmPurchase(requestDTO);
    }

    @PostMapping("/return/{ticketId}")
    public BaseResponse<String>  returnTicket(@PathVariable Long ticketId) throws MessagingException, IOException, WriterException {
        ticketService.returnTicketLink(ticketId);
        return BaseResponse.success("Email send successfully! Please check your email to return your ticket");
    }

    @PostMapping("/confirm-return")
    public BaseResponse<String>  confirmReturn(@RequestBody TicketReturnConfirmRequestDTO requestDTO) {
        ticketService.confirmReturn(requestDTO);
        return BaseResponse.success("You returned ticket successfully!");
    }

    @PostMapping("/generate-qrcode/{ticketId}")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<String> generateQrCode(@PathVariable Long ticketId) {
        try {
            return BaseResponse.success(ticketService.generateQrCode(ticketId));
        } catch (RuntimeException e) {
            return BaseResponse.success("Bad Request");
        } catch (IOException | WriterException e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/scan-qr-code/{ticketId}")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<String> scanQrCode(@PathVariable Long ticketId){
        String scan = ticketService.scanQrCode(ticketId);
        return BaseResponse.success(scan);
    }


    @PostMapping
    public TicketResponseDTO createTicket(@Valid @RequestBody TicketRequestDTO ticketRequestDTO){
        return ticketService.createTicket(ticketRequestDTO);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<TicketResponseDTO> getAllTickets(Pageable pageable) {
        return ticketService.getAllTickets(pageable);
    }

    @GetMapping("/{id}")
    public TicketResponseDTO getTicketById(@PathVariable Long id) {
        return ticketService.getTicketById(id);
    }

    @GetMapping("/available-tickets")
    @PreAuthorize("hasRole('ADMIN')")
    public List<TicketResponseDTO> getAvailableTickets() {
        return ticketService.getAvailableTickets();
    }

    @GetMapping("/byPrice")
    public List<TicketResponseDTO> getTicketByPrice(@RequestParam BigDecimal price) {
        return ticketService.getTicketByPrice(price);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public TicketResponseDTO updateTicket(@PathVariable Long id,
                                          @Valid @RequestBody TicketRequestDTO ticketRequestDTO) {
        return ticketService.updateTicket(id, ticketRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
    }


}
