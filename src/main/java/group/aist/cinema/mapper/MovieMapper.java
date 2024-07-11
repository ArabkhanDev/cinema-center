package group.aist.cinema.mapper;

import group.aist.cinema.dto.common.MovieDTO;
import group.aist.cinema.model.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    Movie mapToEntity(MovieDTO movieDto);

    MovieDTO mapToDto(Movie movie);

    @Mapping(target = "id", ignore = true)
    void updateMovieFromDTO(MovieDTO movieDto, @MappingTarget Movie movie);

}
