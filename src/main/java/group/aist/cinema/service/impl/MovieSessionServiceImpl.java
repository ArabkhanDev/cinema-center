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
import group.aist.cinema.service.MovieStreamService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieSessionServiceImpl implements MovieSessionService {

    private final MovieSessionRepository movieSessionRepository;
    private final MovieSessionMapper movieSessionMapper;
    private final MovieRepository movieRepository;
    private final HallRepository hallRepository;
    private final MovieStreamRepository movieStreamRepository;
    private final MovieStreamService movieStreamService;

    @Override
    public Page<MovieSessionResponseDTO> getAllMovieSessions(Pageable pageable) {
        Page<MovieSession> movieSessions = movieSessionRepository.findAllBy(pageable);
        return movieSessions.map(movieSessionMapper::mapToResponseDTO);
    }

    @Override
    public MovieSessionResponseDTO getMovieSessionById(Long id) {
        MovieSession movieSession = movieSessionRepository.findMovieSessionById(id).orElseThrow(() -> new RuntimeException("There is no id with this movie session " + id));
        return movieSessionMapper.mapToResponseDTO(movieSession);
    }

    @Override
    public List<MovieSessionResponseDTO> getMovieSessionByMovieId(Long movieId) {
        return List.of();
    }

    @Override
    @Transactional
    public MovieSessionResponseDTO createMovieSession(MovieSessionRequestDTO movieSessionRequestDTO) {
        MovieSession movieSession = movieSessionMapper.mapToEntity(movieSessionRequestDTO);
        movieSession.setMovie(movieRepository.findById(movieSessionRequestDTO.getMovieId()).orElseThrow(() -> new RuntimeException("There is no id with this movie session ")));
        movieSession.setHall(hallRepository.findById(movieSessionRequestDTO.getHallId()).orElseThrow(() -> new RuntimeException("There is no id with this hall ")));
        movieSession.setMovieStream(movieStreamRepository.findById(movieSessionRequestDTO.getMovieStreamId()).orElseThrow(() -> new RuntimeException("There is no id with this movie stream ")));
        return movieSessionMapper.mapToResponseDTO(movieSessionRepository.save(movieSession));
    }

    @Override
    @Transactional
    public MovieSessionResponseDTO updateMovieSession(Long id, MovieSessionRequestDTO movieSessionRequestDTO) {
        MovieSession movieSession = movieSessionRepository.findMovieSessionById(id).orElseThrow(() -> new RuntimeException("There is no id with this movie session " + id));
        Hall hall = hallRepository.findById(movieSessionRequestDTO.getHallId()).orElseThrow(() -> new RuntimeException("no hall"));
        MovieStream movieStream = movieStreamRepository.findById(movieSessionRequestDTO.getMovieStreamId()).orElseThrow(() -> new RuntimeException("no hall"));
        Movie movie = movieRepository.findById(movieSessionRequestDTO.getMovieId()).orElseThrow(() -> new RuntimeException("no hall"));
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
