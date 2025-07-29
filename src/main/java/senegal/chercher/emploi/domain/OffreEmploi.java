package senegal.chercher.emploi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OffreEmploi.
 */
@Entity
@Table(name = "offre_emploi")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OffreEmploi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "titre", nullable = false)
    private String titre;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "remuneration")
    private Double remuneration;

    @Column(name = "date_publication")
    private Instant datePublication;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "utilisateur" }, allowSetters = true)
    private Recruteur recruteur;

    @ManyToOne(fetch = FetchType.LAZY)
    private TypeContrat typeContrat;

    @ManyToOne(fetch = FetchType.LAZY)
    private Poste poste;

    @ManyToOne(fetch = FetchType.LAZY)
    private Localisation localisation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OffreEmploi id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return this.titre;
    }

    public OffreEmploi titre(String titre) {
        this.setTitre(titre);
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return this.description;
    }

    public OffreEmploi description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRemuneration() {
        return this.remuneration;
    }

    public OffreEmploi remuneration(Double remuneration) {
        this.setRemuneration(remuneration);
        return this;
    }

    public void setRemuneration(Double remuneration) {
        this.remuneration = remuneration;
    }

    public Instant getDatePublication() {
        return this.datePublication;
    }

    public OffreEmploi datePublication(Instant datePublication) {
        this.setDatePublication(datePublication);
        return this;
    }

    public void setDatePublication(Instant datePublication) {
        this.datePublication = datePublication;
    }

    public Recruteur getRecruteur() {
        return this.recruteur;
    }

    public void setRecruteur(Recruteur recruteur) {
        this.recruteur = recruteur;
    }

    public OffreEmploi recruteur(Recruteur recruteur) {
        this.setRecruteur(recruteur);
        return this;
    }

    public TypeContrat getTypeContrat() {
        return this.typeContrat;
    }

    public void setTypeContrat(TypeContrat typeContrat) {
        this.typeContrat = typeContrat;
    }

    public OffreEmploi typeContrat(TypeContrat typeContrat) {
        this.setTypeContrat(typeContrat);
        return this;
    }

    public Poste getPoste() {
        return this.poste;
    }

    public void setPoste(Poste poste) {
        this.poste = poste;
    }

    public OffreEmploi poste(Poste poste) {
        this.setPoste(poste);
        return this;
    }

    public Localisation getLocalisation() {
        return this.localisation;
    }

    public void setLocalisation(Localisation localisation) {
        this.localisation = localisation;
    }

    public OffreEmploi localisation(Localisation localisation) {
        this.setLocalisation(localisation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OffreEmploi)) {
            return false;
        }
        return getId() != null && getId().equals(((OffreEmploi) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OffreEmploi{" +
            "id=" + getId() +
            ", titre='" + getTitre() + "'" +
            ", description='" + getDescription() + "'" +
            ", remuneration=" + getRemuneration() +
            ", datePublication='" + getDatePublication() + "'" +
            "}";
    }
}
