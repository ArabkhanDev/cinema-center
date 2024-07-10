package group.aist.cinema.mapper;

import group.aist.cinema.dto.common.MovieStreamDto;
import group.aist.cinema.model.MovieStream;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieStreamMapper {
    MovieStream mapToEntity(MovieStreamDto movieStreamDto);
    MovieStreamDto mapToDto(MovieStream movieStream);
}
