package group.aist.cinema.controller;

import group.aist.cinema.dto.request.MovieRequestDTO;
import group.aist.cinema.dto.request.MovieUpdateRequest;
import group.aist.cinema.dto.response.MovieResponseDTO;
import group.aist.cinema.model.base.BaseResponse;
import group.aist.cinema.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/v1/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public BaseResponse<Page<MovieResponseDTO>> getAllMovieStreams(Pageable pageable) {
        return BaseResponse.success(movieService.getAllMovies(pageable));
    }

    @GetMapping("/{id}")
    public BaseResponse<MovieResponseDTO> getMovieById(@PathVariable Long id) {
        return BaseResponse.success(movieService.getMovieById(id));
    }

    @PostMapping(consumes = { "multipart/form-data" })
    public MovieResponseDTO createMovie(@ModelAttribute MovieRequestDTO movieRequestDTO) throws IOException {
        return movieService.createMovie(movieRequestDTO);
    }

    @PutMapping("/{id}")
    public BaseResponse<MovieResponseDTO> updateMovie(@PathVariable Long id, @RequestBody MovieUpdateRequest movieDTO) {
        return BaseResponse.success(movieService.updateMovie(id, movieDTO));
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return BaseResponse.noContent();
    }
}
