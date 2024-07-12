package group.aist.cinema.service;

import group.aist.cinema.dto.request.MovieSessionRequestDTO;
import group.aist.cinema.dto.response.MovieSessionResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieSessionService {

    Page<MovieSessionResponseDTO> getAllMovieSessions(Pageable pageable);

    MovieSessionResponseDTO getMovieSessionById(Long id);

    List<MovieSessionResponseDTO> getMovieSessionByMovieId(Long movieId);

    MovieSessionResponseDTO createMovieSession(MovieSessionRequestDTO movieSessionRequestDTO);

    MovieSessionResponseDTO updateMovieSession(Long id, MovieSessionRequestDTO movieSessionRequestDTO);

    void deleteMovieSession(Long id);
}
