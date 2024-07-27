package group.aist.cinema.mapper;

import group.aist.cinema.dto.request.SeatRequestDTO;
import group.aist.cinema.dto.response.SeatResponseDTO;
import group.aist.cinema.model.Seat;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SeatMapper {

    SeatResponseDTO toDTO(Seat seat);

    Seat toEntity(SeatRequestDTO seatRequestDTO);

    @Mapping(target = "id", ignore = true)
    void updateSeatFromDTO(SeatRequestDTO seatRequestDTO, @MappingTarget Seat seat);

}
