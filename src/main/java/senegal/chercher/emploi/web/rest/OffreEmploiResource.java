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
import senegal.chercher.emploi.repository.OffreEmploiRepository;
import senegal.chercher.emploi.service.OffreEmploiService;
import senegal.chercher.emploi.service.dto.OffreEmploiDTO;
import senegal.chercher.emploi.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link senegal.chercher.emploi.domain.OffreEmploi}.
 */
@RestController
@RequestMapping("/api/offre-emplois")
public class OffreEmploiResource {

    private static final Logger LOG = LoggerFactory.getLogger(OffreEmploiResource.class);

    private static final String ENTITY_NAME = "offreEmploi";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OffreEmploiService offreEmploiService;

    private final OffreEmploiRepository offreEmploiRepository;

    public OffreEmploiResource(OffreEmploiService offreEmploiService, OffreEmploiRepository offreEmploiRepository) {
        this.offreEmploiService = offreEmploiService;
        this.offreEmploiRepository = offreEmploiRepository;
    }

    /**
     * {@code POST  /offre-emplois} : Create a new offreEmploi.
     *
     * @param offreEmploiDTO the offreEmploiDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new offreEmploiDTO, or with status {@code 400 (Bad Request)} if the offreEmploi has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OffreEmploiDTO> createOffreEmploi(@Valid @RequestBody OffreEmploiDTO offreEmploiDTO) throws URISyntaxException {
        LOG.debug("REST request to save OffreEmploi : {}", offreEmploiDTO);
        if (offreEmploiDTO.getId() != null) {
            throw new BadRequestAlertException("A new offreEmploi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        offreEmploiDTO = offreEmploiService.save(offreEmploiDTO);
        return ResponseEntity.created(new URI("/api/offre-emplois/" + offreEmploiDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, offreEmploiDTO.getId().toString()))
            .body(offreEmploiDTO);
    }

    /**
     * {@code PUT  /offre-emplois/:id} : Updates an existing offreEmploi.
     *
     * @param id the id of the offreEmploiDTO to save.
     * @param offreEmploiDTO the offreEmploiDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated offreEmploiDTO,
     * or with status {@code 400 (Bad Request)} if the offreEmploiDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the offreEmploiDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OffreEmploiDTO> updateOffreEmploi(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OffreEmploiDTO offreEmploiDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update OffreEmploi : {}, {}", id, offreEmploiDTO);
        if (offreEmploiDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, offreEmploiDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!offreEmploiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        offreEmploiDTO = offreEmploiService.update(offreEmploiDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, offreEmploiDTO.getId().toString()))
            .body(offreEmploiDTO);
    }

    /**
     * {@code PATCH  /offre-emplois/:id} : Partial updates given fields of an existing offreEmploi, field will ignore if it is null
     *
     * @param id the id of the offreEmploiDTO to save.
     * @param offreEmploiDTO the offreEmploiDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated offreEmploiDTO,
     * or with status {@code 400 (Bad Request)} if the offreEmploiDTO is not valid,
     * or with status {@code 404 (Not Found)} if the offreEmploiDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the offreEmploiDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OffreEmploiDTO> partialUpdateOffreEmploi(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OffreEmploiDTO offreEmploiDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update OffreEmploi partially : {}, {}", id, offreEmploiDTO);
        if (offreEmploiDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, offreEmploiDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!offreEmploiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OffreEmploiDTO> result = offreEmploiService.partialUpdate(offreEmploiDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, offreEmploiDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /offre-emplois} : get all the offreEmplois.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of offreEmplois in body.
     */
    @GetMapping("")
    public ResponseEntity<List<OffreEmploiDTO>> getAllOffreEmplois(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of OffreEmplois");
        Page<OffreEmploiDTO> page = offreEmploiService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /offre-emplois/:id} : get the "id" offreEmploi.
     *
     * @param id the id of the offreEmploiDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the offreEmploiDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OffreEmploiDTO> getOffreEmploi(@PathVariable("id") Long id) {
        LOG.debug("REST request to get OffreEmploi : {}", id);
        Optional<OffreEmploiDTO> offreEmploiDTO = offreEmploiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(offreEmploiDTO);
    }

    /**
     * {@code DELETE  /offre-emplois/:id} : delete the "id" offreEmploi.
     *
     * @param id the id of the offreEmploiDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffreEmploi(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete OffreEmploi : {}", id);
        offreEmploiService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
