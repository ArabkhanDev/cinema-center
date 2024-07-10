package group.aist.cinema.dto.common;

import group.aist.cinema.model.Hall;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HallDTO {

    private Long id;

    private String name;

    private Integer seatCount;

    private Integer availableSeat;


    public HallDTO(Hall hall) {
        this.id = hall.getId();
        this.name = hall.getName();
        this.seatCount = hall.getSeatCount();
        this.availableSeat = hall.getAvailableSeat();
    }

}
