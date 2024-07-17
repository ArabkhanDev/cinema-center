package group.aist.cinema.dto.response;

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

    private Set<MovieResponseDTO> movies;

}
