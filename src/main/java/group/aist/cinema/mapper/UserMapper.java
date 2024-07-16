package group.aist.cinema.mapper;

import group.aist.cinema.dto.request.UserRequestDTO;
import group.aist.cinema.dto.request.UserUpdateRequest;
import group.aist.cinema.dto.response.UserResponseDTO;
import group.aist.cinema.model.User;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "balance", target = "balanceDTO")
    UserResponseDTO toDTO(User user);

    UserResponseDTO mapFromRepresentationToDto(UserRepresentation userRepresentation);

    UserRepresentation mapToRepresentation(UserRequestDTO userRequestDTO);

    User toEntity(UserRequestDTO userDTO);

    void updateUserFromDTO(UserRequestDTO userRequestDTO, @MappingTarget User user);

    @Mapping(target = "id", ignore = true)
    void updateUserFromUpdateRequestDTO(UserUpdateRequest userUpdateRequest, @MappingTarget User user);
}
