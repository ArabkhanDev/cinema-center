package group.aist.cinema.dto.request;

import group.aist.cinema.enums.SessionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieSessionRequestDTO {

    private Long id;

    private LocalDate time;

    private SessionType type;

    private boolean isActive;

    private Long movieId;

    private Long hallId;

    private Long movieStreamId;
}
