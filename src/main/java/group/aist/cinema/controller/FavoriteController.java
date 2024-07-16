package group.aist.cinema.controller;

import group.aist.cinema.dto.request.FavoriteRequestDTO;
import group.aist.cinema.dto.response.FavoriteResponseDTO;
import group.aist.cinema.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping
    public Page<FavoriteResponseDTO> getAllFavorites(Pageable pageable) {
        return favoriteService.getAllFavorites(pageable);
    }

    @GetMapping("/name")
    public FavoriteResponseDTO getFavoriteByName(@RequestParam String name) {
        return favoriteService.getFavoriteByName(name);
    }

    @GetMapping("/{userId}")
    public Page<FavoriteResponseDTO> getFavoriteByUserId(@PathVariable Long userId, Pageable pageable) {
        return favoriteService.getFavoriteByUserId(userId, pageable);
    }

    @PostMapping
    public void addFavorite(@RequestBody FavoriteRequestDTO favoriteRequestDTO) {
        favoriteService.createFavorite(favoriteRequestDTO);
    }

    @PostMapping("/{favoriteId}/movies/{movieId}")
    public void addFavoriteToUser(@PathVariable Long favoriteId,
                                  @PathVariable Long movieId) {
        favoriteService.addMovieToFavorite(favoriteId, movieId);
    }

    @PutMapping("/{id}")
    public void updateFavorite(@PathVariable Long id, @RequestBody FavoriteRequestDTO favoriteRequestDTO) {
        favoriteService.updateFavorite(id, favoriteRequestDTO);
    }

    @DeleteMapping("/{favoriteId}/movies/{movieId}")
    public void deleteFavorite(@PathVariable Long favoriteId,
                               @PathVariable Long movieId) {
        favoriteService.deleteMovieFromFavorite(favoriteId, movieId);
    }

    @DeleteMapping("/{id}")
    public void deleteFavorite(@PathVariable Long id) {
        favoriteService.deleteFavorite(id);
    }


}
