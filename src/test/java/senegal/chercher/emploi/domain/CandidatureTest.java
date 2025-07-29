package senegal.chercher.emploi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static senegal.chercher.emploi.domain.CandidatTestSamples.*;
import static senegal.chercher.emploi.domain.CandidatureTestSamples.*;
import static senegal.chercher.emploi.domain.OffreEmploiTestSamples.*;

import org.junit.jupiter.api.Test;
import senegal.chercher.emploi.web.rest.TestUtil;

class CandidatureTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Candidature.class);
        Candidature candidature1 = getCandidatureSample1();
        Candidature candidature2 = new Candidature();
        assertThat(candidature1).isNotEqualTo(candidature2);

        candidature2.setId(candidature1.getId());
        assertThat(candidature1).isEqualTo(candidature2);

        candidature2 = getCandidatureSample2();
        assertThat(candidature1).isNotEqualTo(candidature2);
    }

    @Test
    void offreTest() {
        Candidature candidature = getCandidatureRandomSampleGenerator();
        OffreEmploi offreEmploiBack = getOffreEmploiRandomSampleGenerator();

        candidature.setOffre(offreEmploiBack);
        assertThat(candidature.getOffre()).isEqualTo(offreEmploiBack);

        candidature.offre(null);
        assertThat(candidature.getOffre()).isNull();
    }

    @Test
    void candidatTest() {
        Candidature candidature = getCandidatureRandomSampleGenerator();
        Candidat candidatBack = getCandidatRandomSampleGenerator();

        candidature.setCandidat(candidatBack);
        assertThat(candidature.getCandidat()).isEqualTo(candidatBack);

        candidature.candidat(null);
        assertThat(candidature.getCandidat()).isNull();
    }
}
