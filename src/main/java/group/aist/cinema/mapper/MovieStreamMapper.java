package group.aist.cinema.mapper;

import group.aist.cinema.dto.common.MovieStreamDTO;
import group.aist.cinema.model.MovieStream;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MovieStreamMapper {
    MovieStream mapToEntity(MovieStreamDTO movieStreamDto);
    MovieStreamDTO mapToDto(MovieStream movieStream);
    @Mapping(target = "id",ignore = true)
    void updateMovieStreamFromDTO(MovieStreamDTO movieStreamDTO, @MappingTarget MovieStream movieStream);
}
