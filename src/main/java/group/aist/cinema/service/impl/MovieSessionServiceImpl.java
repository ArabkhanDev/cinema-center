package group.aist.cinema.service.impl;

import group.aist.cinema.dto.request.MovieSessionRequestDTO;
import group.aist.cinema.dto.response.MovieSessionResponseDTO;
import group.aist.cinema.mapper.MovieSessionMapper;
import group.aist.cinema.model.MovieSession;
import group.aist.cinema.repository.HallRepository;
import group.aist.cinema.repository.MovieRepository;
import group.aist.cinema.repository.MovieSessionRepository;
import group.aist.cinema.repository.MovieStreamRepository;
import group.aist.cinema.service.MovieSessionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static group.aist.cinema.util.ExceptionMessages.*;
import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
public class MovieSessionServiceImpl implements MovieSessionService {

    private final MovieSessionRepository movieSessionRepository;
    private final MovieSessionMapper movieSessionMapper;
    private final MovieRepository movieRepository;
    private final HallRepository hallRepository;
    private final MovieStreamRepository movieStreamRepository;

    @Override
    public Page<MovieSessionResponseDTO> getAllMovieSessions(Pageable pageable) {
        Page<MovieSession> movieSessions = movieSessionRepository.findAllBy(pageable);
        return movieSessions.map(movieSessionMapper::mapToResponseDTO);
    }

    @Override
    public MovieSessionResponseDTO getMovieSessionById(Long id) {
        MovieSession movieSession = movieSessionRepository.findMovieSessionById(id)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST,MOVIE_SESSION_NOT_FOUND));
        return movieSessionMapper.mapToResponseDTO(movieSession);
    }

    @Override
    @Transactional
    public MovieSessionResponseDTO createMovieSession(MovieSessionRequestDTO movieSessionRequestDTO) {
        MovieSession movieSession = movieSessionMapper.mapToEntity(movieSessionRequestDTO);
        setAllRelations(movieSessionRequestDTO, movieSession);
        return movieSessionMapper.mapToResponseDTO(movieSessionRepository.save(movieSession));
    }

    @Override
    @Transactional
    public MovieSessionResponseDTO updateMovieSession(Long id, MovieSessionRequestDTO movieSessionRequestDTO) {
        MovieSession movieSession = movieSessionRepository.findMovieSessionById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,MOVIE_SESSION_NOT_FOUND));
        setAllRelations(movieSessionRequestDTO, movieSession);
        movieSessionMapper.updateMovieSessionFromDTO(movieSessionRequestDTO,movieSession);
        return movieSessionMapper.mapToResponseDTO(movieSessionRepository.save(movieSession));
    }

    @Override
    public void deleteMovieSession(Long id) {
        movieSessionRepository.deleteById(id);
    }

    private void setAllRelations(MovieSessionRequestDTO movieSessionRequestDTO, MovieSession movieSession) {
        movieSession.setMovie(movieRepository.findById(movieSessionRequestDTO.getMovieId()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, MOVIE_NOT_FOUND)));
        movieSession.setHall(hallRepository.findById(movieSessionRequestDTO.getHallId()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND,HALL_NOT_FOUND)));
        movieSession.setMovieStream(movieStreamRepository.findById(movieSessionRequestDTO.getMovieStreamId()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND,MOVIE_STREAM_NOT_FOUND)));
    }
}
