package group.aist.cinema.dto.common;

import group.aist.cinema.model.DubbingLanguage;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DubbingLanguageDTO {

    @NotNull(message = "Id cannot be null")
    private Long id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;

    @NotNull(message = "ISO code cannot be null")
    @Size(min = 2, max = 3, message = "ISO code must be between 2 and 3 characters")
    @Pattern(regexp = "^[a-zA-Z]{2,3}$", message = "ISO code must consist of 2 to 3 alphabetic characters")
    private String isoCode;

}
