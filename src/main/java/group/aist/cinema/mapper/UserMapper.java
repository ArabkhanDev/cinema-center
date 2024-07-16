package group.aist.cinema.mapper;

import group.aist.cinema.dto.request.UserRequestDTO;
import group.aist.cinema.dto.request.UserUpdateRequest;
import group.aist.cinema.dto.response.SeatResponseDTO;
import group.aist.cinema.dto.response.UserResponseDTO;
import group.aist.cinema.model.Seat;
import group.aist.cinema.model.User;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO toDTO(User user);

    UserResponseDTO mapFromRepresentationToDto(UserRepresentation userRepresentation);

    UserRepresentation mapToRepresentation(UserRequestDTO userRequestDTO);

    User fromRepresentationToEntity(UserRepresentation userRepresentation);

    User fromResponseDtoToEntity(UserResponseDTO userResponseDTO);

    User toEntity(UserRequestDTO userDTO);

    User fromUpdateReqToEntity(UserUpdateRequest userUpdateRequest);

    @Mapping(target = "id", ignore = true)
    void updateUserFromDTO(UserRequestDTO userRequestDTO, @MappingTarget User user);

    @AfterMapping
    default void setBalanceId(@MappingTarget UserResponseDTO userResponseDTO, User user) {
        if (user.getBalance() != null) {
            userResponseDTO.getBalanceDTO().setId(user.getBalance().getId());
        }
    }
}
