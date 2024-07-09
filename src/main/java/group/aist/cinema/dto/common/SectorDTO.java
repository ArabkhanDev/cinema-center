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

    private String raw;

    private String column;

    private String name;


    public SectorDTO(Sector sector) {
        this.raw = sector.getRaw();
        this.id = sector.getId();
        this.column = sector.getColumn();
        this.name = sector.getName();
    }
}
