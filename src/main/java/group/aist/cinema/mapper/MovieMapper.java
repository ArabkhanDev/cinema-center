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

    @Mapping(target = "image", ignore = true)
    Movie mapToEntity(MovieRequestDTO movieRequestDTO);

    MovieResponseDTO mapToDto(Movie movie);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    void updateMovieFromDTO(MovieUpdateRequest movieDto, @MappingTarget Movie movie);

    @AfterMapping
    default void setImagePath(@MappingTarget Movie movie, MovieRequestDTO movieRequestDTO) {
        String imageName = movieRequestDTO.getImage().getOriginalFilename();
        String imagePath = IMAGE_PATH + "/" + imageName;
        movie.setImage(imagePath);
    }

}
