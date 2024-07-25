package group.aist.cinema.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatRequestDTO {

    @NotNull(message = "Row must not be null")
    @Size(max = 10, message = "Row must not exceed 10 characters")
    private String row;

    @NotNull(message = "Number must not be null")
    @Size(max = 10, message = "Number must not exceed 10 characters")
    private String number;

    @NotNull(message = "Type must not be null")
    @Size(max = 20, message = "Type must not exceed 20 characters")
    private String type;

    @NotNull(message = "Sector ID must not be null")
    private Long sectorId;

}
