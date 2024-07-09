package group.aist.cinema.repository;

import group.aist.cinema.model.Favorite;
import group.aist.cinema.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Movie> findByName(Long userId);
}
