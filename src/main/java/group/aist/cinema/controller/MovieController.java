package group.aist.cinema.controller;

import group.aist.cinema.dto.request.MovieRequestDTO;
import group.aist.cinema.dto.request.MovieUpdateRequest;
import group.aist.cinema.dto.response.MovieResponseDTO;
import group.aist.cinema.model.base.BaseResponse;
import group.aist.cinema.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

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

    @GetMapping("/date")
    public BaseResponse<List<MovieResponseDTO>> getMovieByDate(@RequestParam LocalDate date) {
        return BaseResponse.success(movieService.getMovieByDate(date));
    }

    @PostMapping(consumes = { "multipart/form-data" })
    @PreAuthorize("hasAnyRole('ADMIN')")
    public MovieResponseDTO createMovie(@ModelAttribute MovieRequestDTO movieRequestDTO) throws IOException {
        return movieService.createMovie(movieRequestDTO);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public BaseResponse<MovieResponseDTO> updateMovie(@PathVariable Long id,@Valid @RequestBody MovieUpdateRequest movieDTO) {
        return BaseResponse.success(movieService.updateMovie(id, movieDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public BaseResponse<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return BaseResponse.noContent();
    }
}
