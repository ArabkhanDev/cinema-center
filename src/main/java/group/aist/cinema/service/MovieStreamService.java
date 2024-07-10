package group.aist.cinema.service;

import group.aist.cinema.dto.common.MovieStreamDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieStreamService {

    Page<MovieStreamDTO> getAllMovieStreams(Pageable pageable);

    MovieStreamDTO getMovieStreamById(Long id);

    MovieStreamDTO createMovieStream(MovieStreamDTO movieStreamDto);

    MovieStreamDTO updateMovieStream(Long id, MovieStreamDTO movieStreamDto);

    void deleteMovieStream(Long id);
}
