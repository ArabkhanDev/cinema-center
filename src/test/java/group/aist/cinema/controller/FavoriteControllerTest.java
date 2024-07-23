package group.aist.cinema.controller;

import group.aist.cinema.dto.request.FavoriteRequestDTO;
import group.aist.cinema.dto.response.FavoriteResponseDTO;
import group.aist.cinema.model.base.BaseResponse;
import group.aist.cinema.service.FavoriteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FavoriteControllerTest {

    @Mock
    private FavoriteService favoriteService;

    @InjectMocks
    private FavoriteController favoriteController;

    private FavoriteRequestDTO favoriteRequestDTO;
    private FavoriteResponseDTO favoriteResponseDTO;
    private Page<FavoriteResponseDTO> favoriteResponseDTOPage;

    @BeforeEach
    void setUp() {
        favoriteRequestDTO = new FavoriteRequestDTO();
        favoriteRequestDTO.setUserId(1L);

        favoriteResponseDTO = new FavoriteResponseDTO();
        favoriteResponseDTO.setId(1L);

        favoriteResponseDTOPage = new PageImpl<>(Collections.singletonList(favoriteResponseDTO));
    }

    @Test
    void getAllFavorites() {
        when(favoriteService.getAllFavorites(any(Pageable.class))).thenReturn(favoriteResponseDTOPage);

        BaseResponse<Page<FavoriteResponseDTO>> response = favoriteController.getAllFavorites(null);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(1, response.getData().getContent().size());
        assertEquals(favoriteResponseDTO.getId(), response.getData().getContent().get(0).getId());
    }

    @Test
    void getFavoriteByName() {
        when(favoriteService.getFavoriteByName(anyString())).thenReturn(favoriteResponseDTO);

        BaseResponse<FavoriteResponseDTO> response = favoriteController.getFavoriteByName("FavoriteName");

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(favoriteResponseDTO.getId(), response.getData().getId());
    }

    @Test
    void getFavoriteByUserId() {
        when(favoriteService.getFavoriteByUserId(anyLong(), any(Pageable.class))).thenReturn(favoriteResponseDTOPage);

        BaseResponse<Page<FavoriteResponseDTO>> response = favoriteController.getFavoriteByUserId(1L, null);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(1, response.getData().getContent().size());
        assertEquals(favoriteResponseDTO.getId(), response.getData().getContent().get(0).getId());
    }

    @Test
    void addFavorite() {
        BaseResponse<Void> response = favoriteController.addFavorite(favoriteRequestDTO);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatus());
    }

    @Test
    void addFavoriteToUser() {
        BaseResponse<Void> response = favoriteController.addFavoriteToUser(1L, 2L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatus());
    }

    @Test
    void updateFavorite() {
        BaseResponse<Void> response = favoriteController.updateFavorite(1L, favoriteRequestDTO);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatus());
    }

    @Test
    void deleteMovieFromFavorite() {
        BaseResponse<Void> response = favoriteController.deleteFavorite(1L, 2L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatus());
    }

    @Test
    void deleteFavorite() {
        BaseResponse<Void> response = favoriteController.deleteFavorite(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatus());
    }
}
