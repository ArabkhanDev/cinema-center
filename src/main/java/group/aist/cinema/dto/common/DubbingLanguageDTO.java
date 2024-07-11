package group.aist.cinema.dto.common;

import group.aist.cinema.model.DubbingLanguage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DubbingLanguageDTO {

    private Long id;
    private String name;
    private String isACode;
    private Long movieStreamId;

    public DubbingLanguageDTO(DubbingLanguage dubbingLanguage) {
        this.id = dubbingLanguage.getId();
        this.name = dubbingLanguage.getName();
        this.isACode = dubbingLanguage.getIsACode();
        this.movieStreamId = dubbingLanguage.getMovieStream().getId();
    }
}
