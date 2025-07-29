package senegal.chercher.emploi.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senegal.chercher.emploi.domain.TypeContrat;
import senegal.chercher.emploi.repository.TypeContratRepository;
import senegal.chercher.emploi.service.dto.TypeContratDTO;
import senegal.chercher.emploi.service.mapper.TypeContratMapper;

/**
 * Service Implementation for managing {@link senegal.chercher.emploi.domain.TypeContrat}.
 */
@Service
@Transactional
public class TypeContratService {

    private static final Logger LOG = LoggerFactory.getLogger(TypeContratService.class);

    private final TypeContratRepository typeContratRepository;

    private final TypeContratMapper typeContratMapper;

    public TypeContratService(TypeContratRepository typeContratRepository, TypeContratMapper typeContratMapper) {
        this.typeContratRepository = typeContratRepository;
        this.typeContratMapper = typeContratMapper;
    }

    /**
     * Save a typeContrat.
     *
     * @param typeContratDTO the entity to save.
     * @return the persisted entity.
     */
    public TypeContratDTO save(TypeContratDTO typeContratDTO) {
        LOG.debug("Request to save TypeContrat : {}", typeContratDTO);
        TypeContrat typeContrat = typeContratMapper.toEntity(typeContratDTO);
        typeContrat = typeContratRepository.save(typeContrat);
        return typeContratMapper.toDto(typeContrat);
    }

    /**
     * Update a typeContrat.
     *
     * @param typeContratDTO the entity to save.
     * @return the persisted entity.
     */
    public TypeContratDTO update(TypeContratDTO typeContratDTO) {
        LOG.debug("Request to update TypeContrat : {}", typeContratDTO);
        TypeContrat typeContrat = typeContratMapper.toEntity(typeContratDTO);
        typeContrat = typeContratRepository.save(typeContrat);
        return typeContratMapper.toDto(typeContrat);
    }

    /**
     * Partially update a typeContrat.
     *
     * @param typeContratDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TypeContratDTO> partialUpdate(TypeContratDTO typeContratDTO) {
        LOG.debug("Request to partially update TypeContrat : {}", typeContratDTO);

        return typeContratRepository
            .findById(typeContratDTO.getId())
            .map(existingTypeContrat -> {
                typeContratMapper.partialUpdate(existingTypeContrat, typeContratDTO);

                return existingTypeContrat;
            })
            .map(typeContratRepository::save)
            .map(typeContratMapper::toDto);
    }

    /**
     * Get all the typeContrats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TypeContratDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all TypeContrats");
        return typeContratRepository.findAll(pageable).map(typeContratMapper::toDto);
    }

    /**
     * Get one typeContrat by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TypeContratDTO> findOne(Long id) {
        LOG.debug("Request to get TypeContrat : {}", id);
        return typeContratRepository.findById(id).map(typeContratMapper::toDto);
    }

    /**
     * Delete the typeContrat by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete TypeContrat : {}", id);
        typeContratRepository.deleteById(id);
    }
}
