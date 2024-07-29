package group.aist.cinema.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketReturnConfirmRequestDTO {
    private Long ticketId;
    private Long seatId;
}
