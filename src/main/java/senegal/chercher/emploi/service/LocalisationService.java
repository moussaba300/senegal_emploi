package senegal.chercher.emploi.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senegal.chercher.emploi.domain.Localisation;
import senegal.chercher.emploi.repository.LocalisationRepository;
import senegal.chercher.emploi.service.dto.LocalisationDTO;
import senegal.chercher.emploi.service.mapper.LocalisationMapper;

/**
 * Service Implementation for managing {@link senegal.chercher.emploi.domain.Localisation}.
 */
@Service
@Transactional
public class LocalisationService {

    private static final Logger LOG = LoggerFactory.getLogger(LocalisationService.class);

    private final LocalisationRepository localisationRepository;

    private final LocalisationMapper localisationMapper;

    public LocalisationService(LocalisationRepository localisationRepository, LocalisationMapper localisationMapper) {
        this.localisationRepository = localisationRepository;
        this.localisationMapper = localisationMapper;
    }

    /**
     * Save a localisation.
     *
     * @param localisationDTO the entity to save.
     * @return the persisted entity.
     */
    public LocalisationDTO save(LocalisationDTO localisationDTO) {
        LOG.debug("Request to save Localisation : {}", localisationDTO);
        Localisation localisation = localisationMapper.toEntity(localisationDTO);
        localisation = localisationRepository.save(localisation);
        return localisationMapper.toDto(localisation);
    }

    /**
     * Update a localisation.
     *
     * @param localisationDTO the entity to save.
     * @return the persisted entity.
     */
    public LocalisationDTO update(LocalisationDTO localisationDTO) {
        LOG.debug("Request to update Localisation : {}", localisationDTO);
        Localisation localisation = localisationMapper.toEntity(localisationDTO);
        localisation = localisationRepository.save(localisation);
        return localisationMapper.toDto(localisation);
    }

    /**
     * Partially update a localisation.
     *
     * @param localisationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LocalisationDTO> partialUpdate(LocalisationDTO localisationDTO) {
        LOG.debug("Request to partially update Localisation : {}", localisationDTO);

        return localisationRepository
            .findById(localisationDTO.getId())
            .map(existingLocalisation -> {
                localisationMapper.partialUpdate(existingLocalisation, localisationDTO);

                return existingLocalisation;
            })
            .map(localisationRepository::save)
            .map(localisationMapper::toDto);
    }

    /**
     * Get all the localisations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LocalisationDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Localisations");
        return localisationRepository.findAll(pageable).map(localisationMapper::toDto);
    }

    /**
     * Get one localisation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LocalisationDTO> findOne(Long id) {
        LOG.debug("Request to get Localisation : {}", id);
        return localisationRepository.findById(id).map(localisationMapper::toDto);
    }

    /**
     * Delete the localisation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Localisation : {}", id);
        localisationRepository.deleteById(id);
    }
}
