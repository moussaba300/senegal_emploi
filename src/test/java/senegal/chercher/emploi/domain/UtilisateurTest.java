package senegal.chercher.emploi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static senegal.chercher.emploi.domain.CandidatTestSamples.*;
import static senegal.chercher.emploi.domain.RecruteurTestSamples.*;
import static senegal.chercher.emploi.domain.UtilisateurTestSamples.*;

import org.junit.jupiter.api.Test;
import senegal.chercher.emploi.web.rest.TestUtil;

class UtilisateurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Utilisateur.class);
        Utilisateur utilisateur1 = getUtilisateurSample1();
        Utilisateur utilisateur2 = new Utilisateur();
        assertThat(utilisateur1).isNotEqualTo(utilisateur2);

        utilisateur2.setId(utilisateur1.getId());
        assertThat(utilisateur1).isEqualTo(utilisateur2);

        utilisateur2 = getUtilisateurSample2();
        assertThat(utilisateur1).isNotEqualTo(utilisateur2);
    }

    @Test
    void candidatTest() {
        Utilisateur utilisateur = getUtilisateurRandomSampleGenerator();
        Candidat candidatBack = getCandidatRandomSampleGenerator();

        utilisateur.setCandidat(candidatBack);
        assertThat(utilisateur.getCandidat()).isEqualTo(candidatBack);
        assertThat(candidatBack.getUtilisateur()).isEqualTo(utilisateur);

        utilisateur.candidat(null);
        assertThat(utilisateur.getCandidat()).isNull();
        assertThat(candidatBack.getUtilisateur()).isNull();
    }

    @Test
    void recruteurTest() {
        Utilisateur utilisateur = getUtilisateurRandomSampleGenerator();
        Recruteur recruteurBack = getRecruteurRandomSampleGenerator();

        utilisateur.setRecruteur(recruteurBack);
        assertThat(utilisateur.getRecruteur()).isEqualTo(recruteurBack);
        assertThat(recruteurBack.getUtilisateur()).isEqualTo(utilisateur);

        utilisateur.recruteur(null);
        assertThat(utilisateur.getRecruteur()).isNull();
        assertThat(recruteurBack.getUtilisateur()).isNull();
    }
}
