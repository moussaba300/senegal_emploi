package senegal.chercher.emploi.service.mapper;

import org.mapstruct.*;
import senegal.chercher.emploi.domain.Candidat;
import senegal.chercher.emploi.domain.Candidature;
import senegal.chercher.emploi.domain.OffreEmploi;
import senegal.chercher.emploi.service.dto.CandidatDTO;
import senegal.chercher.emploi.service.dto.CandidatureDTO;
import senegal.chercher.emploi.service.dto.OffreEmploiDTO;

/**
 * Mapper for the entity {@link Candidature} and its DTO {@link CandidatureDTO}.
 */
@Mapper(componentModel = "spring")
public interface CandidatureMapper extends EntityMapper<CandidatureDTO, Candidature> {
    @Mapping(target = "offre", source = "offre", qualifiedByName = "offreEmploiId")
    @Mapping(target = "candidat", source = "candidat", qualifiedByName = "candidatId")
    CandidatureDTO toDto(Candidature s);

    @Named("offreEmploiId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OffreEmploiDTO toDtoOffreEmploiId(OffreEmploi offreEmploi);

    @Named("candidatId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CandidatDTO toDtoCandidatId(Candidat candidat);
}
