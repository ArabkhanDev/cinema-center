package group.aist.cinema.repository;

import group.aist.cinema.model.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

   @EntityGraph(attributePaths = {"movies", "user"})
   Page<Favorite> findAllBy(Pageable pageable);

   @EntityGraph(attributePaths = {"movies", "user"})
   List<Favorite> findAll();

   @EntityGraph(attributePaths = {"movies", "user"})
   Page<Favorite> findAllByUserId(Long userId, Pageable pageable);

   @EntityGraph(attributePaths = {"movies", "user"})
   Optional<Favorite> findByName(String name);
}
