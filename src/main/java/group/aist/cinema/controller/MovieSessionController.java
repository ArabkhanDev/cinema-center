package group.aist.cinema.controller;

import group.aist.cinema.dto.request.MovieSessionRequestDTO;
import group.aist.cinema.dto.response.MovieSessionResponseDTO;
import group.aist.cinema.service.MovieSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/movie-sessions")
@RequiredArgsConstructor
public class MovieSessionController {

    private final MovieSessionService movieSessionService;

    @GetMapping
    public Page<MovieSessionResponseDTO> getAllMovieSessions(Pageable pageable) {
        return movieSessionService.getAllMovieSessions(pageable);
    }

    @GetMapping("/{id}")
    public MovieSessionResponseDTO getMovieSessionById(@PathVariable Long id) {
        return movieSessionService.getMovieSessionById(id);
    }

    @PostMapping
    public MovieSessionResponseDTO createMovieSession(@RequestBody MovieSessionRequestDTO movieSessionRequestDTO) {
        return movieSessionService.createMovieSession(movieSessionRequestDTO);
    }

    @PutMapping("/{id}")
    public MovieSessionResponseDTO updateMovieSession(@PathVariable Long id, @RequestBody MovieSessionRequestDTO movieSessionRequestDTO) {
        return movieSessionService.updateMovieSession(id, movieSessionRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteMovieSession(@PathVariable Long id) {
        movieSessionService.deleteMovieSession(id);
    }
}
