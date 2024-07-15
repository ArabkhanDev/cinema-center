package group.aist.cinema.controller;

import group.aist.cinema.dto.request.UserRequestDTO;
import group.aist.cinema.dto.response.UserResponseDTO;
import group.aist.cinema.service.UserService;
import group.aist.cinema.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v1/api/users")
@RequiredArgsConstructor

public class UserController {
    private final UserService userService;

    @GetMapping
    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @GetMapping("/email")
    public UserResponseDTO getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping
    public UserResponseDTO registerUser(@RequestBody UserRequestDTO userDTO) {
        return userService.registerUser(userDTO);
    }

    @PutMapping("/{id}")
    public UserResponseDTO updateUser(@PathVariable String id, @RequestBody UserRequestDTO userDTO) {
        return userService.updateUser(id, userDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUserById(id);
    }

}
