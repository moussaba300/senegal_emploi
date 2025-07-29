package senegal.chercher.emploi.service.mapper;

import org.mapstruct.*;
import senegal.chercher.emploi.domain.Candidat;
import senegal.chercher.emploi.domain.Utilisateur;
import senegal.chercher.emploi.service.dto.CandidatDTO;
import senegal.chercher.emploi.service.dto.UtilisateurDTO;

/**
 * Mapper for the entity {@link Candidat} and its DTO {@link CandidatDTO}.
 */
@Mapper(componentModel = "spring")
public interface CandidatMapper extends EntityMapper<CandidatDTO, Candidat> {
    @Mapping(target = "utilisateur", source = "utilisateur", qualifiedByName = "utilisateurId")
    CandidatDTO toDto(Candidat s);

    @Named("utilisateurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UtilisateurDTO toDtoUtilisateurId(Utilisateur utilisateur);
}
