package group.aist.cinema.repository;

import group.aist.cinema.model.MovieStream;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieStreamRepository extends JpaRepository<MovieStream,Long> {
}
