package group.aist.cinema.mapper;

import group.aist.cinema.dto.request.MovieRequestDTO;
import group.aist.cinema.dto.request.MovieUpdateRequest;
import group.aist.cinema.dto.response.MovieResponseDTO;
import group.aist.cinema.model.Movie;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static group.aist.cinema.util.ImageUploadUtil.IMAGE_PATH;


@Mapper(componentModel = "spring")
public interface MovieMapper {

    @Mapping(target = "posterImage", ignore = true)
    @Mapping(target = "backgroundImage", ignore = true)
    Movie mapToEntity(MovieRequestDTO movieRequestDTO);

    MovieResponseDTO mapToDto(Movie movie);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "posterImage", ignore = true)
    void updateMovieFromDTO(MovieUpdateRequest movieDto, @MappingTarget Movie movie);

    @AfterMapping
    default void setImagePath(@MappingTarget Movie movie, MovieRequestDTO movieRequestDTO) {
        String posterImageName = movieRequestDTO.getPosterImage().getOriginalFilename();
        String backgroundImageName = movieRequestDTO.getBackgroundImage().getOriginalFilename();
        String posterImagePath = IMAGE_PATH + "/" + posterImageName;
        String backgroundImagePath = IMAGE_PATH + "/" + backgroundImageName;
        movie.setPosterImage(posterImagePath);
        movie.setBackgroundImage(backgroundImagePath);
    }
}
