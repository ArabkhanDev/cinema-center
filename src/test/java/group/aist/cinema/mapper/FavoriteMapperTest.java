package group.aist.cinema.mapper;

import group.aist.cinema.dto.request.FavoriteRequestDTO;
import group.aist.cinema.dto.response.FavoriteResponseDTO;
import group.aist.cinema.dto.response.MovieResponseDTO;
import group.aist.cinema.dto.response.UserResponseDTO;
import group.aist.cinema.model.Favorite;
import group.aist.cinema.model.Movie;
import group.aist.cinema.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FavoriteMapperTest {

    private FavoriteMapper favoriteMapper;

    @BeforeEach
    public void setUp() {
        favoriteMapper = Mappers.getMapper(FavoriteMapper.class);
    }

    @Test
    public void testToEntity() {
        FavoriteRequestDTO favoriteRequestDTO = new FavoriteRequestDTO();
        favoriteRequestDTO.setName("My Favorite");
        favoriteRequestDTO.setUserId(1L);

        Favorite favorite = favoriteMapper.toEntity(favoriteRequestDTO);

        assertEquals("My Favorite", favorite.getName());
        assertEquals(null, favorite.getUser());
        assertEquals(Collections.emptyList(), favorite.getMovies());
    }

    @Test
    public void testToDTO() {
        Favorite favorite = new Favorite();
        favorite.setId(1L);
        favorite.setName("My Favorite");

        User user = new User();
        user.setId("user1");
        user.setUsername("username");
        user.setEmail("user@example.com");
        user.setPhone("123456789");
        favorite.setUser(user);

        Movie movie = new Movie();
        movie.setId(1L);
        movie.setName("Movie Name");
        movie.setDescription("Description");
        movie.setGenre("Genre");
        movie.setReleaseDate(java.time.LocalDate.of(2023, 1, 1));
        movie.setAgeRestriction((short) 18);
        movie.setDuration("120 min");
        movie.setImage("image.jpg");

        favorite.setMovies(Collections.singletonList(movie));

        FavoriteResponseDTO favoriteResponseDTO = favoriteMapper.toDTO(favorite);

        assertEquals(1L, favoriteResponseDTO.getId());
        assertEquals("My Favorite", favoriteResponseDTO.getName());

        UserResponseDTO userResponseDTO = favoriteResponseDTO.getUser();
        assertEquals("user1", userResponseDTO.getId());
        assertEquals("username", userResponseDTO.getUsername());
        assertEquals("user@example.com", userResponseDTO.getEmail());
        assertEquals("123456789", userResponseDTO.getPhone());

        assertEquals(1, favoriteResponseDTO.getMovies().size());
        MovieResponseDTO movieResponseDTO = favoriteResponseDTO.getMovies().iterator().next();
        assertEquals(1L, movieResponseDTO.getId());
        assertEquals("Movie Name", movieResponseDTO.getName());
        assertEquals("Description", movieResponseDTO.getDescription());
        assertEquals("Genre", movieResponseDTO.getGenre());
        assertEquals(java.time.LocalDate.of(2023, 1, 1), movieResponseDTO.getReleaseDate());
        assertEquals((short) 18, movieResponseDTO.getAgeRestriction());
        assertEquals("120 min", movieResponseDTO.getDuration());
        assertEquals("image.jpg", movieResponseDTO.getImage());
    }

    @Test
    public void testUpdateFavoriteFromDTO() {
        Favorite favorite = new Favorite();
        favorite.setId(1L);
        favorite.setName("Old Name");
        favorite.setUser(new User());
        favorite.setMovies(Collections.emptyList());

        FavoriteRequestDTO favoriteRequestDTO = new FavoriteRequestDTO();
        favoriteRequestDTO.setName("Updated Name");
        favoriteRequestDTO.setUserId(2L);

        favoriteMapper.updateFavoriteFromDTO(favoriteRequestDTO, favorite);

        assertEquals(1L, favorite.getId());
        assertEquals("Updated Name", favorite.getName());
        assertEquals(2L, favorite.getUser().getId());
        assertEquals(Collections.emptyList(), favorite.getMovies());
    }
}
