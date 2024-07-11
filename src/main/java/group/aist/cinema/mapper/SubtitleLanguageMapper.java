package group.aist.cinema.mapper;

import group.aist.cinema.dto.common.SubtitleLanguageDTO;
import group.aist.cinema.model.SubtitleLanguage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SubtitleLanguageMapper {

    SubtitleLanguage mapToEntity(SubtitleLanguageDTO subtitleLanguageDto);

    SubtitleLanguageDTO mapToDto(SubtitleLanguage subtitleLanguage);

    @Mapping(target = "id", ignore = true)
    void updateSubtitleLanguageFromDTO(SubtitleLanguageDTO subtitleLanguageDto, @MappingTarget SubtitleLanguage subtitleLanguage);
}

