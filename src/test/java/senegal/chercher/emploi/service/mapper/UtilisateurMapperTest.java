package senegal.chercher.emploi.service.mapper;

import static senegal.chercher.emploi.domain.UtilisateurAsserts.*;
import static senegal.chercher.emploi.domain.UtilisateurTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UtilisateurMapperTest {

    private UtilisateurMapper utilisateurMapper;

    @BeforeEach
    void setUp() {
        utilisateurMapper = new UtilisateurMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getUtilisateurSample1();
        var actual = utilisateurMapper.toEntity(utilisateurMapper.toDto(expected));
        assertUtilisateurAllPropertiesEquals(expected, actual);
    }
}
