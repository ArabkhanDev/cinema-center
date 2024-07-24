package group.aist.cinema.repository;

import group.aist.cinema.model.Hall;
import group.aist.cinema.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class HallRepositoryTest {

    @Autowired
    private HallRepository hallRepository;

    private Hall hallWithAvailableSeats;
    private Hall hallWithoutAvailableSeats;

    @BeforeEach
    void setUp() {
        hallWithAvailableSeats = new Hall();
        hallWithAvailableSeats.setName("Main Hall");
        hallWithAvailableSeats.setSeatCount(150);
        hallWithAvailableSeats.setAvailableSeat(100);
        hallWithAvailableSeats = hallRepository.save(hallWithAvailableSeats);

        hallWithoutAvailableSeats = new Hall();
        hallWithoutAvailableSeats.setName("Small Hall");
        hallWithoutAvailableSeats.setSeatCount(50);
        hallWithoutAvailableSeats.setAvailableSeat(0);
        hallWithoutAvailableSeats = hallRepository.save(hallWithoutAvailableSeats);
    }

    @Test
    void testFindByNameWithMovies() {
        List<Movie> movies = hallRepository.findByName("Main Hall");
        assertThat(movies).isNotEmpty();
        assertThat(movies).hasSize(2);

        Movie foundMovie1 = movies.stream().filter(movie -> movie.getName().equals("Action Movie")).findFirst().orElse(null);
        assertThat(foundMovie1).isNotNull();
        assertThat(foundMovie1.getName()).isEqualTo("Action Movie");

        Movie foundMovie2 = movies.stream().filter(movie -> movie.getName().equals("Comedy Movie")).findFirst().orElse(null);
        assertThat(foundMovie2).isNotNull();
        assertThat(foundMovie2.getName()).isEqualTo("Comedy Movie");
    }

    @Test
    void testFindByNameWithoutMovies() {
        List<Movie> movies = hallRepository.findByName("Small Hall");
        assertThat(movies).isEmpty();
    }

    @Test
    void testFindByNameNotFound() {
        List<Movie> movies = hallRepository.findByName("Non-Existing Hall");
        assertThat(movies).isEmpty();
    }
}
