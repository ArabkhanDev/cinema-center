package group.aist.cinema.repository;

import group.aist.cinema.model.Balance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BalanceRepositoryTest {

    @Autowired
    private BalanceRepository balanceRepository;

    private Balance balance;

    @BeforeEach
    void setUp() {
        balance = Balance.builder()
                .currency("USD")
                .amount(new BigDecimal("100.00"))
                .build();
        balance = balanceRepository.save(balance);
    }

    @Test
    void testFindById() {
        Optional<Balance> foundBalance = balanceRepository.findById(balance.getId());
        assertThat(foundBalance).isPresent();
        assertThat(foundBalance.get().getCurrency()).isEqualTo("USD");
        assertThat(foundBalance.get().getAmount()).isEqualTo(new BigDecimal("100.00"));
    }

    @Test
    void testSave() {
        Balance newBalance = Balance.builder()
                .currency("EUR")
                .amount(new BigDecimal("200.00"))
                .build();
        Balance savedBalance = balanceRepository.save(newBalance);
        assertThat(savedBalance).isNotNull();
        assertThat(savedBalance.getCurrency()).isEqualTo("EUR");
        assertThat(savedBalance.getAmount()).isEqualTo(new BigDecimal("200.00"));
    }

    @Test
    void testDelete() {
        balanceRepository.deleteById(balance.getId());
        Optional<Balance> deletedBalance = balanceRepository.findById(balance.getId());
        assertThat(deletedBalance).isEmpty();
    }

    @Test
    void testFindAll() {
        Iterable<Balance> balances = balanceRepository.findAll();
        assertThat(balances).isNotEmpty();
    }
}
