package senegal.chercher.emploi.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static senegal.chercher.emploi.domain.LocalisationAsserts.*;
import static senegal.chercher.emploi.web.rest.TestUtil.createUpdateProxyForBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import senegal.chercher.emploi.IntegrationTest;
import senegal.chercher.emploi.domain.Localisation;
import senegal.chercher.emploi.repository.LocalisationRepository;
import senegal.chercher.emploi.service.dto.LocalisationDTO;
import senegal.chercher.emploi.service.mapper.LocalisationMapper;

/**
 * Integration tests for the {@link LocalisationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LocalisationResourceIT {

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTEMENT = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTEMENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/localisations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LocalisationRepository localisationRepository;

    @Autowired
    private LocalisationMapper localisationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocalisationMockMvc;

    private Localisation localisation;

    private Localisation insertedLocalisation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Localisation createEntity() {
        return new Localisation().region(DEFAULT_REGION).departement(DEFAULT_DEPARTEMENT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Localisation createUpdatedEntity() {
        return new Localisation().region(UPDATED_REGION).departement(UPDATED_DEPARTEMENT);
    }

    @BeforeEach
    void initTest() {
        localisation = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedLocalisation != null) {
            localisationRepository.delete(insertedLocalisation);
            insertedLocalisation = null;
        }
    }

    @Test
    @Transactional
    void createLocalisation() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Localisation
        LocalisationDTO localisationDTO = localisationMapper.toDto(localisation);
        var returnedLocalisationDTO = om.readValue(
            restLocalisationMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(localisationDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            LocalisationDTO.class
        );

        // Validate the Localisation in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedLocalisation = localisationMapper.toEntity(returnedLocalisationDTO);
        assertLocalisationUpdatableFieldsEquals(returnedLocalisation, getPersistedLocalisation(returnedLocalisation));

        insertedLocalisation = returnedLocalisation;
    }

    @Test
    @Transactional
    void createLocalisationWithExistingId() throws Exception {
        // Create the Localisation with an existing ID
        localisation.setId(1L);
        LocalisationDTO localisationDTO = localisationMapper.toDto(localisation);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocalisationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(localisationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Localisation in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRegionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        localisation.setRegion(null);

        // Create the Localisation, which fails.
        LocalisationDTO localisationDTO = localisationMapper.toDto(localisation);

        restLocalisationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(localisationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDepartementIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        localisation.setDepartement(null);

        // Create the Localisation, which fails.
        LocalisationDTO localisationDTO = localisationMapper.toDto(localisation);

        restLocalisationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(localisationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLocalisations() throws Exception {
        // Initialize the database
        insertedLocalisation = localisationRepository.saveAndFlush(localisation);

        // Get all the localisationList
        restLocalisationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localisation.getId().intValue())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION)))
            .andExpect(jsonPath("$.[*].departement").value(hasItem(DEFAULT_DEPARTEMENT)));
    }

    @Test
    @Transactional
    void getLocalisation() throws Exception {
        // Initialize the database
        insertedLocalisation = localisationRepository.saveAndFlush(localisation);

        // Get the localisation
        restLocalisationMockMvc
            .perform(get(ENTITY_API_URL_ID, localisation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(localisation.getId().intValue()))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION))
            .andExpect(jsonPath("$.departement").value(DEFAULT_DEPARTEMENT));
    }

    @Test
    @Transactional
    void getNonExistingLocalisation() throws Exception {
        // Get the localisation
        restLocalisationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLocalisation() throws Exception {
        // Initialize the database
        insertedLocalisation = localisationRepository.saveAndFlush(localisation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the localisation
        Localisation updatedLocalisation = localisationRepository.findById(localisation.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLocalisation are not directly saved in db
        em.detach(updatedLocalisation);
        updatedLocalisation.region(UPDATED_REGION).departement(UPDATED_DEPARTEMENT);
        LocalisationDTO localisationDTO = localisationMapper.toDto(updatedLocalisation);

        restLocalisationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, localisationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(localisationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Localisation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLocalisationToMatchAllProperties(updatedLocalisation);
    }

    @Test
    @Transactional
    void putNonExistingLocalisation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        localisation.setId(longCount.incrementAndGet());

        // Create the Localisation
        LocalisationDTO localisationDTO = localisationMapper.toDto(localisation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocalisationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, localisationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(localisationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Localisation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLocalisation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        localisation.setId(longCount.incrementAndGet());

        // Create the Localisation
        LocalisationDTO localisationDTO = localisationMapper.toDto(localisation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocalisationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(localisationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Localisation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLocalisation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        localisation.setId(longCount.incrementAndGet());

        // Create the Localisation
        LocalisationDTO localisationDTO = localisationMapper.toDto(localisation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocalisationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(localisationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Localisation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLocalisationWithPatch() throws Exception {
        // Initialize the database
        insertedLocalisation = localisationRepository.saveAndFlush(localisation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the localisation using partial update
        Localisation partialUpdatedLocalisation = new Localisation();
        partialUpdatedLocalisation.setId(localisation.getId());

        restLocalisationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocalisation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLocalisation))
            )
            .andExpect(status().isOk());

        // Validate the Localisation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLocalisationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedLocalisation, localisation),
            getPersistedLocalisation(localisation)
        );
    }

    @Test
    @Transactional
    void fullUpdateLocalisationWithPatch() throws Exception {
        // Initialize the database
        insertedLocalisation = localisationRepository.saveAndFlush(localisation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the localisation using partial update
        Localisation partialUpdatedLocalisation = new Localisation();
        partialUpdatedLocalisation.setId(localisation.getId());

        partialUpdatedLocalisation.region(UPDATED_REGION).departement(UPDATED_DEPARTEMENT);

        restLocalisationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocalisation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLocalisation))
            )
            .andExpect(status().isOk());

        // Validate the Localisation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLocalisationUpdatableFieldsEquals(partialUpdatedLocalisation, getPersistedLocalisation(partialUpdatedLocalisation));
    }

    @Test
    @Transactional
    void patchNonExistingLocalisation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        localisation.setId(longCount.incrementAndGet());

        // Create the Localisation
        LocalisationDTO localisationDTO = localisationMapper.toDto(localisation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocalisationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, localisationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(localisationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Localisation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLocalisation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        localisation.setId(longCount.incrementAndGet());

        // Create the Localisation
        LocalisationDTO localisationDTO = localisationMapper.toDto(localisation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocalisationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(localisationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Localisation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLocalisation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        localisation.setId(longCount.incrementAndGet());

        // Create the Localisation
        LocalisationDTO localisationDTO = localisationMapper.toDto(localisation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocalisationMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(localisationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Localisation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLocalisation() throws Exception {
        // Initialize the database
        insertedLocalisation = localisationRepository.saveAndFlush(localisation);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the localisation
        restLocalisationMockMvc
            .perform(delete(ENTITY_API_URL_ID, localisation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return localisationRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Localisation getPersistedLocalisation(Localisation localisation) {
        return localisationRepository.findById(localisation.getId()).orElseThrow();
    }

    protected void assertPersistedLocalisationToMatchAllProperties(Localisation expectedLocalisation) {
        assertLocalisationAllPropertiesEquals(expectedLocalisation, getPersistedLocalisation(expectedLocalisation));
    }

    protected void assertPersistedLocalisationToMatchUpdatableProperties(Localisation expectedLocalisation) {
        assertLocalisationAllUpdatablePropertiesEquals(expectedLocalisation, getPersistedLocalisation(expectedLocalisation));
    }
}
