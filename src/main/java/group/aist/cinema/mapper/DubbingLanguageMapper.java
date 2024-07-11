package group.aist.cinema.mapper;

import group.aist.cinema.dto.common.DubbingLanguageDTO;
import group.aist.cinema.model.DubbingLanguage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DubbingLanguageMapper {

    DubbingLanguageDTO toDTO(DubbingLanguage dubbingLanguage);

    DubbingLanguage toEntity(DubbingLanguageDTO dubbingLanguageDTO);

    @Mapping(target = "id",ignore = true)
    void updateDubbingLanguageFromDTO(DubbingLanguageDTO dubbingLanguageDTO, @MappingTarget DubbingLanguage dubbingLanguage);

}
