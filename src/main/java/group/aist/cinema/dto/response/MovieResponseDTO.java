package group.aist.cinema.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponseDTO {

    private Long id;

    private String name;

    private String description;

    private String genre;

    private LocalDate releaseDate;

    private Short ageRestriction;

    private String duration;

    private String image;

}
