package senegal.chercher.emploi.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import senegal.chercher.emploi.domain.OffreEmploi;

/**
 * Spring Data JPA repository for the OffreEmploi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OffreEmploiRepository extends JpaRepository<OffreEmploi, Long> {}
