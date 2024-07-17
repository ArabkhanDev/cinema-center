package group.aist.cinema.controller;

import group.aist.cinema.dto.request.MovieSessionRequestDTO;
import group.aist.cinema.dto.response.MovieSessionResponseDTO;
import group.aist.cinema.model.base.BaseResponse;
import group.aist.cinema.service.MovieSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public BaseResponse<EntityModel<MovieSessionResponseDTO>> getMovieSessionById(@PathVariable Long id) {
        MovieSessionResponseDTO movieSessionDto = movieSessionService.getMovieSessionById(id);
        EntityModel<MovieSessionResponseDTO> resource = EntityModel.of(movieSessionDto);
        resource.add(linkTo(methodOn(MovieController.class).getMovieById(movieSessionDto.getMovie().getId())).withRel("Movie"));
        resource.add(linkTo(methodOn(MovieSessionController.class).getMovieSessionById(movieSessionDto.getMovieStream().getId())).withRel("Movie Stream"));
        resource.add(linkTo(methodOn(HallController.class).getHallById(movieSessionDto.getHall().getId())).withRel("Hall"));
        return BaseResponse.success(resource);
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
