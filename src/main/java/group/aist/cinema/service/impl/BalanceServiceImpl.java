package group.aist.cinema.service.impl;

import group.aist.cinema.dto.common.BalanceDTO;
import group.aist.cinema.mapper.BalanceMapper;
import group.aist.cinema.model.Balance;
import group.aist.cinema.repository.BalanceRepository;
import group.aist.cinema.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static group.aist.cinema.util.ExceptionMessages.BALANCE_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

     private final BalanceRepository balanceRepository;
     private final BalanceMapper balanceMapper;


     @Override
     public Page<BalanceDTO> getAllBalances(Pageable pageable) {
          return balanceRepository.findAll(pageable).map(balanceMapper::toDTO);
     }

     @Override
     public BalanceDTO getBalanceById(Long id) {
          return balanceMapper.toDTO(balanceRepository.findById(id)
                  .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, BALANCE_NOT_FOUND + id)));
     }

     @Override
     public BalanceDTO createBalance(BalanceDTO balanceDTO) {
          Balance balance = balanceMapper.toEntity(balanceDTO);
          return balanceMapper.toDTO(balanceRepository.save(balance));
     }

     @Override
     public BalanceDTO updateBalance(Long id, BalanceDTO balanceDTO) {
          Balance balance = balanceRepository.findById(id)
                  .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, BALANCE_NOT_FOUND + id));
          balanceMapper.updateBalanceFromDTO(balanceDTO, balance);
          return balanceMapper.toDTO(balanceRepository.save(balance));
     }

     @Override
     public void deleteBalance(Long id) {
          balanceRepository.deleteById(id);
     }
}
