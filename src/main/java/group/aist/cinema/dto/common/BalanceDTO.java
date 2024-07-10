package group.aist.cinema.dto.common;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceDTO {

    private Long id;

    private String currency;

    private BigDecimal amount;

}
