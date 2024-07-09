package group.aist.cinema.repository;

import group.aist.cinema.model.MovieStream;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieStreamRepository extends JpaRepository<MovieStream,Long> {

    List<MovieStream> findMovieStreamByDubbingType(String dubbingType);
}
