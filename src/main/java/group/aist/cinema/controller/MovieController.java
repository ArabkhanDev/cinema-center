package group.aist.cinema.controller;

import group.aist.cinema.dto.common.MovieDTO;
import group.aist.cinema.model.base.BaseResponse;
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
    public BaseResponse<Page<MovieDTO>> getAllMovieStreams(Pageable pageable) {
        return BaseResponse.success(movieService.getAllMovies(pageable));
    }

    @GetMapping("/{id}")
    public BaseResponse<MovieDTO> getMovieById(@PathVariable Long id) {
        return BaseResponse.success(movieService.getMovieById(id));
    }

    @PostMapping
    public BaseResponse<MovieDTO> createMovie(@RequestBody MovieDTO movieDTO) {
        return BaseResponse.created(movieService.createMovie(movieDTO));
    }

    @PutMapping("/{id}")
    public BaseResponse<MovieDTO> updateMovie(@PathVariable Long id, @RequestBody MovieDTO movieDTO) {
        return BaseResponse.success(movieService.updateMovie(id, movieDTO));
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return BaseResponse.noContent();
    }
}
