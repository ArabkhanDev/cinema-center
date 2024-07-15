package group.aist.cinema.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

//    private String id;

    private String username;

    private String email;

    private String phone;

    private String password;

    private Long balanceId;
}
