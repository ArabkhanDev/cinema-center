package group.aist.cinema.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketPurchaseConfirmRequestDTO {

    private Long ticketId;
    private String seatRow;
    private Integer seatNumber;
}
