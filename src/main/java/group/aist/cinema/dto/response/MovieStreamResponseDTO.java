package group.aist.cinema.dto.response;

import group.aist.cinema.dto.common.DubbingLanguageDTO;
import group.aist.cinema.dto.common.SubtitleLanguageDTO;
import group.aist.cinema.model.DubbingLanguage;
import group.aist.cinema.model.SubtitleLanguage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieStreamResponseDTO {

    private Long id;

    private Boolean hasSubtitle;

    private Set<DubbingLanguage> dubbingLanguages;

    private Set<SubtitleLanguage> subtitleLanguages;

}
