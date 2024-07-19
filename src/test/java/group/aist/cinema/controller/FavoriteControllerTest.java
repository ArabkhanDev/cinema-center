package group.aist.cinema.controller;

import group.aist.cinema.dto.request.FavoriteRequestDTO;
import group.aist.cinema.dto.response.FavoriteResponseDTO;
import group.aist.cinema.model.base.BaseResponse;
import group.aist.cinema.service.FavoriteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FavoriteController.class)
class FavoriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FavoriteService favoriteService;

    private FavoriteRequestDTO favoriteRequestDTO;
    private FavoriteResponseDTO favoriteResponseDTO;
    private Page<FavoriteResponseDTO> favoriteResponseDTOPage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        favoriteRequestDTO = new FavoriteRequestDTO();
        favoriteRequestDTO.setUserId(1L);

        favoriteResponseDTO = new FavoriteResponseDTO();
        favoriteResponseDTO.setId(1L);

        favoriteResponseDTOPage = new PageImpl<>(Collections.singletonList(favoriteResponseDTO));
    }

    @Test
    void getAllFavorites() throws Exception {
        when(favoriteService.getAllFavorites(any(Pageable.class))).thenReturn(favoriteResponseDTOPage);

        mockMvc.perform(get("/v1/api/favorites")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].id").value(favoriteResponseDTO.getId()));
    }

    @Test
    void getFavoriteByName() throws Exception {
        when(favoriteService.getFavoriteByName(anyString())).thenReturn(favoriteResponseDTO);

        mockMvc.perform(get("/v1/api/favorites/name")
                        .param("name", "FavoriteName")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(favoriteResponseDTO.getId()));
    }

    @Test
    void getFavoriteByUserId() throws Exception {
        when(favoriteService.getFavoriteByUserId(anyLong(), any(Pageable.class))).thenReturn(favoriteResponseDTOPage);

        mockMvc.perform(get("/v1/api/favorites/{userId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].id").value(favoriteResponseDTO.getId()));
    }

    @Test
    void addFavorite() throws Exception {
        mockMvc.perform(post("/v1/api/favorites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1}"))
                .andExpect(status().isNoContent());
    }

    @Test
    void addFavoriteToUser() throws Exception {
        mockMvc.perform(post("/v1/api/favorites/{favoriteId}/movies/{movieId}", 1L, 2L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateFavorite() throws Exception {
        mockMvc.perform(put("/v1/api/favorites/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1}"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteMovieFromFavorite() throws Exception {
        mockMvc.perform(delete("/v1/api/favorites/{favoriteId}/movies/{movieId}", 1L, 2L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteFavorite() throws Exception {
        mockMvc.perform(delete("/v1/api/favorites/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
