package group.aist.cinema.repository;

import group.aist.cinema.model.MovieStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MovieStreamRepository extends JpaRepository<MovieStream,Long> {

    @EntityGraph(attributePaths = {"dubbingLanguages", "subtitleLanguages"})
    Page<MovieStream> findAllBy(Pageable pageable);
}
