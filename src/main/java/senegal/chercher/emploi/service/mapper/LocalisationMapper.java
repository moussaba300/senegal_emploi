package senegal.chercher.emploi.service.mapper;

import org.mapstruct.*;
import senegal.chercher.emploi.domain.Localisation;
import senegal.chercher.emploi.service.dto.LocalisationDTO;

/**
 * Mapper for the entity {@link Localisation} and its DTO {@link LocalisationDTO}.
 */
@Mapper(componentModel = "spring")
public interface LocalisationMapper extends EntityMapper<LocalisationDTO, Localisation> {}
