package group.aist.cinema.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    private Long id;

    private String fullName;

    private String email;

    private String phone;

    private Long balanceId;
}
