package group.aist.cinema.service.impl;

import group.aist.cinema.config.KeycloakSecurityUtil;
import group.aist.cinema.dto.common.BalanceDTO;
import group.aist.cinema.dto.request.UserLoginRequestDTO;
import group.aist.cinema.dto.request.UserRequestDTO;
import group.aist.cinema.dto.request.UserUpdateRequest;
import group.aist.cinema.dto.response.UserLoginResponseDTO;
import group.aist.cinema.dto.response.UserResponseDTO;
import group.aist.cinema.mapper.BalanceMapper;
import group.aist.cinema.mapper.UserMapper;
import group.aist.cinema.model.Balance;
import group.aist.cinema.model.User;
import group.aist.cinema.repository.BalanceRepository;
import group.aist.cinema.repository.UserRepository;
import group.aist.cinema.service.UserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    private final Keycloak keycloak;
    private final BalanceMapper balanceMapper;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final KeycloakSecurityUtil keycloakSecurityUtil;
//    private final AuthenticationManager authenticationManager;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${springdoc.swagger-ui.oauth.client-id}")
    private String clientId;

    @Value("${springdoc.swagger-ui.oauth.client-secret}")
    private String clientSecret;

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

//        Balance balance = balanceRepository.findById(userRequestDTO.getBalanceId())
//                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,BALANCE_NOT_FOUND + userRequestDTO.getBalanceId()));
        UserRepresentation userRepresentation = createUserRepresentation(userRequestDTO);
        Response res = keycloak.realm(realm).users().create(userRepresentation);
        if (res.getStatus() != 201) {
            throw new ResponseStatusException(BAD_REQUEST,"Failed to create user: " + res.readEntity(String.class));
        }

        URI location = res.getLocation();
        String locationHeader = location.toString();
        String userId = locationHeader.substring(locationHeader.lastIndexOf('/') + 1);

        User user = userMapper.toEntity(userRequestDTO);
        user.setId(userId);
        Balance balance = new Balance(1L,"azn", BigDecimal.valueOf(100));
        user.setBalance(balance);

        BalanceDTO balanceDTO = balanceMapper.toDTO(balance);
        UserResponseDTO userResponseDTO = userMapper.toDTO(userRepository.save(user));
        userResponseDTO.setBalanceDTO(balanceDTO);
        return userResponseDTO;
    }

    @Override
    public UserLoginResponseDTO login(UserLoginRequestDTO userRequest){
        String tokenUrl = "https://auth.md7.info/auth/realms/team1/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("username", userRequest.getEmail());
        body.add("password", userRequest.getPassword());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserLoginResponseDTO> response;

        try {
            response = restTemplate.postForEntity(tokenUrl, request, UserLoginResponseDTO.class);

        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Failed to retrieve token");
        }

        UserLoginResponseDTO tokenResponseDTO = (UserLoginResponseDTO) response.getBody();
        UserLoginResponseDTO loginResponseDTO = new UserLoginResponseDTO();
        loginResponseDTO.setAccess_token(tokenResponseDTO.getAccess_token());
        loginResponseDTO.setRefresh_token(tokenResponseDTO.getRefresh_token());
        loginResponseDTO.setExpires_in(tokenResponseDTO.getExpires_in());
        loginResponseDTO.setRefresh_expires_in(tokenResponseDTO.getRefresh_expires_in());

        return loginResponseDTO;
    }

    @Override
    @Transactional
    public UserResponseDTO updateUser(String id, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,USER_NOT_FOUND + id));
        userMapper.updateUserFromUpdateRequestDTO(userUpdateRequest,user);
        userRepository.save(user);
        UserRepresentation userRepresentation = keycloak.realm(realm).users().get(id).toRepresentation();
        userMapper.updateUserRepresentation(userUpdateRequest,userRepresentation);
        keycloak.realm(realm).users().get(id).update(userRepresentation);
        return userMapper.toDTO(user);
    }

    @Override
    public void deleteUserById(String id) {
        keycloak.realm(realm).users().delete(id);
        userRepository.deleteUserById(id);
    }

    private UserRepresentation createUserRepresentation(UserRequestDTO userRequestDTO) {
        UserRepresentation userRepresentation = new UserRepresentation();

        userRepresentation.setUsername(userRequestDTO.getEmail());
        userRepresentation.setEmail(userRequestDTO.getEmail());
        userRepresentation.setFirstName(userRequestDTO.getFirstName());
        userRepresentation.setLastName(userRequestDTO.getLastName());
        userRepresentation.setEnabled(true);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(userRequestDTO.getPassword());
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        List<CredentialRepresentation> credentialsList = new ArrayList<>();
        credentialsList.add(credentialRepresentation);
        userRepresentation.setCredentials(credentialsList);

        return userRepresentation;
    }

}
