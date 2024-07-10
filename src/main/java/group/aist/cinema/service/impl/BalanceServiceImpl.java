package group.aist.cinema.service.impl;

import group.aist.cinema.dto.common.BalanceDTO;
import group.aist.cinema.mapper.BalanceMapper;
import group.aist.cinema.model.Balance;
import group.aist.cinema.repository.BalanceRepository;
import group.aist.cinema.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

     private final BalanceRepository balanceRepository;
     private final BalanceMapper balanceMapper;


     @Override
     public BalanceDTO getBalanceById(Long id) {
          return balanceMapper.toDTO(balanceRepository.findById(id)
                  .orElseThrow(() -> new RuntimeException("Balance not found with id " + id)));
     }

     @Override
     public BalanceDTO createBalance(BalanceDTO balanceDTO) {
          Balance balance = balanceMapper.toEntity(balanceDTO);
          return balanceMapper.toDTO(balanceRepository.save(balance));
     }

     @Override
     public BalanceDTO updateBalance(Long id, BalanceDTO balanceDTO) {
          Balance balance = balanceRepository.findById(id)
                  .orElseThrow(() -> new RuntimeException("Balance not found with id " + id));
          balanceMapper.updateBalanceFromDTO(balanceDTO, balance);
          return balanceMapper.toDTO(balanceRepository.save(balance));
     }

     @Override
     public void deleteBalance(Long id) {
          balanceRepository.deleteById(id);
     }
}
