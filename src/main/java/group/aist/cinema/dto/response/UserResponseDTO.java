package group.aist.cinema.dto.response;

import group.aist.cinema.dto.common.BalanceDTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private BalanceDTO balanceDTO;
}
