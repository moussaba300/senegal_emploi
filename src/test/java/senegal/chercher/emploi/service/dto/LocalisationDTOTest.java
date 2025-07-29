package senegal.chercher.emploi.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import senegal.chercher.emploi.web.rest.TestUtil;

class LocalisationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocalisationDTO.class);
        LocalisationDTO localisationDTO1 = new LocalisationDTO();
        localisationDTO1.setId(1L);
        LocalisationDTO localisationDTO2 = new LocalisationDTO();
        assertThat(localisationDTO1).isNotEqualTo(localisationDTO2);
        localisationDTO2.setId(localisationDTO1.getId());
        assertThat(localisationDTO1).isEqualTo(localisationDTO2);
        localisationDTO2.setId(2L);
        assertThat(localisationDTO1).isNotEqualTo(localisationDTO2);
        localisationDTO1.setId(null);
        assertThat(localisationDTO1).isNotEqualTo(localisationDTO2);
    }
}
