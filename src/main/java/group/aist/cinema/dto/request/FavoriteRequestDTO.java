package group.aist.cinema.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FavoriteRequestDTO {

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @NotNull
    private Long userId;

    @NotNull
    private Long movieId;

}
