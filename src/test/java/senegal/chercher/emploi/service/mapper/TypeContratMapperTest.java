package senegal.chercher.emploi.service.mapper;

import static senegal.chercher.emploi.domain.TypeContratAsserts.*;
import static senegal.chercher.emploi.domain.TypeContratTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TypeContratMapperTest {

    private TypeContratMapper typeContratMapper;

    @BeforeEach
    void setUp() {
        typeContratMapper = new TypeContratMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTypeContratSample1();
        var actual = typeContratMapper.toEntity(typeContratMapper.toDto(expected));
        assertTypeContratAllPropertiesEquals(expected, actual);
    }
}
