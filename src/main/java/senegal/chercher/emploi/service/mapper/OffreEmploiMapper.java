package senegal.chercher.emploi.service.mapper;

import org.mapstruct.*;
import senegal.chercher.emploi.domain.Localisation;
import senegal.chercher.emploi.domain.OffreEmploi;
import senegal.chercher.emploi.domain.Poste;
import senegal.chercher.emploi.domain.Recruteur;
import senegal.chercher.emploi.domain.TypeContrat;
import senegal.chercher.emploi.service.dto.LocalisationDTO;
import senegal.chercher.emploi.service.dto.OffreEmploiDTO;
import senegal.chercher.emploi.service.dto.PosteDTO;
import senegal.chercher.emploi.service.dto.RecruteurDTO;
import senegal.chercher.emploi.service.dto.TypeContratDTO;

/**
 * Mapper for the entity {@link OffreEmploi} and its DTO {@link OffreEmploiDTO}.
 */
@Mapper(componentModel = "spring")
public interface OffreEmploiMapper extends EntityMapper<OffreEmploiDTO, OffreEmploi> {
    @Mapping(target = "recruteur", source = "recruteur", qualifiedByName = "recruteurId")
    @Mapping(target = "typeContrat", source = "typeContrat", qualifiedByName = "typeContratId")
    @Mapping(target = "poste", source = "poste", qualifiedByName = "posteId")
    @Mapping(target = "localisation", source = "localisation", qualifiedByName = "localisationId")
    OffreEmploiDTO toDto(OffreEmploi s);

    @Named("recruteurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RecruteurDTO toDtoRecruteurId(Recruteur recruteur);

    @Named("typeContratId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TypeContratDTO toDtoTypeContratId(TypeContrat typeContrat);

    @Named("posteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PosteDTO toDtoPosteId(Poste poste);

    @Named("localisationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocalisationDTO toDtoLocalisationId(Localisation localisation);
}
