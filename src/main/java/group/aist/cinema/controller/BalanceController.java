package group.aist.cinema.controller;

import group.aist.cinema.dto.common.BalanceDTO;
import group.aist.cinema.model.base.BaseResponse;
import group.aist.cinema.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/balances")
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public BaseResponse<Page<BalanceDTO>> getAllBalances(Pageable pageable){
        return BaseResponse.success(balanceService.getAllBalances(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public BaseResponse<BalanceDTO> getBalanceById(@PathVariable Long id) {
        return BaseResponse.success(balanceService.getBalanceById(id));
    }


    @PostMapping
//    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public BaseResponse<BalanceDTO> createBalance(BalanceDTO balanceDTO) {
        return BaseResponse.created(balanceService.createBalance(balanceDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public BaseResponse<BalanceDTO> updateBalance(@PathVariable Long id, BalanceDTO balanceDTO) {
        return BaseResponse.success(balanceService.updateBalance(id, balanceDTO));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public BaseResponse<Void> deleteBalance(@PathVariable Long id) {
        balanceService.deleteBalance(id);
        return BaseResponse.noContent();
    }

}
