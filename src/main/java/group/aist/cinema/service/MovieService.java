package group.aist.cinema.service;

import group.aist.cinema.dto.request.MovieRequestDTO;
import group.aist.cinema.dto.request.MovieUpdateRequest;
import group.aist.cinema.dto.response.MovieResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface MovieService {

    Page<MovieResponseDTO> getAllMovies(Pageable pageable);

    MovieResponseDTO getMovieById(Long id);

    List<MovieResponseDTO> getMovieByName(String name);

    public MovieResponseDTO createMovie(MovieRequestDTO movieDto) throws IOException;

    MovieResponseDTO updateMovie(Long id, MovieUpdateRequest movieDto);

    void deleteMovie(Long id);

}
