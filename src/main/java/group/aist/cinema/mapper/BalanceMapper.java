package group.aist.cinema.mapper;

import group.aist.cinema.dto.common.BalanceDTO;
import group.aist.cinema.model.Balance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BalanceMapper {

    BalanceDTO toDTO(Balance balance);

    Balance toEntity(BalanceDTO balanceDTO);

    @Mapping(target = "id", ignore = true)
    void updateBalanceFromDTO(BalanceDTO balanceDTO, @MappingTarget Balance balance);
}
