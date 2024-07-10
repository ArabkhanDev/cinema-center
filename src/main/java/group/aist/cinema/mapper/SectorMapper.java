package group.aist.cinema.mapper;

import group.aist.cinema.dto.common.SectorDTO;
import group.aist.cinema.model.Sector;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SectorMapper {

    @Mapping(target = "hallId",source = "hall.id")
    SectorDTO toDTO(Sector author);

    Sector toEntity(SectorDTO authorDTO);

    @Mapping(target = "id", ignore = true)
    void updateSectorFromDTO(SectorDTO sectorDTO, @MappingTarget Sector sector);

}
