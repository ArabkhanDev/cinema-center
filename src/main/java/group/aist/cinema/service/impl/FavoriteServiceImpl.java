package group.aist.cinema.service.impl;

import group.aist.cinema.dto.request.FavoriteRequestDTO;
import group.aist.cinema.dto.response.FavoriteResponseDTO;
import group.aist.cinema.mapper.FavoriteMapper;
import group.aist.cinema.model.Favorite;
import group.aist.cinema.repository.FavoriteRepository;
import group.aist.cinema.repository.MovieRepository;
import group.aist.cinema.repository.UserRepository;
import group.aist.cinema.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final FavoriteMapper favoriteMapper;

    @Override
    public Page<FavoriteResponseDTO> getAllFavorites(Pageable pageable) {
        return favoriteRepository.findAllBy(pageable).map(favoriteMapper::toDTO);
    }

    @Override
    public FavoriteResponseDTO getFavoriteByName(String name) {
        return favoriteMapper.toDTO(favoriteRepository.findByName(name).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"There is no name with this favorite")));
    }

    @Override
    public Page<FavoriteResponseDTO> getFavoriteByUserId(Long userId, Pageable pageable) {
        return favoriteRepository.findAllByUserId(userId, pageable).map(favoriteMapper::toDTO);
    }

    @Override
    public FavoriteResponseDTO createFavorite(FavoriteRequestDTO favoriteDTO) {
        Favorite favorite = favoriteMapper.toEntity(favoriteDTO);
        favorite.setUser(userRepository.findById(favoriteDTO.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"There is no user with this id" + favoriteDTO.getUserId())));
        return favoriteMapper.toDTO(favoriteRepository.save(favorite));
    }

    @Transactional
    @Override
    public FavoriteResponseDTO addMovieToFavorite(Long id, Long movieId) {
        Favorite favorite = getFavorite(id);
        favorite.getMovies().add(movieRepository.findById(movieId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"There is no movie with this id " + movieId)));
        return favoriteMapper.toDTO(favoriteRepository.save(favorite));
    }

    @Transactional
    @Override
    public FavoriteResponseDTO deleteMovieFromFavorite(Long id, Long movieId) {
        Favorite favorite = getFavorite(id);
        favorite.getMovies().remove(movieRepository.findById(movieId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"There is no movie with this id " + movieId)));
        return favoriteMapper.toDTO(favoriteRepository.save(favorite));
    }

    @Transactional
    @Override
    public FavoriteResponseDTO updateFavorite(Long id, FavoriteRequestDTO favoriteDTO) {
        Favorite favorite = getFavorite(id);
        favoriteMapper.updateFavoriteFromDTO(favoriteDTO, favorite);
        return favoriteMapper.toDTO(favoriteRepository.save(favorite));
    }

    @Override
    public void deleteFavorite(Long id) {
        favoriteRepository.deleteById(id);
    }



    private Favorite getFavorite(Long id) {
        return favoriteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"There is no favorite with this id " + id));
    }
}
