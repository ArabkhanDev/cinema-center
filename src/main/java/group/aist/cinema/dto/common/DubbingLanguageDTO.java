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
    private String isoCode;

}
