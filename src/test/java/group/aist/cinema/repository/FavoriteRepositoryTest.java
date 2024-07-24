package group.aist.cinema.repository;

import group.aist.cinema.model.Favorite;
import group.aist.cinema.model.Movie;
import group.aist.cinema.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class FavoriteRepositoryTest {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    private User user;
    private Movie movie;
    private Favorite favorite;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId("user123");
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPhone("1234567890");
        user = userRepository.save(user);

        movie = new Movie();
        movie.setName("Test Movie");
        movie.setDescription("Test Description");
        movie.setGenre("Action");
        movie.setReleaseDate(LocalDate.of(2023, 7, 15));
        movie.setAgeRestriction((short) 18);
        movie.setDuration("120 minutes");
        movie.setImage("test.jpg");
        movie = movieRepository.save(movie);

        favorite = new Favorite();
        favorite.setName("Test Favorite");
        favorite.setUser(user);
        favorite.getMovies().add(movie);
        favorite = favoriteRepository.save(favorite);
    }

    @Test
    void testFindAllBy() {
        Page<Favorite> favorites = favoriteRepository.findAllBy(PageRequest.of(0, 10));
        assertThat(favorites).isNotEmpty();
        assertThat(favorites.getContent()).contains(favorite);
    }

    @Test
    void testFindAllByUserId() {
        Page<Favorite> favorites = favoriteRepository.findAllByUserId(Long.valueOf(user.getId()), PageRequest.of(0, 10));
        assertThat(favorites).isNotEmpty();
        assertThat(favorites.getContent()).contains(favorite);
    }

    @Test
    void testFindByName() {
        Optional<Favorite> foundFavorite = favoriteRepository.findByName("Test Favorite");
        assertThat(foundFavorite).isPresent();
        assertThat(foundFavorite.get()).isEqualTo(favorite);
    }

    @Test
    void testFindByName_NotFound() {
        Optional<Favorite> foundFavorite = favoriteRepository.findByName("Nonexistent Favorite");
        assertThat(foundFavorite).isNotPresent();
    }
}
