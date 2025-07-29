package senegal.chercher.emploi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static senegal.chercher.emploi.domain.RecruteurTestSamples.*;
import static senegal.chercher.emploi.domain.UtilisateurTestSamples.*;

import org.junit.jupiter.api.Test;
import senegal.chercher.emploi.web.rest.TestUtil;

class RecruteurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recruteur.class);
        Recruteur recruteur1 = getRecruteurSample1();
        Recruteur recruteur2 = new Recruteur();
        assertThat(recruteur1).isNotEqualTo(recruteur2);

        recruteur2.setId(recruteur1.getId());
        assertThat(recruteur1).isEqualTo(recruteur2);

        recruteur2 = getRecruteurSample2();
        assertThat(recruteur1).isNotEqualTo(recruteur2);
    }

    @Test
    void utilisateurTest() {
        Recruteur recruteur = getRecruteurRandomSampleGenerator();
        Utilisateur utilisateurBack = getUtilisateurRandomSampleGenerator();

        recruteur.setUtilisateur(utilisateurBack);
        assertThat(recruteur.getUtilisateur()).isEqualTo(utilisateurBack);

        recruteur.utilisateur(null);
        assertThat(recruteur.getUtilisateur()).isNull();
    }
}
