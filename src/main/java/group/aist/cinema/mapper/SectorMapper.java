package group.aist.cinema.mapper;

import group.aist.cinema.dto.common.SectorDTO;
import group.aist.cinema.model.Sector;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SectorMapper {

    SectorDTO toDTO(Sector author);

    Sector toEntity(SectorDTO authorDTO);

}
