package group.aist.cinema.service;

import group.aist.cinema.dto.common.MovieStreamDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieStreamService {

    Page<MovieStreamDto> getAllMovieStreams(Pageable pageable);

    MovieStreamDto getMovieStreamById(Long id);

    MovieStreamDto createMovieStream(MovieStreamDto movieStreamDto);

    MovieStreamDto updateMovieStream(Long id, MovieStreamDto movieStreamDto);

    void deleteMovieStream(Long id);
}
