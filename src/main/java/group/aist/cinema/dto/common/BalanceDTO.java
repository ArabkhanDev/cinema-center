package group.aist.cinema.dto.common;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceDTO {

    @NotNull(message = "Id cannot be null")
    private Long id;

    @NotNull(message = "Currency cannot be null")
    @Size(min = 3, max = 3, message = "Currency must be a 3-letter ISO code")
    private String currency;

    @NotNull(message = "Amount cannot be null")
    @Digits(integer = 10, fraction = 2, message = "Amount must be a valid decimal number with up to 10 integer digits and 2 fractional digits")
    private BigDecimal amount;

}
