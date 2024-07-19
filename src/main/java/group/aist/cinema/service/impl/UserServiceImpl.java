package group.aist.cinema.service.impl;

import group.aist.cinema.config.KeycloakSecurityUtil;
import group.aist.cinema.dto.common.BalanceDTO;
import group.aist.cinema.dto.request.UserRequestDTO;
import group.aist.cinema.dto.request.UserUpdateRequest;
import group.aist.cinema.dto.response.UserResponseDTO;
import group.aist.cinema.mapper.BalanceMapper;
import group.aist.cinema.mapper.UserMapper;
import group.aist.cinema.model.Balance;
import group.aist.cinema.model.User;
import group.aist.cinema.repository.BalanceRepository;
import group.aist.cinema.repository.UserRepository;
import group.aist.cinema.service.UserService;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static group.aist.cinema.util.ExceptionMessages.BALANCE_NOT_FOUND;
import static group.aist.cinema.util.ExceptionMessages.USER_NOT_FOUND;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BalanceRepository balanceRepository;
    private final KeycloakSecurityUtil keycloakSecurityUtil;
    private final BalanceMapper balanceMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> getAllUsers(Pageable pageable){
        return userRepository.findAllBy(pageable).map(user -> {
            BalanceDTO balanceDTO = balanceMapper.toDTO(user.getBalance());
            UserResponseDTO userResponseDTO = userMapper.toDTO(user);
            userResponseDTO.setBalanceDTO(balanceDTO);
            return userResponseDTO;
        });
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND,USER_NOT_FOUND + id));
        UserResponseDTO userResponseDTO = userMapper.toDTO(user);
        userResponseDTO.setBalanceDTO(balanceMapper.toDTO(user.getBalance()));
        return userResponseDTO;
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(NOT_FOUND,"User not found with this email " + email));
        UserResponseDTO userResponseDTO = userMapper.toDTO(user);
        userResponseDTO.setBalanceDTO(balanceMapper.toDTO(user.getBalance()));
        return userResponseDTO;
    }

    @Override
    @Transactional
    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {

        UserRepresentation userRepresentation = userMapper.mapToRepresentation(userRequestDTO);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(userRequestDTO.getPassword());
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        List<CredentialRepresentation> credentialsList = new ArrayList<>();
        credentialsList.add(credentialRepresentation);
        userRepresentation.setCredentials(credentialsList);
        Response res = keycloakSecurityUtil.getKeycloakInstance().realm("Team1").users().create(userRepresentation);

        if (res.getStatus() != 201) {
            throw new ResponseStatusException(BAD_REQUEST,"Failed to create user: " + res.readEntity(String.class));
        }

        URI location = res.getLocation();
        String locationHeader = location.toString();
        String userId = locationHeader.substring(locationHeader.lastIndexOf('/') + 1);

        Balance balance = balanceRepository.findById(userRequestDTO.getBalanceId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,BALANCE_NOT_FOUND + userRequestDTO.getBalanceId()));
        BalanceDTO balanceDTO = balanceMapper.toDTO(balance);
        User user = userMapper.toEntity(userRequestDTO);
        user.setId(userId);
        user.setBalance(balance);

        User savedUser = userRepository.save(user);
        UserResponseDTO userResponseDTO = userMapper.toDTO(savedUser);
        userResponseDTO.setBalanceDTO(balanceDTO);
        return userResponseDTO;
    }

    @Override
    @Transactional
    public UserResponseDTO updateUser(String id, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,USER_NOT_FOUND + id));
        userMapper.updateUserFromUpdateRequestDTO(userUpdateRequest,user);
        userRepository.save(user);
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        UserRepresentation userRepresentation = keycloak.realm("Team1").users().get(id).toRepresentation();
        userRepresentation.setEmail(userUpdateRequest.getEmail());
        keycloakSecurityUtil.getKeycloakInstance().realm("Team1").users().get(id).update(userRepresentation);
        return userMapper.toDTO(user);
    }

    @Override
    public void deleteUserById(String id) {
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        keycloak.realm("cinema").users().delete(id);
        userRepository.deleteUserById(id);

    }

}
