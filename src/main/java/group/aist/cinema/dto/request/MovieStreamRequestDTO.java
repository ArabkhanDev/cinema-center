package group.aist.cinema.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieStreamRequestDTO {

    @NotNull(message = "HasSubtitle cannot be null")
    private Boolean hasSubtitle;

    @NotNull(message = "Dubbing Language ID cannot be null")
    @Positive(message = "Dubbing Language ID must be a positive number")
    private Long dubbingLanguageId;

    @NotNull(message = "Subtitle Language ID cannot be null")
    @Positive(message = "Subtitle Language ID must be a positive number")
    private Long subtitleLanguageId;

}
