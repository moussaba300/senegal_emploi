package senegal.chercher.emploi.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senegal.chercher.emploi.domain.Recruteur;
import senegal.chercher.emploi.repository.RecruteurRepository;
import senegal.chercher.emploi.service.dto.RecruteurDTO;
import senegal.chercher.emploi.service.mapper.RecruteurMapper;

/**
 * Service Implementation for managing {@link senegal.chercher.emploi.domain.Recruteur}.
 */
@Service
@Transactional
public class RecruteurService {

    private static final Logger LOG = LoggerFactory.getLogger(RecruteurService.class);

    private final RecruteurRepository recruteurRepository;

    private final RecruteurMapper recruteurMapper;

    public RecruteurService(RecruteurRepository recruteurRepository, RecruteurMapper recruteurMapper) {
        this.recruteurRepository = recruteurRepository;
        this.recruteurMapper = recruteurMapper;
    }

    /**
     * Save a recruteur.
     *
     * @param recruteurDTO the entity to save.
     * @return the persisted entity.
     */
    public RecruteurDTO save(RecruteurDTO recruteurDTO) {
        LOG.debug("Request to save Recruteur : {}", recruteurDTO);
        Recruteur recruteur = recruteurMapper.toEntity(recruteurDTO);
        recruteur = recruteurRepository.save(recruteur);
        return recruteurMapper.toDto(recruteur);
    }

    /**
     * Update a recruteur.
     *
     * @param recruteurDTO the entity to save.
     * @return the persisted entity.
     */
    public RecruteurDTO update(RecruteurDTO recruteurDTO) {
        LOG.debug("Request to update Recruteur : {}", recruteurDTO);
        Recruteur recruteur = recruteurMapper.toEntity(recruteurDTO);
        recruteur = recruteurRepository.save(recruteur);
        return recruteurMapper.toDto(recruteur);
    }

    /**
     * Partially update a recruteur.
     *
     * @param recruteurDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RecruteurDTO> partialUpdate(RecruteurDTO recruteurDTO) {
        LOG.debug("Request to partially update Recruteur : {}", recruteurDTO);

        return recruteurRepository
            .findById(recruteurDTO.getId())
            .map(existingRecruteur -> {
                recruteurMapper.partialUpdate(existingRecruteur, recruteurDTO);

                return existingRecruteur;
            })
            .map(recruteurRepository::save)
            .map(recruteurMapper::toDto);
    }

    /**
     * Get all the recruteurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RecruteurDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Recruteurs");
        return recruteurRepository.findAll(pageable).map(recruteurMapper::toDto);
    }

    /**
     * Get one recruteur by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RecruteurDTO> findOne(Long id) {
        LOG.debug("Request to get Recruteur : {}", id);
        return recruteurRepository.findById(id).map(recruteurMapper::toDto);
    }

    /**
     * Delete the recruteur by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Recruteur : {}", id);
        recruteurRepository.deleteById(id);
    }
}
