package group.aist.cinema.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    @NotNull(message = "Username must not be null")
    @Size(max = 30, message = "Username must not exceed 30 characters")
    private String username;

    @NotNull(message = "Email must not be null")
    @Email(message = "Email must be valid")
    private String email;

    @NotNull(message = "Phone must not be null")
    @Size(max = 15, message = "Phone must not exceed 15 characters")
    private String phone;

    @NotNull(message = "Password must not be null")
    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
    private String password;

    @NotNull(message = "Balance ID must not be null")
    private Long balanceId;
}
