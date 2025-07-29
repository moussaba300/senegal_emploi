package senegal.chercher.emploi.service.mapper;

import org.mapstruct.*;
import senegal.chercher.emploi.domain.TypeContrat;
import senegal.chercher.emploi.service.dto.TypeContratDTO;

/**
 * Mapper for the entity {@link TypeContrat} and its DTO {@link TypeContratDTO}.
 */
@Mapper(componentModel = "spring")
public interface TypeContratMapper extends EntityMapper<TypeContratDTO, TypeContrat> {}
