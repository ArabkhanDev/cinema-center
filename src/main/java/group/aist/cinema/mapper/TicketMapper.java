package group.aist.cinema.mapper;

import group.aist.cinema.dto.request.TicketRequestDTO;
import group.aist.cinema.dto.response.TicketResponseDTO;
import group.aist.cinema.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    TicketResponseDTO toDTO(Ticket ticket);

    Ticket toEntity(TicketRequestDTO ticketRequestDTO);

    @Mapping(target = "id", ignore = true)
    void updateTicketFromDTO(TicketRequestDTO userRequestDTO, @MappingTarget Ticket ticket);
}
