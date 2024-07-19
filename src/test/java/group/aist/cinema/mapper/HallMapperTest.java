package group.aist.cinema.mapper;

import group.aist.cinema.dto.common.HallDTO;
import group.aist.cinema.model.Hall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HallMapperTest {

    private HallMapper hallMapper;

    @BeforeEach
    public void setUp() {
        hallMapper = Mappers.getMapper(HallMapper.class);
    }

    @Test
    public void testToEntity() {
        HallDTO hallDTO = new HallDTO();
        hallDTO.setName("Main Hall");
        hallDTO.setSeatCount(200);
        hallDTO.setAvailableSeat(150);

        Hall hall = hallMapper.toEntity(hallDTO);

        assertEquals("Main Hall", hall.getName());
        assertEquals(200, hall.getSeatCount());
        assertEquals(150, hall.getAvailableSeat());
    }

    @Test
    public void testToDTO() {
        Hall hall = new Hall();
        hall.setId(1L);
        hall.setName("Main Hall");
        hall.setSeatCount(200);
        hall.setAvailableSeat(150);

        HallDTO hallDTO = hallMapper.toDTO(hall);

        assertEquals(1L, hallDTO.getId());
        assertEquals("Main Hall", hallDTO.getName());
        assertEquals(200, hallDTO.getSeatCount());
        assertEquals(150, hallDTO.getAvailableSeat());
    }

    @Test
    public void testUpdateHallFromDTO() {
        Hall hall = new Hall();
        hall.setId(1L);
        hall.setName("Old Hall");
        hall.setSeatCount(100);
        hall.setAvailableSeat(50);

        HallDTO hallDTO = new HallDTO();
        hallDTO.setName("Updated Hall");
        hallDTO.setSeatCount(250);
        hallDTO.setAvailableSeat(200);

        hallMapper.updateHallFromDTO(hallDTO, hall);

        assertEquals(1L, hall.getId());
        assertEquals("Updated Hall", hall.getName());
        assertEquals(250, hall.getSeatCount());
        assertEquals(200, hall.getAvailableSeat());
    }
}
