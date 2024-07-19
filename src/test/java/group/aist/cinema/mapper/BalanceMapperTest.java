package group.aist.cinema.mapper;

import group.aist.cinema.dto.common.BalanceDTO;
import group.aist.cinema.model.Balance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BalanceMapperTest {

    private BalanceMapper balanceMapper;

    @BeforeEach
    public void setUp() {
        balanceMapper = Mappers.getMapper(BalanceMapper.class);
    }

    @Test
    public void testToDTO() {
        Balance balance = new Balance();
        balance.setId(1L);
        balance.setCurrency("USD");
        balance.setAmount(BigDecimal.valueOf(100.00));

        BalanceDTO balanceDTO = balanceMapper.toDTO(balance);

        assertEquals(1L, balanceDTO.getId());
        assertEquals("USD", balanceDTO.getCurrency());
        assertEquals(BigDecimal.valueOf(100.00), balanceDTO.getAmount());
    }

    @Test
    public void testToEntity() {
        BalanceDTO balanceDTO = new BalanceDTO();
        balanceDTO.setId(1L);
        balanceDTO.setCurrency("USD");
        balanceDTO.setAmount(BigDecimal.valueOf(100.00));

        Balance balance = balanceMapper.toEntity(balanceDTO);

        assertEquals(1L, balance.getId());
        assertEquals("USD", balance.getCurrency());
        assertEquals(BigDecimal.valueOf(100.00), balance.getAmount());
    }

    @Test
    public void testUpdateBalanceFromDTO() {
        Balance balance = new Balance();
        balance.setId(1L);
        balance.setCurrency("EUR");
        balance.setAmount(BigDecimal.valueOf(50.00));

        BalanceDTO balanceDTO = new BalanceDTO();
        balanceDTO.setCurrency("USD");
        balanceDTO.setAmount(BigDecimal.valueOf(100.00));

        balanceMapper.updateBalanceFromDTO(balanceDTO, balance);

        assertEquals(1L, balance.getId());
        assertEquals("USD", balance.getCurrency());
        assertEquals(BigDecimal.valueOf(100.00), balance.getAmount());
    }
}
