package group.aist.cinema.controller;

import group.aist.cinema.dto.request.MovieStreamRequestDTO;
import group.aist.cinema.dto.response.MovieStreamResponseDTO;
import group.aist.cinema.model.base.BaseResponse;
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
    public BaseResponse<Page<MovieStreamResponseDTO>> getAllMovieStreams(Pageable pageable) {
        return BaseResponse.success(movieStreamService.getAllMovieStreams(pageable));
    }

    @GetMapping("/{id}")
    public BaseResponse<MovieStreamResponseDTO> getMovieStreamById(@PathVariable Long id) {
        return BaseResponse.success(movieStreamService.getMovieStreamById(id));
    }

    @PostMapping
    public BaseResponse<MovieStreamResponseDTO> createMovieStream(@RequestBody MovieStreamRequestDTO movieStreamRequestDto) {
        return BaseResponse.created(movieStreamService.createMovieStream(movieStreamRequestDto));
    }

    @PutMapping("/{id}")
    public BaseResponse<MovieStreamResponseDTO> updateMovieStream(@PathVariable Long id, @RequestBody MovieStreamRequestDTO movieStreamRequestDto) {
        return BaseResponse.success(movieStreamService.updateMovieStream(id, movieStreamRequestDto));
    }

    @PostMapping("/{streamId}/dubbing/{dubbingId}")
    public BaseResponse<Void> addDubbingLanguage(@PathVariable Long streamId, @PathVariable Long dubbingId) {
        movieStreamService.addDubbingLanguageToMovieStream(streamId, dubbingId);
        return BaseResponse.created();
    }

    @PostMapping("/{streamId}/subtitle/{subtitleId}")
    public BaseResponse<Void> addSubtitleLanguage(@PathVariable Long streamId, @PathVariable Long subtitleId) {
        movieStreamService.addSubtitleLanguageToMovieStream(streamId, subtitleId);
        return BaseResponse.created();
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteMovieStream(@PathVariable Long id) {
        movieStreamService.deleteMovieStream(id);
        return BaseResponse.noContent();
    }
}
