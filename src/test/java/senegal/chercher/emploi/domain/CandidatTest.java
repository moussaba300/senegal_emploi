package senegal.chercher.emploi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static senegal.chercher.emploi.domain.CandidatTestSamples.*;
import static senegal.chercher.emploi.domain.UtilisateurTestSamples.*;

import org.junit.jupiter.api.Test;
import senegal.chercher.emploi.web.rest.TestUtil;

class CandidatTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Candidat.class);
        Candidat candidat1 = getCandidatSample1();
        Candidat candidat2 = new Candidat();
        assertThat(candidat1).isNotEqualTo(candidat2);

        candidat2.setId(candidat1.getId());
        assertThat(candidat1).isEqualTo(candidat2);

        candidat2 = getCandidatSample2();
        assertThat(candidat1).isNotEqualTo(candidat2);
    }

    @Test
    void utilisateurTest() {
        Candidat candidat = getCandidatRandomSampleGenerator();
        Utilisateur utilisateurBack = getUtilisateurRandomSampleGenerator();

        candidat.setUtilisateur(utilisateurBack);
        assertThat(candidat.getUtilisateur()).isEqualTo(utilisateurBack);

        candidat.utilisateur(null);
        assertThat(candidat.getUtilisateur()).isNull();
    }
}
