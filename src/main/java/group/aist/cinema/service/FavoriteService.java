package group.aist.cinema.service;

import group.aist.cinema.dto.request.FavoriteRequestDTO;
import group.aist.cinema.dto.response.FavoriteResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FavoriteService {

    Page<FavoriteResponseDTO> getAllFavorites(Pageable pageable);

    FavoriteResponseDTO getFavoriteByName(String name);

    Page<FavoriteResponseDTO> getFavoriteByUserId(Long userId, Pageable pageable);

    FavoriteResponseDTO createFavorite(FavoriteRequestDTO favoriteDTO);

    FavoriteResponseDTO addMovieToFavorite(Long id, Long movieId);

    FavoriteResponseDTO deleteMovieFromFavorite(Long id, Long movieId);

    FavoriteResponseDTO updateFavorite(Long id, FavoriteRequestDTO favoriteDTO);

    void deleteFavorite(Long id);

}
