package group.aist.cinema.service;

import group.aist.cinema.dto.common.BalanceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BalanceService {

    Page<BalanceDTO> getAllBalances(Pageable pageable);

    BalanceDTO getBalanceById(Long id);

    BalanceDTO createBalance(BalanceDTO balanceDTO);

    BalanceDTO updateBalance(Long id, BalanceDTO balanceDTO);

    void deleteBalance(Long id);

}
