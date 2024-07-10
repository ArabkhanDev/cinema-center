package group.aist.cinema.repository;

import group.aist.cinema.model.Hall;
import group.aist.cinema.model.Movie;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HallRepository extends JpaRepository<Hall, Long> {

    @EntityGraph(attributePaths = {"movies", "users"})
    List<Movie> findByName(String hall);

}
