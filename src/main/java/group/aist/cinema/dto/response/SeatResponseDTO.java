package group.aist.cinema.dto.response;

import group.aist.cinema.dto.common.SectorDTO;
import group.aist.cinema.enums.SeatType;
import group.aist.cinema.model.Seat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatResponseDTO {

    private Long id;
    private String vertical;
    private String horizontal;
    private String type;
    private SectorDTO sector;

}
