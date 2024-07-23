package group.aist.cinema.service;

import group.aist.cinema.dto.request.UserLoginRequestDTO;
import group.aist.cinema.dto.request.UserRequestDTO;
import group.aist.cinema.dto.request.UserUpdateRequest;
import group.aist.cinema.dto.response.UserLoginResponseDTO;
import group.aist.cinema.dto.response.UserResponseDTO;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<UserResponseDTO> getAllUsers(Pageable pageable);

    UserResponseDTO getUserById(String id);

    UserResponseDTO getUserByEmail(String email);

    UserResponseDTO registerUser(UserRequestDTO userDTO);

    UserLoginResponseDTO login(UserLoginRequestDTO userLoginRequestDTO);

    UserResponseDTO updateUser(String id, UserUpdateRequest userUpdateRequest);

    void deleteUserById(String id);
}
