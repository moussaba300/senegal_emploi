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
import senegal.chercher.emploi.repository.RecruteurRepository;
import senegal.chercher.emploi.service.RecruteurService;
import senegal.chercher.emploi.service.dto.RecruteurDTO;
import senegal.chercher.emploi.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link senegal.chercher.emploi.domain.Recruteur}.
 */
@RestController
@RequestMapping("/api/recruteurs")
public class RecruteurResource {

    private static final Logger LOG = LoggerFactory.getLogger(RecruteurResource.class);

    private static final String ENTITY_NAME = "recruteur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecruteurService recruteurService;

    private final RecruteurRepository recruteurRepository;

    public RecruteurResource(RecruteurService recruteurService, RecruteurRepository recruteurRepository) {
        this.recruteurService = recruteurService;
        this.recruteurRepository = recruteurRepository;
    }

    /**
     * {@code POST  /recruteurs} : Create a new recruteur.
     *
     * @param recruteurDTO the recruteurDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recruteurDTO, or with status {@code 400 (Bad Request)} if the recruteur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RecruteurDTO> createRecruteur(@Valid @RequestBody RecruteurDTO recruteurDTO) throws URISyntaxException {
        LOG.debug("REST request to save Recruteur : {}", recruteurDTO);
        if (recruteurDTO.getId() != null) {
            throw new BadRequestAlertException("A new recruteur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        recruteurDTO = recruteurService.save(recruteurDTO);
        return ResponseEntity.created(new URI("/api/recruteurs/" + recruteurDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, recruteurDTO.getId().toString()))
            .body(recruteurDTO);
    }

    /**
     * {@code PUT  /recruteurs/:id} : Updates an existing recruteur.
     *
     * @param id the id of the recruteurDTO to save.
     * @param recruteurDTO the recruteurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recruteurDTO,
     * or with status {@code 400 (Bad Request)} if the recruteurDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recruteurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RecruteurDTO> updateRecruteur(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RecruteurDTO recruteurDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Recruteur : {}, {}", id, recruteurDTO);
        if (recruteurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recruteurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recruteurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        recruteurDTO = recruteurService.update(recruteurDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recruteurDTO.getId().toString()))
            .body(recruteurDTO);
    }

    /**
     * {@code PATCH  /recruteurs/:id} : Partial updates given fields of an existing recruteur, field will ignore if it is null
     *
     * @param id the id of the recruteurDTO to save.
     * @param recruteurDTO the recruteurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recruteurDTO,
     * or with status {@code 400 (Bad Request)} if the recruteurDTO is not valid,
     * or with status {@code 404 (Not Found)} if the recruteurDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the recruteurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RecruteurDTO> partialUpdateRecruteur(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RecruteurDTO recruteurDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Recruteur partially : {}, {}", id, recruteurDTO);
        if (recruteurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recruteurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recruteurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RecruteurDTO> result = recruteurService.partialUpdate(recruteurDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recruteurDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /recruteurs} : get all the recruteurs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recruteurs in body.
     */
    @GetMapping("")
    public ResponseEntity<List<RecruteurDTO>> getAllRecruteurs(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Recruteurs");
        Page<RecruteurDTO> page = recruteurService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /recruteurs/:id} : get the "id" recruteur.
     *
     * @param id the id of the recruteurDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recruteurDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RecruteurDTO> getRecruteur(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Recruteur : {}", id);
        Optional<RecruteurDTO> recruteurDTO = recruteurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recruteurDTO);
    }

    /**
     * {@code DELETE  /recruteurs/:id} : delete the "id" recruteur.
     *
     * @param id the id of the recruteurDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecruteur(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Recruteur : {}", id);
        recruteurService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
