package group.aist.cinema.dto.request;

import group.aist.cinema.enums.SeatType;
import group.aist.cinema.model.Seat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatRequestDTO {

    @NotNull(message = "Vertical must not be null")
    @Size(max = 10, message = "Vertical must not exceed 10 characters")
    private String vertical;

    @NotNull(message = "Horizontal must not be null")
    @Size(max = 10, message = "Horizontal must not exceed 10 characters")
    private String horizontal;

    @NotNull(message = "Type must not be null")
    @Size(max = 20, message = "Type must not exceed 20 characters")
    private String type;

    @NotNull(message = "Sector ID must not be null")
    private Long sectorId;

}
