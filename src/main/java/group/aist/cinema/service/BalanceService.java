package group.aist.cinema.service;

import group.aist.cinema.dto.common.BalanceDTO;

public interface BalanceService {

    BalanceDTO getBalanceById(Long id);

    BalanceDTO createBalance(BalanceDTO balanceDTO);

    BalanceDTO updateBalance(Long id, BalanceDTO balanceDTO);

    void deleteBalance(Long id);

}
