package group.aist.cinema.dto.response;

import group.aist.cinema.dto.common.BalanceDTO;
import group.aist.cinema.model.Balance;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private String id;

    private String username;

    private String email;

    private String phone;

    private BalanceDTO balanceDTO;
}
