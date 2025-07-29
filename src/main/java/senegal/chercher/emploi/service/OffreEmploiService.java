package senegal.chercher.emploi.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senegal.chercher.emploi.domain.OffreEmploi;
import senegal.chercher.emploi.repository.OffreEmploiRepository;
import senegal.chercher.emploi.service.dto.OffreEmploiDTO;
import senegal.chercher.emploi.service.mapper.OffreEmploiMapper;

/**
 * Service Implementation for managing {@link senegal.chercher.emploi.domain.OffreEmploi}.
 */
@Service
@Transactional
public class OffreEmploiService {

    private static final Logger LOG = LoggerFactory.getLogger(OffreEmploiService.class);

    private final OffreEmploiRepository offreEmploiRepository;

    private final OffreEmploiMapper offreEmploiMapper;

    public OffreEmploiService(OffreEmploiRepository offreEmploiRepository, OffreEmploiMapper offreEmploiMapper) {
        this.offreEmploiRepository = offreEmploiRepository;
        this.offreEmploiMapper = offreEmploiMapper;
    }

    /**
     * Save a offreEmploi.
     *
     * @param offreEmploiDTO the entity to save.
     * @return the persisted entity.
     */
    public OffreEmploiDTO save(OffreEmploiDTO offreEmploiDTO) {
        LOG.debug("Request to save OffreEmploi : {}", offreEmploiDTO);
        OffreEmploi offreEmploi = offreEmploiMapper.toEntity(offreEmploiDTO);
        offreEmploi = offreEmploiRepository.save(offreEmploi);
        return offreEmploiMapper.toDto(offreEmploi);
    }

    /**
     * Update a offreEmploi.
     *
     * @param offreEmploiDTO the entity to save.
     * @return the persisted entity.
     */
    public OffreEmploiDTO update(OffreEmploiDTO offreEmploiDTO) {
        LOG.debug("Request to update OffreEmploi : {}", offreEmploiDTO);
        OffreEmploi offreEmploi = offreEmploiMapper.toEntity(offreEmploiDTO);
        offreEmploi = offreEmploiRepository.save(offreEmploi);
        return offreEmploiMapper.toDto(offreEmploi);
    }

    /**
     * Partially update a offreEmploi.
     *
     * @param offreEmploiDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OffreEmploiDTO> partialUpdate(OffreEmploiDTO offreEmploiDTO) {
        LOG.debug("Request to partially update OffreEmploi : {}", offreEmploiDTO);

        return offreEmploiRepository
            .findById(offreEmploiDTO.getId())
            .map(existingOffreEmploi -> {
                offreEmploiMapper.partialUpdate(existingOffreEmploi, offreEmploiDTO);

                return existingOffreEmploi;
            })
            .map(offreEmploiRepository::save)
            .map(offreEmploiMapper::toDto);
    }

    /**
     * Get all the offreEmplois.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OffreEmploiDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all OffreEmplois");
        return offreEmploiRepository.findAll(pageable).map(offreEmploiMapper::toDto);
    }

    /**
     * Get one offreEmploi by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OffreEmploiDTO> findOne(Long id) {
        LOG.debug("Request to get OffreEmploi : {}", id);
        return offreEmploiRepository.findById(id).map(offreEmploiMapper::toDto);
    }

    /**
     * Delete the offreEmploi by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete OffreEmploi : {}", id);
        offreEmploiRepository.deleteById(id);
    }
}
