package group.aist.cinema.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "Price must not be null")
    @Positive(message = "Price must be a positive number")
    private BigDecimal price;

    @NotNull(message = "Currency must not be null")
    @Size(min = 3, max = 3, message = "Currency must be a 3-letter ISO code")
    private String currency;

    @NotNull(message = "Start date must not be null")
    @FutureOrPresent(message = "Start date must be in the present or future")
    private LocalDate startDate;

    @NotNull(message = "End date must not be null")
    @FutureOrPresent(message = "End date must be in the present or future")
    private LocalDate endDate;

    @NotNull(message = "User id must not be null")
    private String userId;

    @NotNull(message = "Movie Session id must not be null")
    @Positive(message = "Movie Session ID must be a positive number")
    private Long movieSessionId;
}
