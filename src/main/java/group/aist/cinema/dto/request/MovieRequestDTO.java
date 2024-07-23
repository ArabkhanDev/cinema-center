package group.aist.cinema.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequestDTO {

    @NotBlank(message = "Name cannot be null and empty")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;

    @NotBlank(message = "Description cannot be null and empty")
    @Size(min = 1, max = 1000, message = "Description must be between 1 and 1000 characters")
    private String description;

    @NotNull(message = "Genre cannot be null")
    @Size(min = 1, max = 50, message = "Genre must be between 1 and 50 characters")
    private String genre;

    @NotNull(message = "Release date cannot be null")
    @PastOrPresent(message = "Release date must be in the past or present")
    private LocalDate releaseDate;

    @NotNull(message = "Age restriction cannot be null")
    @Min(value = 0, message = "Age restriction must be at least 0")
    @Max(value = 18, message = "Age restriction must be at most 18")
    private Short ageRestriction;

    @NotNull(message = "Duration cannot be null")
    @Pattern(regexp = "^(\\d{1,2}h)?\\s*(\\d{1,2}m)?$", message = "Duration must be in the format 'Xh Ym'")
    private String duration;

    @NotNull(message = "Poster image cannot be null")
    private MultipartFile posterImage;

    @NotNull(message = "Background image cannot be null")
    private MultipartFile backgroundImage;

    private Long dubbingLanguageId;
}
