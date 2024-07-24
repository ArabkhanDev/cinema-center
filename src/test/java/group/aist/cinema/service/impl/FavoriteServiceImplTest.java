package group.aist.cinema.service.impl;

import group.aist.cinema.dto.request.FavoriteRequestDTO;
import group.aist.cinema.dto.response.FavoriteResponseDTO;
import group.aist.cinema.mapper.FavoriteMapper;
import group.aist.cinema.model.Favorite;
import group.aist.cinema.model.Movie;
import group.aist.cinema.model.User;
import group.aist.cinema.repository.FavoriteRepository;
import group.aist.cinema.repository.MovieRepository;
import group.aist.cinema.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class FavoriteServiceImplTest {

    @InjectMocks
    private FavoriteServiceImpl favoriteService;

    @Mock
    private FavoriteRepository favoriteRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FavoriteMapper favoriteMapper;

    private Favorite favorite;
    private FavoriteResponseDTO favoriteResponseDTO;
    private FavoriteRequestDTO favoriteRequestDTO;
    private Movie movie;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        favorite = new Favorite();
        favorite.setId(1L);
        favoriteResponseDTO = new FavoriteResponseDTO();
        favoriteResponseDTO.setId(1L);
        favoriteRequestDTO = new FavoriteRequestDTO();
        favoriteRequestDTO.setUserId(1L);
        movie = new Movie();
        movie.setId(1L);
        user = new User();
        user.setId("1");
    }

    @Test
    void getAllFavorites() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Favorite> favoritePage = new PageImpl<>(List.of(favorite));
        when(favoriteRepository.findAllBy(pageable)).thenReturn(favoritePage);
        when(favoriteMapper.toDTO(favorite)).thenReturn(favoriteResponseDTO);

        Page<FavoriteResponseDTO> result = favoriteService.getAllFavorites(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(favoriteRepository, times(1)).findAllBy(pageable);
    }

    @Test
    void getFavoriteByName() {
        when(favoriteRepository.findByName(anyString())).thenReturn(Optional.of(favorite));
        when(favoriteMapper.toDTO(any(Favorite.class))).thenReturn(favoriteResponseDTO);

        FavoriteResponseDTO result = favoriteService.getFavoriteByName("name");

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(favoriteRepository, times(1)).findByName(anyString());
    }

    @Test
    void getFavoriteByName_NotFound() {
        when(favoriteRepository.findByName(anyString())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            favoriteService.getFavoriteByName("name");
        });

        String expectedMessage = "There is no name with this favorite";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getFavoriteByUserId() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Favorite> favoritePage = new PageImpl<>(List.of(favorite));
        when(favoriteRepository.findAllByUserId(anyLong(), any(Pageable.class))).thenReturn(favoritePage);
        when(favoriteMapper.toDTO(favorite)).thenReturn(favoriteResponseDTO);

        Page<FavoriteResponseDTO> result = favoriteService.getFavoriteByUserId(1L, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(favoriteRepository, times(1)).findAllByUserId(anyLong(), any(Pageable.class));
    }

    @Test
    void createFavorite() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(favoriteMapper.toEntity(any(FavoriteRequestDTO.class))).thenReturn(favorite);
        when(favoriteRepository.save(any(Favorite.class))).thenReturn(favorite);
        when(favoriteMapper.toDTO(any(Favorite.class))).thenReturn(favoriteResponseDTO);

        FavoriteResponseDTO result = favoriteService.createFavorite(favoriteRequestDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(favoriteRepository, times(1)).save(any(Favorite.class));
    }

    @Test
    void createFavorite_UserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            favoriteService.createFavorite(favoriteRequestDTO);
        });

        String expectedMessage = "There is no user with this id1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void addMovieToFavorite() {
        when(favoriteRepository.findById(anyLong())).thenReturn(Optional.of(favorite));
        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(movie));
        when(favoriteMapper.toDTO(any(Favorite.class))).thenReturn(favoriteResponseDTO);

        FavoriteResponseDTO result = favoriteService.addMovieToFavorite(1L, 1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(favoriteRepository, times(1)).findById(anyLong());
        verify(movieRepository, times(1)).findById(anyLong());
    }

    @Test
    void addMovieToFavorite_FavoriteNotFound() {
        when(favoriteRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            favoriteService.addMovieToFavorite(1L, 1L);
        });

        String expectedMessage = "There is no favorite with this id 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void addMovieToFavorite_MovieNotFound() {
        when(favoriteRepository.findById(anyLong())).thenReturn(Optional.of(favorite));
        when(movieRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            favoriteService.addMovieToFavorite(1L, 1L);
        });

        String expectedMessage = "There is no movie with this id 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void deleteMovieFromFavorite() {
        when(favoriteRepository.findById(anyLong())).thenReturn(Optional.of(favorite));
        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(movie));
        when(favoriteMapper.toDTO(any(Favorite.class))).thenReturn(favoriteResponseDTO);

        FavoriteResponseDTO result = favoriteService.deleteMovieFromFavorite(1L, 1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(favoriteRepository, times(1)).findById(anyLong());
        verify(movieRepository, times(1)).findById(anyLong());
    }

    @Test
    void deleteMovieFromFavorite_FavoriteNotFound() {
        when(favoriteRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            favoriteService.deleteMovieFromFavorite(1L, 1L);
        });

        String expectedMessage = "There is no favorite with this id 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void deleteMovieFromFavorite_MovieNotFound() {
        when(favoriteRepository.findById(anyLong())).thenReturn(Optional.of(favorite));
        when(movieRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            favoriteService.deleteMovieFromFavorite(1L, 1L);
        });

        String expectedMessage = "There is no movie with this id 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updateFavorite() {
        when(favoriteRepository.findById(anyLong())).thenReturn(Optional.of(favorite));
        when(favoriteMapper.toDTO(any(Favorite.class))).thenReturn(favoriteResponseDTO);

        FavoriteResponseDTO result = favoriteService.updateFavorite(1L, favoriteRequestDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(favoriteRepository, times(1)).findById(anyLong());
        verify(favoriteRepository, times(1)).save(any(Favorite.class));
    }

    @Test
    void updateFavorite_NotFound() {
        when(favoriteRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            favoriteService.updateFavorite(1L, favoriteRequestDTO);
        });

        String expectedMessage = "There is no favorite with this id 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void deleteFavorite() {
        doNothing().when(favoriteRepository).deleteById(anyLong());

        favoriteService.deleteFavorite(1L);

        verify(favoriteRepository, times(1)).deleteById(anyLong());
    }
}
