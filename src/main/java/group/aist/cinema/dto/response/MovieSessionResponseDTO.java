package group.aist.cinema.dto.response;

import group.aist.cinema.enums.SessionType;
import group.aist.cinema.model.Hall;
import group.aist.cinema.model.Movie;
import group.aist.cinema.model.MovieStream;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieSessionResponseDTO {

    private Long id;

    private LocalDate time;

    private SessionType type;

    private boolean isActive;

    private Movie movie;

    private Hall hall;

    private MovieStream movieStream;

}
