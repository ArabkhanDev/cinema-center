package group.aist.cinema.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieUpdateRequest {

    @NotBlank(message = "Name must not be blank")
    @Size(min = 2, max = 30, message = "Name must not exceed 30 characters")
    private String name;

    @NotNull(message = "Description must not be null")
    @Size(min = 10, max = 200, message = "Description must not exceed 200 characters")
    private String description;

    @NotNull(message = "Genre must not be null")
    @Size(min = 2, max = 25, message = "Genre must not exceed 25 characters")
    private String genre;

    @NotNull(message = "Release date must not be null")
    private LocalDate releaseDate;

    @NotNull(message = "Age restriction must not be null")
    @Min(value = 0, message = "Age restriction must not be negative")
    @Max(value = 100, message = "Age restriction must not exceed 100")
    @Positive
    private Short ageRestriction;

    @NotNull(message = "Duration must not be null")
    @Pattern(regexp = "^PT?\\d+H\\d+M$", message = "Duration must be in the format of PTnHnM (e.g., PT2H30M)")
    private String duration;

}
