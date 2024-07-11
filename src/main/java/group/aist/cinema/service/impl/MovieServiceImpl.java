package group.aist.cinema.service.impl;

import group.aist.cinema.dto.common.MovieDTO;
import group.aist.cinema.mapper.MovieMapper;
import group.aist.cinema.model.Movie;
import group.aist.cinema.repository.MovieRepository;
import group.aist.cinema.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Override
    public Page<MovieDTO> getAllMovies(Pageable pageable) {
        Page<Movie> movies = movieRepository.findAll(pageable);
        return movies.map(movieMapper::mapToDto);
    }

    @Override
    public MovieDTO getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
        return movieMapper.mapToDto(movie);
    }

    @Override
    public List<MovieDTO> getMovieByName(String name) {
        List<Movie> movies = movieRepository.findByName(name);
        return movies.stream().map(movieMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public MovieDTO createMovie(MovieDTO movieDto) {
        Movie movie = movieMapper.mapToEntity(movieDto);
        return movieMapper.mapToDto(movieRepository.save(movie));
    }

    @Override
    public MovieDTO updateMovie(Long id, MovieDTO movieDto) {
        Movie existingMovie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
        movieMapper.updateMovieFromDTO(movieDto, existingMovie);
        return movieMapper.mapToDto(movieRepository.save(existingMovie));
    }

    @Override
    public void deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new RuntimeException("Movie not found with id: " + id);
        }
        movieRepository.deleteById(id);
    }
}
