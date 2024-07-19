package group.aist.cinema.mapper;

import group.aist.cinema.dto.common.DubbingLanguageDTO;
import group.aist.cinema.model.DubbingLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DubbingLanguageMapperTest {

    private DubbingLanguageMapper dubbingLanguageMapper;

    @BeforeEach
    public void setUp() {
        dubbingLanguageMapper = Mappers.getMapper(DubbingLanguageMapper.class);
    }

    @Test
    public void testToDTO() {
        DubbingLanguage dubbingLanguage = new DubbingLanguage();
        dubbingLanguage.setId(1L);
        dubbingLanguage.setName("English");
        dubbingLanguage.setIsoCode("EN");

        DubbingLanguageDTO dubbingLanguageDTO = dubbingLanguageMapper.toDTO(dubbingLanguage);

        assertEquals(1L, dubbingLanguageDTO.getId());
        assertEquals("English", dubbingLanguageDTO.getName());
        assertEquals("EN", dubbingLanguageDTO.getIsoCode());
    }

    @Test
    public void testToEntity() {
        DubbingLanguageDTO dubbingLanguageDTO = new DubbingLanguageDTO();
        dubbingLanguageDTO.setId(1L);
        dubbingLanguageDTO.setName("English");
        dubbingLanguageDTO.setIsoCode("EN");

        DubbingLanguage dubbingLanguage = dubbingLanguageMapper.toEntity(dubbingLanguageDTO);

        assertEquals(1L, dubbingLanguage.getId());
        assertEquals("English", dubbingLanguage.getName());
        assertEquals("EN", dubbingLanguage.getIsoCode());
    }

    @Test
    public void testUpdateDubbingLanguageFromDTO() {
        DubbingLanguage dubbingLanguage = new DubbingLanguage();
        dubbingLanguage.setId(1L);
        dubbingLanguage.setName("Spanish");
        dubbingLanguage.setIsoCode("ES");

        DubbingLanguageDTO dubbingLanguageDTO = new DubbingLanguageDTO();
        dubbingLanguageDTO.setName("Azerbaijan");
        dubbingLanguageDTO.setIsoCode("AZ");

        dubbingLanguageMapper.updateDubbingLanguageFromDTO(dubbingLanguageDTO, dubbingLanguage);

        assertEquals(1L, dubbingLanguage.getId());
        assertEquals("Azerbaijan", dubbingLanguage.getName());
        assertEquals("AZ", dubbingLanguage.getIsoCode());
    }
}
