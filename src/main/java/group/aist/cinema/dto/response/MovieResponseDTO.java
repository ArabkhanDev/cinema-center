package group.aist.cinema.dto.response;

import group.aist.cinema.dto.common.DubbingLanguageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

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

    private String posterImage;

    private String backgroundImage;

    private Set<DubbingLanguageDTO> dubbingLanguages;

}
