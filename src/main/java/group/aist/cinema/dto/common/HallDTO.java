package group.aist.cinema.dto.common;

import group.aist.cinema.model.Hall;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HallDTO {

    @NotNull(message = "Id cannot be null")
    private Long id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;

//    @NotNull(message = "Seat count cannot be null")
//    @Positive(message = "Seat count must be a positive integer")
    private Integer seatCount;

//    @NotNull(message = "Available seat cannot be null")
//    @Positive(message = "Available seat must be greater than or equal to 0")
    private Integer availableSeat;

    public HallDTO(Hall hall) {
        this.id = hall.getId();
        this.name = hall.getName();
        this.seatCount = hall.getSeatCount();
        this.availableSeat = hall.getAvailableSeat();
    }

}
