package group.aist.cinema.dto.response;

import group.aist.cinema.dto.common.BalanceDTO;
import group.aist.cinema.model.Balance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;

    private String fullName;

    private String email;

    private String phone;

    private BalanceDTO balanceDTO;
}
