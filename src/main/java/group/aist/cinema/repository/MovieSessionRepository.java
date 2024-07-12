package group.aist.cinema.repository;

import group.aist.cinema.dto.response.MovieSessionResponseDTO;
import group.aist.cinema.model.MovieSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieSessionRepository extends JpaRepository<MovieSession, Long> {

    @EntityGraph(attributePaths = {"movie","hall","movieStream","movieStream.dubbingLanguages","movieStream.subtitleLanguages"})
    Page<MovieSession> findAllBy(Pageable pageable);

    @EntityGraph(attributePaths = {"movie","hall","movieStream","movieStream.dubbingLanguages","movieStream.subtitleLanguages"})
    Optional<MovieSession> findMovieSessionById(Long id);


}
