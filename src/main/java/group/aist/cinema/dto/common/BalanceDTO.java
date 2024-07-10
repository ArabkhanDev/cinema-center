package group.aist.cinema.dto.common;

import group.aist.cinema.model.Balance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceDTO {
    private Long id;
    private String currency;
    private BigDecimal amount;

    public BalanceDTO(Balance balance) {
        this.id = balance.getId();
        this.currency = balance.getCurrency();
        this.amount = balance.getAmount();
    }
}
