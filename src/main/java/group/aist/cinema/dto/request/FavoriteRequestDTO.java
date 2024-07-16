package group.aist.cinema.dto.request;

import lombok.Data;

@Data
public class FavoriteRequestDTO {

    //todo unique constraint
    private String name;

    private Long userId;

    private Long movieId;

}
