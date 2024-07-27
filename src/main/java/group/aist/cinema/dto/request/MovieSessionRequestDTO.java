package group.aist.cinema.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieSessionRequestDTO {

    @NotNull(message = "Time cannot be null")
    @Valid
    private Set<@FutureOrPresent(message = "Time must be in the present or future") LocalDateTime> time;

    private boolean isActive;

    @NotNull(message = "Movie ID cannot be null")
    @Positive(message = "Movie ID must be a positive number")
    private Long movieId;

    @NotNull(message = "Hall ID cannot be null")
    @Positive(message = "Hall ID must be a positive number")
    private Long hallId;

    @NotNull(message = "Movie Stream ID cannot be null")
    @Positive(message = "Movie Stream ID must be a positive number")
    private Long movieStreamId;
}
