package group.aist.cinema.dto.common;

import group.aist.cinema.model.MovieStream;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {

    private Long id;

    private String name;

    private String description;

    private String genre;

    private LocalDate releaseDate;

    private Short ageRestriction;

    private String duration;

    private String image;

}
