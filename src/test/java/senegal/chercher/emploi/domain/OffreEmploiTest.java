package senegal.chercher.emploi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static senegal.chercher.emploi.domain.LocalisationTestSamples.*;
import static senegal.chercher.emploi.domain.OffreEmploiTestSamples.*;
import static senegal.chercher.emploi.domain.PosteTestSamples.*;
import static senegal.chercher.emploi.domain.RecruteurTestSamples.*;
import static senegal.chercher.emploi.domain.TypeContratTestSamples.*;

import org.junit.jupiter.api.Test;
import senegal.chercher.emploi.web.rest.TestUtil;

class OffreEmploiTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OffreEmploi.class);
        OffreEmploi offreEmploi1 = getOffreEmploiSample1();
        OffreEmploi offreEmploi2 = new OffreEmploi();
        assertThat(offreEmploi1).isNotEqualTo(offreEmploi2);

        offreEmploi2.setId(offreEmploi1.getId());
        assertThat(offreEmploi1).isEqualTo(offreEmploi2);

        offreEmploi2 = getOffreEmploiSample2();
        assertThat(offreEmploi1).isNotEqualTo(offreEmploi2);
    }

    @Test
    void recruteurTest() {
        OffreEmploi offreEmploi = getOffreEmploiRandomSampleGenerator();
        Recruteur recruteurBack = getRecruteurRandomSampleGenerator();

        offreEmploi.setRecruteur(recruteurBack);
        assertThat(offreEmploi.getRecruteur()).isEqualTo(recruteurBack);

        offreEmploi.recruteur(null);
        assertThat(offreEmploi.getRecruteur()).isNull();
    }

    @Test
    void typeContratTest() {
        OffreEmploi offreEmploi = getOffreEmploiRandomSampleGenerator();
        TypeContrat typeContratBack = getTypeContratRandomSampleGenerator();

        offreEmploi.setTypeContrat(typeContratBack);
        assertThat(offreEmploi.getTypeContrat()).isEqualTo(typeContratBack);

        offreEmploi.typeContrat(null);
        assertThat(offreEmploi.getTypeContrat()).isNull();
    }

    @Test
    void posteTest() {
        OffreEmploi offreEmploi = getOffreEmploiRandomSampleGenerator();
        Poste posteBack = getPosteRandomSampleGenerator();

        offreEmploi.setPoste(posteBack);
        assertThat(offreEmploi.getPoste()).isEqualTo(posteBack);

        offreEmploi.poste(null);
        assertThat(offreEmploi.getPoste()).isNull();
    }

    @Test
    void localisationTest() {
        OffreEmploi offreEmploi = getOffreEmploiRandomSampleGenerator();
        Localisation localisationBack = getLocalisationRandomSampleGenerator();

        offreEmploi.setLocalisation(localisationBack);
        assertThat(offreEmploi.getLocalisation()).isEqualTo(localisationBack);

        offreEmploi.localisation(null);
        assertThat(offreEmploi.getLocalisation()).isNull();
    }
}
