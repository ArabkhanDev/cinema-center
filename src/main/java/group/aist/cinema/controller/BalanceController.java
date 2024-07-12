package group.aist.cinema.controller;

import group.aist.cinema.dto.common.BalanceDTO;
import group.aist.cinema.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/balances")
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;

    @GetMapping("/{id}")
    public BalanceDTO getBalanceById(@PathVariable Long id) {
        return balanceService.getBalanceById(id);
    }

    @PostMapping()
    public BalanceDTO createBalance(BalanceDTO balanceDTO) {
        return balanceService.createBalance(balanceDTO);
    }

    @PutMapping("/{id}")
    public BalanceDTO updateBalance(@PathVariable Long id, BalanceDTO balanceDTO) {
        return balanceService.updateBalance(id, balanceDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBalance(@PathVariable Long id) {
        balanceService.deleteBalance(id);
    }

}
