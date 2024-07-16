package group.aist.cinema.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieStreamRequestDTO {

    private Boolean hasSubtitle;

    private Long dubbingLanguageId;

    private Long subtitleLanguageId;

}
