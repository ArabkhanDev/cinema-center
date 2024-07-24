package group.aist.cinema.repository;

import group.aist.cinema.model.DubbingLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DubbingLanguageRepositoryTest {

    @Autowired
    private DubbingLanguageRepository dubbingLanguageRepository;

    private DubbingLanguage dubbingLanguage;

    @BeforeEach
    void setUp() {
        dubbingLanguage = new DubbingLanguage();
        dubbingLanguage.setName("English");
        dubbingLanguage.setIsoCode("EN");
        dubbingLanguage = dubbingLanguageRepository.save(dubbingLanguage);
    }

    @Test
    void testFindById() {
        Optional<DubbingLanguage> foundDubbingLanguage = dubbingLanguageRepository.findById(dubbingLanguage.getId());
        assertThat(foundDubbingLanguage).isPresent();
        assertThat(foundDubbingLanguage.get().getName()).isEqualTo("English");
        assertThat(foundDubbingLanguage.get().getIsoCode()).isEqualTo("EN");
    }

    @Test
    void testSave() {
        dubbingLanguage = new DubbingLanguage();
        dubbingLanguage.setName("Azerbaijan");
        dubbingLanguage.setIsoCode("AZ");
        DubbingLanguage savedDubbingLanguage = dubbingLanguageRepository.save(dubbingLanguage);
        assertThat(savedDubbingLanguage).isNotNull();
        assertThat(savedDubbingLanguage.getName()).isEqualTo("French");
        assertThat(savedDubbingLanguage.getIsoCode()).isEqualTo("FR");
    }

    @Test
    void testDelete() {
        dubbingLanguageRepository.deleteById(dubbingLanguage.getId());
        Optional<DubbingLanguage> deletedDubbingLanguage = dubbingLanguageRepository.findById(dubbingLanguage.getId());
        assertThat(deletedDubbingLanguage).isEmpty();
    }

    @Test
    void testFindAll() {
        Iterable<DubbingLanguage> dubbingLanguages = dubbingLanguageRepository.findAll();
        assertThat(dubbingLanguages).isNotEmpty();
    }
}
