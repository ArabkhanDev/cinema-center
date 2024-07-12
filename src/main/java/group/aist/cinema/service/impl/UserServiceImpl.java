package group.aist.cinema.service.impl;

import group.aist.cinema.dto.request.UserRequestDTO;
import group.aist.cinema.dto.response.UserResponseDTO;
import group.aist.cinema.mapper.UserMapper;
import group.aist.cinema.model.Balance;
import group.aist.cinema.model.User;
import group.aist.cinema.repository.BalanceRepository;
import group.aist.cinema.repository.UserRepository;
import group.aist.cinema.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BalanceRepository balanceRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> getAllUsers(Pageable pageable){
        Page<User> users = userRepository.findAll(pageable);
        return users.map(userMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return userMapper.toDTO(user);
    }

    @Override
    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = userMapper.toEntity(userRequestDTO);
        Balance balance = balanceRepository.findById(userRequestDTO.getBalanceId())
                .orElseThrow(() -> new RuntimeException("Balance not found with id " + userRequestDTO.getBalanceId()));

        user.setBalance(balance);
        return userMapper.toDTO(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        Balance balance = balanceRepository.findById(userRequestDTO.getBalanceId())
                .orElseThrow(() -> new RuntimeException("Balance not found with id " + userRequestDTO.getBalanceId()));

        user.setBalance(balance);
        userMapper.updateUserFromDTO(userRequestDTO, user);
        return userMapper.toDTO(userRepository.save(user));
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

}
