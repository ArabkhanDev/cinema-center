package group.aist.cinema.controller;

import group.aist.cinema.dto.request.MovieStreamRequestDTO;
import group.aist.cinema.dto.response.MovieStreamResponseDTO;
import group.aist.cinema.service.MovieStreamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/movie-streams")
@RequiredArgsConstructor
public class MovieStreamController {

    private final MovieStreamService movieStreamService;

    @GetMapping
    public Page<MovieStreamResponseDTO> getAllMovieStreams(Pageable pageable) {
        return movieStreamService.getAllMovieStreams(pageable);
    }

    @GetMapping("/{id}")
    public MovieStreamResponseDTO getMovieStreamById(@PathVariable Long id) {
        return movieStreamService.getMovieStreamById(id);
    }

    @PostMapping
    public MovieStreamResponseDTO createMovieStream(@RequestBody MovieStreamRequestDTO movieStreamRequestDto) {
        return movieStreamService.createMovieStream(movieStreamRequestDto);
    }

    @PutMapping("/{id}")
    public MovieStreamResponseDTO updateMovieStream(@PathVariable Long id, @RequestBody MovieStreamRequestDTO movieStreamRequestDto) {
        return movieStreamService.updateMovieStream(id, movieStreamRequestDto);
    }

    @PostMapping("/{streamId}/dubbing/{dubbingId}")
    public void addDubbingLanguage(@PathVariable Long streamId, @PathVariable Long dubbingId) {
        movieStreamService.addDubbingLanguageToMovieStream(streamId, dubbingId);
    }

    @PostMapping("/{streamId}/subtitle/{subtitleId}")
    public void addSubtitleLanguage(@PathVariable Long streamId, @PathVariable Long subtitleId) {
        movieStreamService.addSubtitleLanguageToMovieStream(streamId, subtitleId);
    }

    @DeleteMapping("/{id}")
    public void deleteMovieStream(@PathVariable Long id) {
        movieStreamService.deleteMovieStream(id);
    }
}
