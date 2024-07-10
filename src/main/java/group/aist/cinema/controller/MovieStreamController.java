package group.aist.cinema.controller;

import group.aist.cinema.dto.common.MovieStreamDTO;
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
    public Page<MovieStreamDTO> getAllMovieStreams(Pageable pageable) {
        return movieStreamService.getAllMovieStreams(pageable);
    }

    @GetMapping("/{id}")
    public MovieStreamDTO getMovieStreamById(@PathVariable Long id) {
        return movieStreamService.getMovieStreamById(id);
    }

    @PostMapping
    public MovieStreamDTO createMovieStream(@RequestBody MovieStreamDTO movieStreamDto) {
        return movieStreamService.createMovieStream(movieStreamDto);
    }

    @PutMapping("/{id}")
    public MovieStreamDTO updateMovieStream(@PathVariable Long id, @RequestBody MovieStreamDTO movieStreamDto) {
        return movieStreamService.updateMovieStream(id, movieStreamDto);
    }

    @DeleteMapping("/{id}")
    public void deleteMovieStream(@PathVariable Long id) {
        movieStreamService.deleteMovieStream(id);
    }

}
