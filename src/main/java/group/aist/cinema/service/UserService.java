package group.aist.cinema.service;

import group.aist.cinema.dto.request.UserRequestDTO;
import group.aist.cinema.dto.response.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserResponseDTO> getAllUsers(Pageable pageable);

    UserResponseDTO getUserById(Long id);

    UserResponseDTO createUser(UserRequestDTO userDTO);

    UserResponseDTO updateUser(Long id, UserRequestDTO userDTO);

    void deleteUserById(Long id);
}
