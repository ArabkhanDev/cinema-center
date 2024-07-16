package group.aist.cinema.controller;

import group.aist.cinema.dto.request.MovieSessionRequestDTO;
import group.aist.cinema.dto.response.MovieSessionResponseDTO;
import group.aist.cinema.model.base.BaseResponse;
import group.aist.cinema.service.MovieSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/movie-sessions")
@RequiredArgsConstructor
public class MovieSessionController {

    private final MovieSessionService movieSessionService;

    @GetMapping
    public BaseResponse<Page<MovieSessionResponseDTO>> getAllMovieSessions(Pageable pageable) {
        return BaseResponse.success(movieSessionService.getAllMovieSessions(pageable));
    }

    @GetMapping("/{id}")
    public BaseResponse<MovieSessionResponseDTO> getMovieSessionById(@PathVariable Long id) {
        return BaseResponse.success(movieSessionService.getMovieSessionById(id));
    }

    @PostMapping
    public BaseResponse<MovieSessionResponseDTO> createMovieSession(@RequestBody MovieSessionRequestDTO movieSessionRequestDTO) {
        return BaseResponse.created(movieSessionService.createMovieSession(movieSessionRequestDTO));
    }

    @PutMapping("/{id}")
    public BaseResponse<MovieSessionResponseDTO> updateMovieSession(@PathVariable Long id, @RequestBody MovieSessionRequestDTO movieSessionRequestDTO) {
        return BaseResponse.success(movieSessionService.updateMovieSession(id, movieSessionRequestDTO));
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteMovieSession(@PathVariable Long id) {
        movieSessionService.deleteMovieSession(id);
        return BaseResponse.noContent();
    }
}
