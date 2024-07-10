package group.aist.cinema.service.impl;

import group.aist.cinema.dto.common.MovieStreamDTO;
import group.aist.cinema.mapper.MovieStreamMapper;
import group.aist.cinema.model.MovieStream;
import group.aist.cinema.repository.MovieStreamRepository;
import group.aist.cinema.service.MovieStreamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieStreamServiceImpl implements MovieStreamService {

    private final MovieStreamRepository movieStreamRepository;

    private final MovieStreamMapper movieStreamMapper;

    @Override
    public Page<MovieStreamDTO> getAllMovieStreams(Pageable pageable) {
        Page<MovieStream> movieStreams = movieStreamRepository.findAll(pageable);
        return movieStreams.map(movieStreamMapper::mapToDto);
    }

    @Override
    public MovieStreamDTO getMovieStreamById(Long id) {
        MovieStream movieStream = movieStreamRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie Stream not found with id: " + id ));
        return movieStreamMapper.mapToDto(movieStream);
    }

    @Override
    public MovieStreamDTO createMovieStream(MovieStreamDTO movieStreamDto) {
        MovieStream movieStream = movieStreamMapper.mapToEntity(movieStreamDto);
        return movieStreamMapper.mapToDto(movieStreamRepository.save(movieStream));
    }

    @Override
    public MovieStreamDTO updateMovieStream(Long id, MovieStreamDTO movieStreamDTO) {
        MovieStream movieStream = movieStreamMapper.mapToEntity(getMovieStreamById(id));
        movieStreamMapper.updateMovieStreamFromDTO(movieStreamDTO,movieStream);
        return movieStreamMapper.mapToDto(movieStreamRepository.save(movieStream));
    }

    @Override
    public void deleteMovieStream(Long id) {
        movieStreamRepository.deleteById(id);
    }
}
