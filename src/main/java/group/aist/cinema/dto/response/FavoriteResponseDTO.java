package group.aist.cinema.dto.response;

import group.aist.cinema.dto.common.MovieDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteResponseDTO {

    private Long id;

    private String name;

    private UserResponseDTO user;

    private Set<MovieDTO> movies;

}
