package group.aist.cinema.controller;

import group.aist.cinema.dto.common.MovieDTO;
import group.aist.cinema.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public Page<MovieDTO> getAllMovieStreams(Pageable pageable) {
        return movieService.getAllMovies(pageable);
    }

    @GetMapping("/{id}")
    public MovieDTO getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @PostMapping
    public MovieDTO createMovie(@RequestBody MovieDTO movieDTO) {
        return movieService.createMovie(movieDTO);
    }

    @PutMapping("/{id}")
    public MovieDTO updateMovie(@PathVariable Long id, @RequestBody MovieDTO movieDTO) {
        return movieService.updateMovie(id, movieDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
    }
}
