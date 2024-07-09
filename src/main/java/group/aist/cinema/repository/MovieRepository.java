package group.aist.cinema.repository;

import group.aist.cinema.model.Movie;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @EntityGraph(attributePaths = {"movies", "users"})
    Movie findByName(String name);

    @EntityGraph(attributePaths = {"movies", "users"})
    List<Movie> findByReleaseDate(LocalDate releaseDate);

    @EntityGraph(attributePaths = {"movies", "users"})
    List<Movie> findByHallName(String hall);

    @EntityGraph(attributePaths = {"movies", "users"})
    List<Movie> findByFavourite(String favorite);
}
