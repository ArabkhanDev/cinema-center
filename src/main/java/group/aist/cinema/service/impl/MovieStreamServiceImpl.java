package group.aist.cinema.service.impl;

import group.aist.cinema.dto.request.MovieStreamRequestDTO;
import group.aist.cinema.dto.response.MovieStreamResponseDTO;
import group.aist.cinema.mapper.MovieStreamMapper;
import group.aist.cinema.model.DubbingLanguage;
import group.aist.cinema.model.MovieStream;
import group.aist.cinema.model.SubtitleLanguage;
import group.aist.cinema.repository.DubbingLanguageRepository;
import group.aist.cinema.repository.MovieStreamRepository;
import group.aist.cinema.repository.SubtitleLanguageRepository;
import group.aist.cinema.service.MovieStreamService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class MovieStreamServiceImpl implements MovieStreamService {

    private final MovieStreamRepository movieStreamRepository;

    private final MovieStreamMapper movieStreamMapper;
    private final DubbingLanguageRepository dubbingLanguageRepository;
    private final SubtitleLanguageRepository subtitleLanguageRepository;

    @Override
    @Transactional
    public Page<MovieStreamResponseDTO> getAllMovieStreams(Pageable pageable) {
        Page<MovieStream> movieStreams = movieStreamRepository.findAllBy(pageable);
        return movieStreams.map(movieStreamMapper::mapToResponseDTO);
    }

    @Override
    @Transactional
    public MovieStreamResponseDTO getMovieStreamById(Long id) {
        MovieStream movieStream = movieStreamRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Movie Stream not found with id: " + id));
        return checkSubtitle(movieStream);
    }

    @Override
    @Transactional
    public MovieStreamResponseDTO createMovieStream(MovieStreamRequestDTO movieStreamRequestDto) {
        MovieStream movieStream = movieStreamMapper.mapToEntity(movieStreamRequestDto);
        DubbingLanguage dubbingLanguage = dubbingLanguageRepository.findById(movieStreamRequestDto.getDubbingLanguageId())
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Movie Stream not found with id: " + movieStreamRequestDto.getDubbingLanguageId()));
        movieStream.getDubbingLanguages().add(dubbingLanguage);
        checkSubtitleLanguageOrAdd(movieStreamRequestDto, movieStream);
        return movieStreamMapper.mapToResponseDTO(movieStreamRepository.save(movieStream));
    }


    @Override
    @Transactional
    public void addDubbingLanguageToMovieStream(Long movieStreamId, Long dubbingLanguageId) {
        MovieStream movieStream = movieStreamRepository.findById(movieStreamId)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Movie Stream not found with id: " + movieStreamId));

        DubbingLanguage dubbingLanguage = dubbingLanguageRepository.findById(dubbingLanguageId)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Dubbing Language not found with id: " + dubbingLanguageId));

        movieStream.getDubbingLanguages().add(dubbingLanguage);
        movieStreamRepository.save(movieStream);
    }

    @Override
    @Transactional
    public void addSubtitleLanguageToMovieStream(Long movieStreamId, Long subtitleLanguageId) {
        MovieStream movieStream = movieStreamRepository.findById(movieStreamId)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Movie Stream not found with id: " + movieStreamId));

        SubtitleLanguage subtitleLanguage = subtitleLanguageRepository.findById(subtitleLanguageId)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Subtitle Language not found with id: " + subtitleLanguageId));

        movieStream.getSubtitleLanguages().add(subtitleLanguage);
        movieStreamRepository.save(movieStream);
    }

    @Override
    @Transactional
    public MovieStreamResponseDTO updateMovieStream(Long id, MovieStreamRequestDTO movieStreamRequestDto) {
        MovieStream movieStream = movieStreamRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Movie Stream not found with id: " + id));
        movieStreamMapper.updateMovieStreamFromDTO(movieStreamRequestDto, movieStream);
        return checkSubtitle(movieStream);
    }

    @Override
    @Transactional
    public void deleteMovieStream(Long id) {
        movieStreamRepository.deleteById(id);
    }



    private MovieStreamResponseDTO checkSubtitle(MovieStream movieStream) {
        MovieStreamResponseDTO dto = movieStreamMapper.mapToResponseDTO(movieStream);
        if (!movieStream.getHasSubtitle()) {
            dto.setSubtitleLanguages(Set.of());
        }
        return dto;
    }

    private void checkSubtitleLanguageOrAdd(MovieStreamRequestDTO movieStreamRequestDto, MovieStream movieStream) {
        if (movieStream.getHasSubtitle()) {
            SubtitleLanguage subtitleLanguage = subtitleLanguageRepository.findById(movieStreamRequestDto.getSubtitleLanguageId())
                    .orElseThrow(() -> new RuntimeException("Subtitle Language not found with id: " + movieStreamRequestDto.getSubtitleLanguageId()));
            movieStream.getSubtitleLanguages().add(subtitleLanguage);
        }
    }
}
