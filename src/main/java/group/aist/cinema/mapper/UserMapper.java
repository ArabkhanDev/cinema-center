package group.aist.cinema.mapper;

import group.aist.cinema.dto.request.UserRequestDTO;
import group.aist.cinema.dto.response.SeatResponseDTO;
import group.aist.cinema.dto.response.UserResponseDTO;
import group.aist.cinema.model.Seat;
import group.aist.cinema.model.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO toDTO(User user);

    User toEntity(UserRequestDTO userDTO);

    @Mapping(target = "id", ignore = true)
    void updateUserFromDTO(UserRequestDTO userRequestDTO, @MappingTarget User user);

    @AfterMapping
    default void setBalanceId(@MappingTarget UserResponseDTO userResponseDTO, User user) {
        if (user.getBalance() != null) {
            userResponseDTO.getBalance().setId(user.getBalance().getId());
        }
    }
}
