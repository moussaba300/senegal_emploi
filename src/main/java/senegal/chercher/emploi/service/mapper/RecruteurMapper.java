package senegal.chercher.emploi.service.mapper;

import org.mapstruct.*;
import senegal.chercher.emploi.domain.Recruteur;
import senegal.chercher.emploi.domain.Utilisateur;
import senegal.chercher.emploi.service.dto.RecruteurDTO;
import senegal.chercher.emploi.service.dto.UtilisateurDTO;

/**
 * Mapper for the entity {@link Recruteur} and its DTO {@link RecruteurDTO}.
 */
@Mapper(componentModel = "spring")
public interface RecruteurMapper extends EntityMapper<RecruteurDTO, Recruteur> {
    @Mapping(target = "utilisateur", source = "utilisateur", qualifiedByName = "utilisateurId")
    RecruteurDTO toDto(Recruteur s);

    @Named("utilisateurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UtilisateurDTO toDtoUtilisateurId(Utilisateur utilisateur);
}
