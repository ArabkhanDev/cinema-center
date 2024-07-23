package group.aist.cinema.service.impl;

import group.aist.cinema.dto.request.MovieRequestDTO;
import group.aist.cinema.dto.request.MovieUpdateRequest;
import group.aist.cinema.dto.response.MovieResponseDTO;
import group.aist.cinema.mapper.MovieMapper;
import group.aist.cinema.model.Movie;
import group.aist.cinema.repository.MovieRepository;
import group.aist.cinema.service.MovieService;
import group.aist.cinema.util.ImageUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import static group.aist.cinema.util.ExceptionMessages.MOVIE_NOT_FOUND;
import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Override
    public Page<MovieResponseDTO> getAllMovies(Pageable pageable) {
        Page<Movie> movies = movieRepository.findAll(pageable);
        return movies.map(movieMapper::mapToDto);
    }

    @Override
    public MovieResponseDTO getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, MOVIE_NOT_FOUND + id));
        return movieMapper.mapToDto(movie);
    }

    @Override
    public List<MovieResponseDTO> getMovieByName(String name) {
        List<Movie> movies = movieRepository.findByName(name);
        return movies.stream().map(movieMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public MovieResponseDTO createMovie(MovieRequestDTO movieRequestDTO) throws IOException {
        Movie movie = movieMapper.mapToEntity(movieRequestDTO);

        ImageUploadUtil.saveFile(movieRequestDTO.getName(), movieRequestDTO.getPosterImage());
        MovieResponseDTO movieResponseDTO = movieMapper.mapToDto(movieRepository.save(movie));

        returnBase64Image(movieRequestDTO, movieResponseDTO);
        return movieResponseDTO;
    }

    @Override
    public MovieResponseDTO updateMovie(Long id, MovieUpdateRequest movieDto) {
        Movie existingMovie = movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, MOVIE_NOT_FOUND + id));
        movieMapper.updateMovieFromDTO(movieDto, existingMovie);
        return movieMapper.mapToDto(movieRepository.save(existingMovie));
    }

    @Override
    public void deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new ResponseStatusException(BAD_REQUEST, MOVIE_NOT_FOUND + id);
        }
        movieRepository.deleteById(id);
    }


    private static void returnBase64Image(MovieRequestDTO movieRequestDTO, MovieResponseDTO movieResponseDTO) throws IOException {
        byte[] posterImageBytes = movieRequestDTO.getPosterImage().getBytes();
        byte[] backgroundImageBytes = movieRequestDTO.getBackgroundImage().getBytes();
        String posterImageBase64 = Base64.getEncoder().encodeToString(posterImageBytes);
        String backgroundImageBase64 = Base64.getEncoder().encodeToString(backgroundImageBytes);
        movieResponseDTO.setPosterImage(posterImageBase64);
        movieResponseDTO.setBackgroundImage(backgroundImageBase64);
    }
}
