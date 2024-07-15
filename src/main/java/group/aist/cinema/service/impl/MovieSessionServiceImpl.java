package group.aist.cinema.service.impl;

import group.aist.cinema.dto.request.MovieSessionRequestDTO;
import group.aist.cinema.dto.response.MovieSessionResponseDTO;
import group.aist.cinema.mapper.MovieSessionMapper;
import group.aist.cinema.model.Hall;
import group.aist.cinema.model.Movie;
import group.aist.cinema.model.MovieSession;
import group.aist.cinema.model.MovieStream;
import group.aist.cinema.repository.HallRepository;
import group.aist.cinema.repository.MovieRepository;
import group.aist.cinema.repository.MovieSessionRepository;
import group.aist.cinema.repository.MovieStreamRepository;
import group.aist.cinema.service.MovieSessionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        MovieSession movieSession = movieSessionRepository.findMovieSessionById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"There is no id with this movie session"));
        return movieSessionMapper.mapToResponseDTO(movieSession);
    }

    @Override
    @Transactional
    public MovieSessionResponseDTO createMovieSession(MovieSessionRequestDTO movieSessionRequestDTO) {
        MovieSession movieSession = movieSessionMapper.mapToEntity(movieSessionRequestDTO);
        movieSession.setMovie(movieRepository.findById(movieSessionRequestDTO.getMovieId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"There is no id with this movie session")));
        movieSession.setHall(hallRepository.findById(movieSessionRequestDTO.getHallId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"There is no id with this movie session")));
        movieSession.setMovieStream(movieStreamRepository.findById(movieSessionRequestDTO.getMovieStreamId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"There is no id with this movie session")));
        return movieSessionMapper.mapToResponseDTO(movieSessionRepository.save(movieSession));
    }

    @Override
    @Transactional
    public MovieSessionResponseDTO updateMovieSession(Long id, MovieSessionRequestDTO movieSessionRequestDTO) {
        MovieSession movieSession = movieSessionRepository.findMovieSessionById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"There is no id with this movie session"));
        Hall hall = hallRepository.findById(movieSessionRequestDTO.getHallId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"There is no id with this movie session"));
        MovieStream movieStream = movieStreamRepository.findById(movieSessionRequestDTO.getMovieStreamId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"There is no id with this movie session"));
        Movie movie = movieRepository.findById(movieSessionRequestDTO.getMovieId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"There is no id with this movie session"));
        movieSession.setMovieStream(movieStream);
        movieSession.setMovie(movie);
        movieSession.setHall(hall);
        movieSessionMapper.updateMovieSessionFromDTO(movieSessionRequestDTO,movieSession);
        return movieSessionMapper.mapToResponseDTO(movieSessionRepository.save(movieSession));
    }

    @Override
    public void deleteMovieSession(Long id) {
        movieSessionRepository.deleteById(id);
    }
}
