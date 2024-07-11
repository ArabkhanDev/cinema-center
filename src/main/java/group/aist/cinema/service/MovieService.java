package group.aist.cinema.service;

import group.aist.cinema.dto.common.MovieDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieService {

    Page<MovieDTO> getAllMovies(Pageable pageable);

    MovieDTO getMovieById(Long id);

    List<MovieDTO> getMovieByName(String name);

    MovieDTO createMovie(MovieDTO movieDto);

    MovieDTO updateMovie(Long id, MovieDTO movieDto);

    void deleteMovie(Long id);

}
