package group.aist.cinema.dto.response;

import group.aist.cinema.dto.common.HallDTO;
import group.aist.cinema.dto.common.MovieDTO;
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

    private MovieDTO movie;

    private HallDTO hall;

    private MovieStreamResponseDTO movieStream;

}
