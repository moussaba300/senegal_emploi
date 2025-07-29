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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import senegal.chercher.emploi.repository.UtilisateurRepository;
import senegal.chercher.emploi.service.UtilisateurService;
import senegal.chercher.emploi.service.dto.UtilisateurDTO;
import senegal.chercher.emploi.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link senegal.chercher.emploi.domain.Utilisateur}.
 */
@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurResource {

    private static final Logger LOG = LoggerFactory.getLogger(UtilisateurResource.class);

    private static final String ENTITY_NAME = "utilisateur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UtilisateurService utilisateurService;

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurResource(UtilisateurService utilisateurService, UtilisateurRepository utilisateurRepository) {
        this.utilisateurService = utilisateurService;
        this.utilisateurRepository = utilisateurRepository;
    }

    /**
     * {@code POST  /utilisateurs} : Create a new utilisateur.
     *
     * @param utilisateurDTO the utilisateurDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new utilisateurDTO, or with status {@code 400 (Bad Request)} if the utilisateur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<UtilisateurDTO> createUtilisateur(@Valid @RequestBody UtilisateurDTO utilisateurDTO) throws URISyntaxException {
        LOG.debug("REST request to save Utilisateur : {}", utilisateurDTO);
        if (utilisateurDTO.getId() != null) {
            throw new BadRequestAlertException("A new utilisateur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        utilisateurDTO = utilisateurService.save(utilisateurDTO);
        return ResponseEntity.created(new URI("/api/utilisateurs/" + utilisateurDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, utilisateurDTO.getId().toString()))
            .body(utilisateurDTO);
    }

    /**
     * {@code PUT  /utilisateurs/:id} : Updates an existing utilisateur.
     *
     * @param id the id of the utilisateurDTO to save.
     * @param utilisateurDTO the utilisateurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated utilisateurDTO,
     * or with status {@code 400 (Bad Request)} if the utilisateurDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the utilisateurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UtilisateurDTO> updateUtilisateur(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UtilisateurDTO utilisateurDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Utilisateur : {}, {}", id, utilisateurDTO);
        if (utilisateurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, utilisateurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!utilisateurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        utilisateurDTO = utilisateurService.update(utilisateurDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, utilisateurDTO.getId().toString()))
            .body(utilisateurDTO);
    }

    /**
     * {@code PATCH  /utilisateurs/:id} : Partial updates given fields of an existing utilisateur, field will ignore if it is null
     *
     * @param id the id of the utilisateurDTO to save.
     * @param utilisateurDTO the utilisateurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated utilisateurDTO,
     * or with status {@code 400 (Bad Request)} if the utilisateurDTO is not valid,
     * or with status {@code 404 (Not Found)} if the utilisateurDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the utilisateurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UtilisateurDTO> partialUpdateUtilisateur(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UtilisateurDTO utilisateurDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Utilisateur partially : {}, {}", id, utilisateurDTO);
        if (utilisateurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, utilisateurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!utilisateurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UtilisateurDTO> result = utilisateurService.partialUpdate(utilisateurDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, utilisateurDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /utilisateurs} : get all the utilisateurs.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of utilisateurs in body.
     */
    @GetMapping("")
    public ResponseEntity<List<UtilisateurDTO>> getAllUtilisateurs(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "filter", required = false) String filter
    ) {
        if ("candidat-is-null".equals(filter)) {
            LOG.debug("REST request to get all Utilisateurs where candidat is null");
            return new ResponseEntity<>(utilisateurService.findAllWhereCandidatIsNull(), HttpStatus.OK);
        }

        if ("recruteur-is-null".equals(filter)) {
            LOG.debug("REST request to get all Utilisateurs where recruteur is null");
            return new ResponseEntity<>(utilisateurService.findAllWhereRecruteurIsNull(), HttpStatus.OK);
        }
        LOG.debug("REST request to get a page of Utilisateurs");
        Page<UtilisateurDTO> page = utilisateurService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /utilisateurs/:id} : get the "id" utilisateur.
     *
     * @param id the id of the utilisateurDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the utilisateurDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurDTO> getUtilisateur(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Utilisateur : {}", id);
        Optional<UtilisateurDTO> utilisateurDTO = utilisateurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(utilisateurDTO);
    }

    /**
     * {@code DELETE  /utilisateurs/:id} : delete the "id" utilisateur.
     *
     * @param id the id of the utilisateurDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Utilisateur : {}", id);
        utilisateurService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
