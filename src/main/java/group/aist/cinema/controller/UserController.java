package group.aist.cinema.controller;

import group.aist.cinema.dto.request.UserRequestDTO;
import group.aist.cinema.dto.request.UserUpdateRequest;
import group.aist.cinema.dto.response.UserResponseDTO;
import group.aist.cinema.model.base.BaseResponse;
import group.aist.cinema.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public BaseResponse<Page<UserResponseDTO>> getAllUsers(Pageable pageable) {
        return BaseResponse.success(userService.getAllUsers(pageable));
    }

    @GetMapping("/{id}")
    public BaseResponse<UserResponseDTO> getUserById(@PathVariable String id) {
        return BaseResponse.success(userService.getUserById(id));
    }

    @GetMapping("/email")
    public BaseResponse<UserResponseDTO> getUserByEmail(@RequestParam String email) {
        return BaseResponse.success(userService.getUserByEmail(email));
    }

    @PostMapping
    public BaseResponse<UserResponseDTO> registerUser(@RequestBody UserRequestDTO userDTO) {
        return BaseResponse.success(userService.registerUser(userDTO));
    }

    @PutMapping("/{id}")
    public BaseResponse<UserResponseDTO> updateUser(@PathVariable String id, @RequestBody UserUpdateRequest userDTO) {
        return BaseResponse.success(userService.updateUser(id, userDTO));
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteUser(@PathVariable String id) {
        userService.deleteUserById(id);
        return BaseResponse.noContent();
    }

}
