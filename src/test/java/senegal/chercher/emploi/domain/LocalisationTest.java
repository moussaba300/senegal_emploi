package senegal.chercher.emploi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static senegal.chercher.emploi.domain.LocalisationTestSamples.*;

import org.junit.jupiter.api.Test;
import senegal.chercher.emploi.web.rest.TestUtil;

class LocalisationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Localisation.class);
        Localisation localisation1 = getLocalisationSample1();
        Localisation localisation2 = new Localisation();
        assertThat(localisation1).isNotEqualTo(localisation2);

        localisation2.setId(localisation1.getId());
        assertThat(localisation1).isEqualTo(localisation2);

        localisation2 = getLocalisationSample2();
        assertThat(localisation1).isNotEqualTo(localisation2);
    }
}
