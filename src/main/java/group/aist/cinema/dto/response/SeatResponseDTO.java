package group.aist.cinema.dto.response;

import group.aist.cinema.dto.common.SectorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatResponseDTO {

    private Long id;

    private String row;

    private String number;

    private String type;

    private SectorDTO sector;

}
