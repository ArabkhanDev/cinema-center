package group.aist.cinema.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequestDTO {

    private Long id;

    private BigDecimal price;

    private String currency;

    private LocalDate startDate;

    private LocalDate endDate;

    private Long userId;

    private Long movieSessionId;
}
