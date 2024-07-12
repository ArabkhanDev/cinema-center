package group.aist.cinema.mapper;

import group.aist.cinema.dto.request.MovieSessionRequestDTO;
import group.aist.cinema.dto.request.MovieStreamRequestDTO;
import group.aist.cinema.dto.response.MovieSessionResponseDTO;
import group.aist.cinema.model.MovieSession;
import group.aist.cinema.model.MovieStream;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MovieSessionMapper {

    MovieSessionResponseDTO mapToResponseDTO(MovieSession movieSession);

    MovieSession mapToEntity(MovieSessionRequestDTO movieSessionRequestDTO);

    @Mapping(target = "id",ignore = true)
    void updateMovieSessionFromDTO(MovieSessionRequestDTO movieSessionRequestDTO, @MappingTarget MovieSession movieSession);

}
