package senegal.chercher.emploi.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link senegal.chercher.emploi.domain.Recruteur} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RecruteurDTO implements Serializable {

    private Long id;

    @NotNull
    private String entreprise;

    @NotNull
    private String secteur;

    private String logoPath;

    private UtilisateurDTO utilisateur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }

    public String getSecteur() {
        return secteur;
    }

    public void setSecteur(String secteur) {
        this.secteur = secteur;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public UtilisateurDTO getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(UtilisateurDTO utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecruteurDTO)) {
            return false;
        }

        RecruteurDTO recruteurDTO = (RecruteurDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, recruteurDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecruteurDTO{" +
            "id=" + getId() +
            ", entreprise='" + getEntreprise() + "'" +
            ", secteur='" + getSecteur() + "'" +
            ", logoPath='" + getLogoPath() + "'" +
            ", utilisateur=" + getUtilisateur() +
            "}";
    }
}
