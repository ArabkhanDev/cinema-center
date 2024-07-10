package group.aist.cinema.dto.common;

import group.aist.cinema.model.Sector;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectorDTO {

    private Long id;

    private String name;

    private Integer number;

    private Long hallId;


    public SectorDTO(Sector sector) {
        this.id = sector.getId();
        this.name = sector.getName();
        this.number = sector.getNumber();
        this.hallId = sector.getHall().getId();
    }
}
