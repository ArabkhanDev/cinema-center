package group.aist.cinema.service;

import group.aist.cinema.dto.request.MovieStreamRequestDTO;
import group.aist.cinema.dto.response.MovieStreamResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieStreamService {

    Page<MovieStreamResponseDTO> getAllMovieStreams(Pageable pageable);

    MovieStreamResponseDTO getMovieStreamById(Long id);

    MovieStreamResponseDTO createMovieStream(MovieStreamRequestDTO movieStreamRequestDto);

    void addDubbingLanguageToMovieStream(Long movieStreamId, Long dubbingLanguageId);

    void addSubtitleLanguageToMovieStream(Long movieStreamId, Long subtitleLanguageId);

    MovieStreamResponseDTO updateMovieStream(Long id, MovieStreamRequestDTO movieStreamRequestDto);

    void deleteMovieStream(Long id);
}
