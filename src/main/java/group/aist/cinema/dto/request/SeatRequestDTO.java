package group.aist.cinema.dto.request;

import group.aist.cinema.enums.SeatType;
import group.aist.cinema.model.Seat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatRequestDTO {


    private Long id;
    private String vertical;
    private String horizontal;
    private String type;
    private Long sectorId;

}
