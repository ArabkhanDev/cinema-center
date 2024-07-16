package group.aist.cinema.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    private String username;

    private String email;

    private String phone;

    private String password;

    private Long balanceId;
}
