package group.aist.cinema.service.impl;

import group.aist.cinema.config.KeycloakSecurityUtil;
import group.aist.cinema.dto.request.UserRequestDTO;
import group.aist.cinema.dto.request.UserUpdateRequest;
import group.aist.cinema.dto.response.UserResponseDTO;
import group.aist.cinema.mapper.UserMapper;
import group.aist.cinema.model.Balance;
import group.aist.cinema.model.User;
import group.aist.cinema.repository.BalanceRepository;
import group.aist.cinema.repository.UserRepository;
import group.aist.cinema.service.UserService;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BalanceRepository balanceRepository;
    private final KeycloakSecurityUtil keycloakSecurityUtil;

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> getAllUsers(Pageable pageable){
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        List<UserRepresentation> keycloakUsers = keycloak.realm("cinema").users().list();
        List<UserResponseDTO> userDTOs = keycloakUsers.stream()
                .map(userMapper::mapFromRepresentationToDto)
                .collect(Collectors.toList());
        return new PageImpl<>(userDTOs, pageable, keycloakUsers.size());
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(String id) {
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        return userMapper.mapFromRepresentationToDto(keycloak.realm("cinema").users().get(id).toRepresentation());
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        List<UserRepresentation> users = keycloak.realm("cinema").users().search(email,true);
        return userMapper.mapFromRepresentationToDto(users.get(0));
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
        Response res = keycloakSecurityUtil.getKeycloakInstance().realm("cinema").users().create(userRepresentation);

        if (res.getStatus() != 201) {
            throw new RuntimeException("Failed to create user: " + res.readEntity(String.class));
        }

        URI location = res.getLocation();
        String locationHeader = location.toString();
        System.out.println("Location Header: " + locationHeader);

        String userId = locationHeader.substring(locationHeader.lastIndexOf('/') + 1);
        System.out.println("Created User ID: " + userId);

        UserRepresentation createdUser = keycloakSecurityUtil.getKeycloakInstance().realm("cinema").users().get(userId).toRepresentation();
        System.out.println("Created User: " + createdUser.getUsername());

        UserResponseDTO userResponseDTO = getUserById(userId);
        User user = userMapper.fromResponseDtoToEntity(userResponseDTO);
        Balance balance = balanceRepository.findById(userRequestDTO.getBalanceId()).orElseThrow(() -> new RuntimeException("Balance not found with id " + userRequestDTO.getBalanceId()));
        user.setBalance(balance);
        user.setPhone(userRequestDTO.getPhone());
        userRepository.save(user);
        return userResponseDTO;
    }

    @Override
    @Transactional
    public UserResponseDTO updateUser(String id, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user = userMapper.fromUpdateReqToEntity(userUpdateRequest);
        userRepository.save(user);

        return userMapper.toDTO(user);
    }

    @Override
    public void deleteUserById(String id) {
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        keycloak.realm("cinema").users().delete(id);
        userRepository.deleteUserById(id);

    }

}
