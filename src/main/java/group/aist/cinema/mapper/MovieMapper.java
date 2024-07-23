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

    @Mapping(target = "backgroundImage", ignore = true)
    @Mapping(target = "posterImage", ignore = true)
    Movie mapToEntity(MovieRequestDTO movieRequestDTO);

    MovieResponseDTO mapToDto(Movie movie);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "backgroundImage", ignore = true)
    @Mapping(target = "posterImage", ignore = true)
    void updateMovieFromDTO(MovieUpdateRequest movieDto, @MappingTarget Movie movie);

    @AfterMapping
    default void setImagePath(@MappingTarget Movie movie, MovieRequestDTO movieRequestDTO) {
        String backgroundImage = movieRequestDTO.getBackgroundImage().getOriginalFilename();
        String posterImage = movieRequestDTO.getPosterImage().getOriginalFilename();
        String backImagePath = IMAGE_PATH + "/" + backgroundImage;
        String poserImagePath = IMAGE_PATH + "/" + posterImage;
        movie.setBackgroundImage(backImagePath);
        movie.setPosterImage(poserImagePath);
    }

}
