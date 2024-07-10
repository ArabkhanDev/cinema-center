package group.aist.cinema.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieStreamDTO {

    private Long id;

    private Boolean subtitle;

    private String dubbingType;

}
