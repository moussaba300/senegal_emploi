package senegal.chercher.emploi.web.rest;

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
import senegal.chercher.emploi.repository.CandidatureRepository;
import senegal.chercher.emploi.service.CandidatureService;
import senegal.chercher.emploi.service.dto.CandidatureDTO;
import senegal.chercher.emploi.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link senegal.chercher.emploi.domain.Candidature}.
 */
@RestController
@RequestMapping("/api/candidatures")
public class CandidatureResource {

    private static final Logger LOG = LoggerFactory.getLogger(CandidatureResource.class);

    private static final String ENTITY_NAME = "candidature";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CandidatureService candidatureService;

    private final CandidatureRepository candidatureRepository;

    public CandidatureResource(CandidatureService candidatureService, CandidatureRepository candidatureRepository) {
        this.candidatureService = candidatureService;
        this.candidatureRepository = candidatureRepository;
    }

    /**
     * {@code POST  /candidatures} : Create a new candidature.
     *
     * @param candidatureDTO the candidatureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new candidatureDTO, or with status {@code 400 (Bad Request)} if the candidature has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CandidatureDTO> createCandidature(@RequestBody CandidatureDTO candidatureDTO) throws URISyntaxException {
        LOG.debug("REST request to save Candidature : {}", candidatureDTO);
        if (candidatureDTO.getId() != null) {
            throw new BadRequestAlertException("A new candidature cannot already have an ID", ENTITY_NAME, "idexists");
        }
        candidatureDTO = candidatureService.save(candidatureDTO);
        return ResponseEntity.created(new URI("/api/candidatures/" + candidatureDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, candidatureDTO.getId().toString()))
            .body(candidatureDTO);
    }

    /**
     * {@code PUT  /candidatures/:id} : Updates an existing candidature.
     *
     * @param id the id of the candidatureDTO to save.
     * @param candidatureDTO the candidatureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated candidatureDTO,
     * or with status {@code 400 (Bad Request)} if the candidatureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the candidatureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CandidatureDTO> updateCandidature(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CandidatureDTO candidatureDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Candidature : {}, {}", id, candidatureDTO);
        if (candidatureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, candidatureDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!candidatureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        candidatureDTO = candidatureService.update(candidatureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, candidatureDTO.getId().toString()))
            .body(candidatureDTO);
    }

    /**
     * {@code PATCH  /candidatures/:id} : Partial updates given fields of an existing candidature, field will ignore if it is null
     *
     * @param id the id of the candidatureDTO to save.
     * @param candidatureDTO the candidatureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated candidatureDTO,
     * or with status {@code 400 (Bad Request)} if the candidatureDTO is not valid,
     * or with status {@code 404 (Not Found)} if the candidatureDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the candidatureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CandidatureDTO> partialUpdateCandidature(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CandidatureDTO candidatureDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Candidature partially : {}, {}", id, candidatureDTO);
        if (candidatureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, candidatureDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!candidatureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CandidatureDTO> result = candidatureService.partialUpdate(candidatureDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, candidatureDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /candidatures} : get all the candidatures.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of candidatures in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CandidatureDTO>> getAllCandidatures(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Candidatures");
        Page<CandidatureDTO> page = candidatureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /candidatures/:id} : get the "id" candidature.
     *
     * @param id the id of the candidatureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the candidatureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CandidatureDTO> getCandidature(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Candidature : {}", id);
        Optional<CandidatureDTO> candidatureDTO = candidatureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(candidatureDTO);
    }

    /**
     * {@code DELETE  /candidatures/:id} : delete the "id" candidature.
     *
     * @param id the id of the candidatureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidature(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Candidature : {}", id);
        candidatureService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
