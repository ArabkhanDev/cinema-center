package group.aist.cinema.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {

    private String username;

    private String email;

    private String phone;

}
