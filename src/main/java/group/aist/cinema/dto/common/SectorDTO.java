package group.aist.cinema.dto.common;

import group.aist.cinema.model.Sector;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectorDTO {

    @NotNull(message = "Id cannot be null")
    private Long id;

    @NotNull(message = "Name of the sectors cannot be null")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;

    @NotNull(message = "Number of the sectors cannot be null")
    @Min(value = 1, message = "Number of the sectors must be greater than or equal to 1")
    private Integer number;

    @NotNull(message = "Hall ID cannot be null")
    private Long hallId;


    public SectorDTO(Sector sector) {
        this.id = sector.getId();
        this.name = sector.getName();
        this.number = sector.getNumber();
        this.hallId = sector.getHall() != null ? sector.getHall().getId() : null;
    }
}
