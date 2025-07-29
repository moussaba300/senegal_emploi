package senegal.chercher.emploi.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senegal.chercher.emploi.domain.Poste;
import senegal.chercher.emploi.repository.PosteRepository;
import senegal.chercher.emploi.service.dto.PosteDTO;
import senegal.chercher.emploi.service.mapper.PosteMapper;

/**
 * Service Implementation for managing {@link senegal.chercher.emploi.domain.Poste}.
 */
@Service
@Transactional
public class PosteService {

    private static final Logger LOG = LoggerFactory.getLogger(PosteService.class);

    private final PosteRepository posteRepository;

    private final PosteMapper posteMapper;

    public PosteService(PosteRepository posteRepository, PosteMapper posteMapper) {
        this.posteRepository = posteRepository;
        this.posteMapper = posteMapper;
    }

    /**
     * Save a poste.
     *
     * @param posteDTO the entity to save.
     * @return the persisted entity.
     */
    public PosteDTO save(PosteDTO posteDTO) {
        LOG.debug("Request to save Poste : {}", posteDTO);
        Poste poste = posteMapper.toEntity(posteDTO);
        poste = posteRepository.save(poste);
        return posteMapper.toDto(poste);
    }

    /**
     * Update a poste.
     *
     * @param posteDTO the entity to save.
     * @return the persisted entity.
     */
    public PosteDTO update(PosteDTO posteDTO) {
        LOG.debug("Request to update Poste : {}", posteDTO);
        Poste poste = posteMapper.toEntity(posteDTO);
        poste = posteRepository.save(poste);
        return posteMapper.toDto(poste);
    }

    /**
     * Partially update a poste.
     *
     * @param posteDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PosteDTO> partialUpdate(PosteDTO posteDTO) {
        LOG.debug("Request to partially update Poste : {}", posteDTO);

        return posteRepository
            .findById(posteDTO.getId())
            .map(existingPoste -> {
                posteMapper.partialUpdate(existingPoste, posteDTO);

                return existingPoste;
            })
            .map(posteRepository::save)
            .map(posteMapper::toDto);
    }

    /**
     * Get all the postes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PosteDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Postes");
        return posteRepository.findAll(pageable).map(posteMapper::toDto);
    }

    /**
     * Get one poste by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PosteDTO> findOne(Long id) {
        LOG.debug("Request to get Poste : {}", id);
        return posteRepository.findById(id).map(posteMapper::toDto);
    }

    /**
     * Delete the poste by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Poste : {}", id);
        posteRepository.deleteById(id);
    }
}
