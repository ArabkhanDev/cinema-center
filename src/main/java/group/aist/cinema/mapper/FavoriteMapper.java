package group.aist.cinema.mapper;

import group.aist.cinema.dto.request.FavoriteRequestDTO;
import group.aist.cinema.dto.response.FavoriteResponseDTO;
import group.aist.cinema.model.Favorite;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FavoriteMapper {

    Favorite toEntity(FavoriteRequestDTO favoriteRequestDTO);

    FavoriteResponseDTO toDTO(Favorite favorite);

    @Mapping(target = "id", ignore = true)
    void updateFavoriteFromDTO(FavoriteRequestDTO favoriteRequestDTO, @MappingTarget Favorite favorite);


}
