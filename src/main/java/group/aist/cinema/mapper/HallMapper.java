package group.aist.cinema.mapper;

import group.aist.cinema.dto.common.HallDTO;
import group.aist.cinema.model.Hall;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface HallMapper {

    Hall toEntity(HallDTO hallDTO);

    HallDTO toDTO(Hall hall);

    @Mapping(target = "id",ignore = true)
    void updateHallFromDTO(HallDTO hallDTO, @MappingTarget Hall hall);

}
