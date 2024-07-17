package group.aist.cinema.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequestDTO {

    private String name;

    private String description;

    private String genre;

    private LocalDate releaseDate;

    private Short ageRestriction;

    private String duration;

    private MultipartFile image;

}
