package group.aist.cinema.mapper;

import group.aist.cinema.dto.request.MovieStreamRequestDTO;
import group.aist.cinema.dto.response.MovieStreamResponseDTO;
import group.aist.cinema.model.MovieStream;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MovieStreamMapper {

    MovieStream mapToEntity(MovieStreamRequestDTO movieStreamRequestDto);

    MovieStreamRequestDTO mapToDto(MovieStream movieStream);

    @Mapping(target = "id",ignore = true)
    void updateMovieStreamFromDTO(MovieStreamRequestDTO movieStreamRequestDTO, @MappingTarget MovieStream movieStream);

    MovieStreamResponseDTO mapToResponseDTO(MovieStream movieStream);
}
