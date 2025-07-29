package senegal.chercher.emploi.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import senegal.chercher.emploi.repository.LocalisationRepository;
import senegal.chercher.emploi.service.LocalisationService;
import senegal.chercher.emploi.service.dto.LocalisationDTO;
import senegal.chercher.emploi.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link senegal.chercher.emploi.domain.Localisation}.
 */
@RestController
@RequestMapping("/api/localisations")
public class LocalisationResource {

    private static final Logger LOG = LoggerFactory.getLogger(LocalisationResource.class);

    private static final String ENTITY_NAME = "localisation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocalisationService localisationService;

    private final LocalisationRepository localisationRepository;

    public LocalisationResource(LocalisationService localisationService, LocalisationRepository localisationRepository) {
        this.localisationService = localisationService;
        this.localisationRepository = localisationRepository;
    }

    /**
     * {@code POST  /localisations} : Create a new localisation.
     *
     * @param localisationDTO the localisationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new localisationDTO, or with status {@code 400 (Bad Request)} if the localisation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<LocalisationDTO> createLocalisation(@Valid @RequestBody LocalisationDTO localisationDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save Localisation : {}", localisationDTO);
        if (localisationDTO.getId() != null) {
            throw new BadRequestAlertException("A new localisation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        localisationDTO = localisationService.save(localisationDTO);
        return ResponseEntity.created(new URI("/api/localisations/" + localisationDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, localisationDTO.getId().toString()))
            .body(localisationDTO);
    }

    /**
     * {@code PUT  /localisations/:id} : Updates an existing localisation.
     *
     * @param id the id of the localisationDTO to save.
     * @param localisationDTO the localisationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated localisationDTO,
     * or with status {@code 400 (Bad Request)} if the localisationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the localisationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LocalisationDTO> updateLocalisation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LocalisationDTO localisationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Localisation : {}, {}", id, localisationDTO);
        if (localisationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, localisationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!localisationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        localisationDTO = localisationService.update(localisationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, localisationDTO.getId().toString()))
            .body(localisationDTO);
    }

    /**
     * {@code PATCH  /localisations/:id} : Partial updates given fields of an existing localisation, field will ignore if it is null
     *
     * @param id the id of the localisationDTO to save.
     * @param localisationDTO the localisationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated localisationDTO,
     * or with status {@code 400 (Bad Request)} if the localisationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the localisationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the localisationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LocalisationDTO> partialUpdateLocalisation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LocalisationDTO localisationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Localisation partially : {}, {}", id, localisationDTO);
        if (localisationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, localisationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!localisationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LocalisationDTO> result = localisationService.partialUpdate(localisationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, localisationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /localisations} : get all the localisations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of localisations in body.
     */
    @GetMapping("")
    public ResponseEntity<List<LocalisationDTO>> getAllLocalisations(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Localisations");
        Page<LocalisationDTO> page = localisationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /localisations/:id} : get the "id" localisation.
     *
     * @param id the id of the localisationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the localisationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LocalisationDTO> getLocalisation(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Localisation : {}", id);
        Optional<LocalisationDTO> localisationDTO = localisationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(localisationDTO);
    }

    /**
     * {@code DELETE  /localisations/:id} : delete the "id" localisation.
     *
     * @param id the id of the localisationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocalisation(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Localisation : {}", id);
        localisationService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
