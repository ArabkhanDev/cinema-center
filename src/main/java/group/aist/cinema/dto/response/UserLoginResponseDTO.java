package group.aist.cinema.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponseDTO {

    private String access_token;
    private String refresh_token;
    private int expires_in;
    private int refresh_expires_in;
}
