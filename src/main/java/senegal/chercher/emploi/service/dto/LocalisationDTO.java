package senegal.chercher.emploi.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link senegal.chercher.emploi.domain.Localisation} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LocalisationDTO implements Serializable {

    private Long id;

    @NotNull
    private String region;

    @NotNull
    private String departement;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocalisationDTO)) {
            return false;
        }

        LocalisationDTO localisationDTO = (LocalisationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, localisationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LocalisationDTO{" +
            "id=" + getId() +
            ", region='" + getRegion() + "'" +
            ", departement='" + getDepartement() + "'" +
            "}";
    }
}
