package senegal.chercher.emploi.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link senegal.chercher.emploi.domain.OffreEmploi} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OffreEmploiDTO implements Serializable {

    private Long id;

    @NotNull
    private String titre;

    @NotNull
    private String description;

    private Double remuneration;

    private Instant datePublication;

    private String imagePath;

    private RecruteurDTO recruteur;

    private TypeContratDTO typeContrat;

    private PosteDTO poste;

    private LocalisationDTO localisation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRemuneration() {
        return remuneration;
    }

    public void setRemuneration(Double remuneration) {
        this.remuneration = remuneration;
    }

    public Instant getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(Instant datePublication) {
        this.datePublication = datePublication;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public RecruteurDTO getRecruteur() {
        return recruteur;
    }

    public void setRecruteur(RecruteurDTO recruteur) {
        this.recruteur = recruteur;
    }

    public TypeContratDTO getTypeContrat() {
        return typeContrat;
    }

    public void setTypeContrat(TypeContratDTO typeContrat) {
        this.typeContrat = typeContrat;
    }

    public PosteDTO getPoste() {
        return poste;
    }

    public void setPoste(PosteDTO poste) {
        this.poste = poste;
    }

    public LocalisationDTO getLocalisation() {
        return localisation;
    }

    public void setLocalisation(LocalisationDTO localisation) {
        this.localisation = localisation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OffreEmploiDTO)) {
            return false;
        }

        OffreEmploiDTO offreEmploiDTO = (OffreEmploiDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, offreEmploiDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OffreEmploiDTO{" +
            "id=" + getId() +
            ", titre='" + getTitre() + "'" +
            ", description='" + getDescription() + "'" +
            ", remuneration=" + getRemuneration() +
            ", datePublication='" + getDatePublication() + "'" +
            ", imagePath='" + getImagePath() + "'" +
            ", recruteur=" + getRecruteur() +
            ", typeContrat=" + getTypeContrat() +
            ", poste=" + getPoste() +
            ", localisation=" + getLocalisation() +
            "}";
    }
}
