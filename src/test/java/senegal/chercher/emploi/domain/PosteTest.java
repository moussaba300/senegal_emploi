package senegal.chercher.emploi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static senegal.chercher.emploi.domain.PosteTestSamples.*;

import org.junit.jupiter.api.Test;
import senegal.chercher.emploi.web.rest.TestUtil;

class PosteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Poste.class);
        Poste poste1 = getPosteSample1();
        Poste poste2 = new Poste();
        assertThat(poste1).isNotEqualTo(poste2);

        poste2.setId(poste1.getId());
        assertThat(poste1).isEqualTo(poste2);

        poste2 = getPosteSample2();
        assertThat(poste1).isNotEqualTo(poste2);
    }
}
