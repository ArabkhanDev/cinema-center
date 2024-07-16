package group.aist.cinema.controller;

import group.aist.cinema.dto.request.FavoriteRequestDTO;
import group.aist.cinema.dto.response.FavoriteResponseDTO;
import group.aist.cinema.model.base.BaseResponse;
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
    public BaseResponse<Page<FavoriteResponseDTO>> getAllFavorites(Pageable pageable) {
        return BaseResponse.success(favoriteService.getAllFavorites(pageable));
    }

    @GetMapping("/name")
    public BaseResponse<FavoriteResponseDTO> getFavoriteByName(@RequestParam String name) {
        return BaseResponse.success(favoriteService.getFavoriteByName(name));
    }

    @GetMapping("/{userId}")
    public BaseResponse<Page<FavoriteResponseDTO>> getFavoriteByUserId(@PathVariable Long userId, Pageable pageable) {
        return BaseResponse.success(favoriteService.getFavoriteByUserId(userId, pageable));
    }

    @PostMapping
    public BaseResponse<Void> addFavorite(@RequestBody FavoriteRequestDTO favoriteRequestDTO) {
        favoriteService.createFavorite(favoriteRequestDTO);
        return BaseResponse.noContent();
    }

    @PostMapping("/{favoriteId}/movies/{movieId}")
    public BaseResponse<Void> addFavoriteToUser(@PathVariable Long favoriteId,
                                  @PathVariable Long movieId) {
        favoriteService.addMovieToFavorite(favoriteId, movieId);
        return BaseResponse.noContent();
    }

    @PutMapping("/{id}")
    public BaseResponse<Void> updateFavorite(@PathVariable Long id, @RequestBody FavoriteRequestDTO favoriteRequestDTO) {
        favoriteService.updateFavorite(id, favoriteRequestDTO);
        return BaseResponse.noContent();
    }

    @DeleteMapping("/{favoriteId}/movies/{movieId}")
    public BaseResponse<Void> deleteFavorite(@PathVariable Long favoriteId,
                               @PathVariable Long movieId) {
        favoriteService.deleteMovieFromFavorite(favoriteId, movieId);
        return BaseResponse.noContent();
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteFavorite(@PathVariable Long id) {
        favoriteService.deleteFavorite(id);
        return BaseResponse.noContent();
    }


}
