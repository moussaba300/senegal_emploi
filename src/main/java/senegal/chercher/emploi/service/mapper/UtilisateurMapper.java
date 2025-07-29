package senegal.chercher.emploi.service.mapper;

import org.mapstruct.*;
import senegal.chercher.emploi.domain.Utilisateur;
import senegal.chercher.emploi.service.dto.UtilisateurDTO;

/**
 * Mapper for the entity {@link Utilisateur} and its DTO {@link UtilisateurDTO}.
 */
@Mapper(componentModel = "spring")
public interface UtilisateurMapper extends EntityMapper<UtilisateurDTO, Utilisateur> {}
