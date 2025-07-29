package senegal.chercher.emploi.service.mapper;

import static senegal.chercher.emploi.domain.OffreEmploiAsserts.*;
import static senegal.chercher.emploi.domain.OffreEmploiTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OffreEmploiMapperTest {

    private OffreEmploiMapper offreEmploiMapper;

    @BeforeEach
    void setUp() {
        offreEmploiMapper = new OffreEmploiMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getOffreEmploiSample1();
        var actual = offreEmploiMapper.toEntity(offreEmploiMapper.toDto(expected));
        assertOffreEmploiAllPropertiesEquals(expected, actual);
    }
}
