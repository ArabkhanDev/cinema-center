package group.aist.cinema.controller;

import group.aist.cinema.dto.common.MovieStreamDto;
import group.aist.cinema.service.MovieStreamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/movieStreams")
@RequiredArgsConstructor
public class MovieStreamController {

    private final MovieStreamService movieStreamService;

    @GetMapping
    public Page<MovieStreamDto> getAllMovieStreams(Pageable pageable) {
        return movieStreamService.getAllMovieStreams(pageable);
    }

    @GetMapping("/{id}")
    public MovieStreamDto getMovieStreamById(@PathVariable Long id) {
        return movieStreamService.getMovieStreamById(id);
    }

    @PostMapping
    public MovieStreamDto createMovieStream(@RequestBody MovieStreamDto movieStreamDto) {
        return movieStreamService.createMovieStream(movieStreamDto);
    }

    @PutMapping("/{id}")
    public MovieStreamDto updateMovieStream(@PathVariable Long id, @RequestBody MovieStreamDto movieStreamDto) {
        return movieStreamService.updateMovieStream(id, movieStreamDto);
    }

    @DeleteMapping("/{id}")
    public void deleteMovieStream(@PathVariable Long id) {
        movieStreamService.deleteMovieStream(id);
    }

}
