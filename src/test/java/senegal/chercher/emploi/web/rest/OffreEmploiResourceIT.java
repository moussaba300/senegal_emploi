package senegal.chercher.emploi.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static senegal.chercher.emploi.domain.OffreEmploiAsserts.*;
import static senegal.chercher.emploi.web.rest.TestUtil.createUpdateProxyForBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
import senegal.chercher.emploi.domain.OffreEmploi;
import senegal.chercher.emploi.repository.OffreEmploiRepository;
import senegal.chercher.emploi.service.dto.OffreEmploiDTO;
import senegal.chercher.emploi.service.mapper.OffreEmploiMapper;

/**
 * Integration tests for the {@link OffreEmploiResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OffreEmploiResourceIT {

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_REMUNERATION = 1D;
    private static final Double UPDATED_REMUNERATION = 2D;

    private static final Instant DEFAULT_DATE_PUBLICATION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_PUBLICATION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/offre-emplois";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OffreEmploiRepository offreEmploiRepository;

    @Autowired
    private OffreEmploiMapper offreEmploiMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOffreEmploiMockMvc;

    private OffreEmploi offreEmploi;

    private OffreEmploi insertedOffreEmploi;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OffreEmploi createEntity() {
        return new OffreEmploi()
            .titre(DEFAULT_TITRE)
            .description(DEFAULT_DESCRIPTION)
            .remuneration(DEFAULT_REMUNERATION)
            .datePublication(DEFAULT_DATE_PUBLICATION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OffreEmploi createUpdatedEntity() {
        return new OffreEmploi()
            .titre(UPDATED_TITRE)
            .description(UPDATED_DESCRIPTION)
            .remuneration(UPDATED_REMUNERATION)
            .datePublication(UPDATED_DATE_PUBLICATION);
    }

    @BeforeEach
    void initTest() {
        offreEmploi = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedOffreEmploi != null) {
            offreEmploiRepository.delete(insertedOffreEmploi);
            insertedOffreEmploi = null;
        }
    }

    @Test
    @Transactional
    void createOffreEmploi() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the OffreEmploi
        OffreEmploiDTO offreEmploiDTO = offreEmploiMapper.toDto(offreEmploi);
        var returnedOffreEmploiDTO = om.readValue(
            restOffreEmploiMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(offreEmploiDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            OffreEmploiDTO.class
        );

        // Validate the OffreEmploi in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedOffreEmploi = offreEmploiMapper.toEntity(returnedOffreEmploiDTO);
        assertOffreEmploiUpdatableFieldsEquals(returnedOffreEmploi, getPersistedOffreEmploi(returnedOffreEmploi));

        insertedOffreEmploi = returnedOffreEmploi;
    }

    @Test
    @Transactional
    void createOffreEmploiWithExistingId() throws Exception {
        // Create the OffreEmploi with an existing ID
        offreEmploi.setId(1L);
        OffreEmploiDTO offreEmploiDTO = offreEmploiMapper.toDto(offreEmploi);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOffreEmploiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(offreEmploiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OffreEmploi in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitreIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        offreEmploi.setTitre(null);

        // Create the OffreEmploi, which fails.
        OffreEmploiDTO offreEmploiDTO = offreEmploiMapper.toDto(offreEmploi);

        restOffreEmploiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(offreEmploiDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        offreEmploi.setDescription(null);

        // Create the OffreEmploi, which fails.
        OffreEmploiDTO offreEmploiDTO = offreEmploiMapper.toDto(offreEmploi);

        restOffreEmploiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(offreEmploiDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOffreEmplois() throws Exception {
        // Initialize the database
        insertedOffreEmploi = offreEmploiRepository.saveAndFlush(offreEmploi);

        // Get all the offreEmploiList
        restOffreEmploiMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offreEmploi.getId().intValue())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].remuneration").value(hasItem(DEFAULT_REMUNERATION)))
            .andExpect(jsonPath("$.[*].datePublication").value(hasItem(DEFAULT_DATE_PUBLICATION.toString())));
    }

    @Test
    @Transactional
    void getOffreEmploi() throws Exception {
        // Initialize the database
        insertedOffreEmploi = offreEmploiRepository.saveAndFlush(offreEmploi);

        // Get the offreEmploi
        restOffreEmploiMockMvc
            .perform(get(ENTITY_API_URL_ID, offreEmploi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(offreEmploi.getId().intValue()))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.remuneration").value(DEFAULT_REMUNERATION))
            .andExpect(jsonPath("$.datePublication").value(DEFAULT_DATE_PUBLICATION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingOffreEmploi() throws Exception {
        // Get the offreEmploi
        restOffreEmploiMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOffreEmploi() throws Exception {
        // Initialize the database
        insertedOffreEmploi = offreEmploiRepository.saveAndFlush(offreEmploi);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the offreEmploi
        OffreEmploi updatedOffreEmploi = offreEmploiRepository.findById(offreEmploi.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOffreEmploi are not directly saved in db
        em.detach(updatedOffreEmploi);
        updatedOffreEmploi
            .titre(UPDATED_TITRE)
            .description(UPDATED_DESCRIPTION)
            .remuneration(UPDATED_REMUNERATION)
            .datePublication(UPDATED_DATE_PUBLICATION);
        OffreEmploiDTO offreEmploiDTO = offreEmploiMapper.toDto(updatedOffreEmploi);

        restOffreEmploiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, offreEmploiDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(offreEmploiDTO))
            )
            .andExpect(status().isOk());

        // Validate the OffreEmploi in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOffreEmploiToMatchAllProperties(updatedOffreEmploi);
    }

    @Test
    @Transactional
    void putNonExistingOffreEmploi() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        offreEmploi.setId(longCount.incrementAndGet());

        // Create the OffreEmploi
        OffreEmploiDTO offreEmploiDTO = offreEmploiMapper.toDto(offreEmploi);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOffreEmploiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, offreEmploiDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(offreEmploiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OffreEmploi in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOffreEmploi() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        offreEmploi.setId(longCount.incrementAndGet());

        // Create the OffreEmploi
        OffreEmploiDTO offreEmploiDTO = offreEmploiMapper.toDto(offreEmploi);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOffreEmploiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(offreEmploiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OffreEmploi in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOffreEmploi() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        offreEmploi.setId(longCount.incrementAndGet());

        // Create the OffreEmploi
        OffreEmploiDTO offreEmploiDTO = offreEmploiMapper.toDto(offreEmploi);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOffreEmploiMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(offreEmploiDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OffreEmploi in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOffreEmploiWithPatch() throws Exception {
        // Initialize the database
        insertedOffreEmploi = offreEmploiRepository.saveAndFlush(offreEmploi);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the offreEmploi using partial update
        OffreEmploi partialUpdatedOffreEmploi = new OffreEmploi();
        partialUpdatedOffreEmploi.setId(offreEmploi.getId());

        partialUpdatedOffreEmploi.description(UPDATED_DESCRIPTION).datePublication(UPDATED_DATE_PUBLICATION);

        restOffreEmploiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOffreEmploi.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOffreEmploi))
            )
            .andExpect(status().isOk());

        // Validate the OffreEmploi in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOffreEmploiUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOffreEmploi, offreEmploi),
            getPersistedOffreEmploi(offreEmploi)
        );
    }

    @Test
    @Transactional
    void fullUpdateOffreEmploiWithPatch() throws Exception {
        // Initialize the database
        insertedOffreEmploi = offreEmploiRepository.saveAndFlush(offreEmploi);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the offreEmploi using partial update
        OffreEmploi partialUpdatedOffreEmploi = new OffreEmploi();
        partialUpdatedOffreEmploi.setId(offreEmploi.getId());

        partialUpdatedOffreEmploi
            .titre(UPDATED_TITRE)
            .description(UPDATED_DESCRIPTION)
            .remuneration(UPDATED_REMUNERATION)
            .datePublication(UPDATED_DATE_PUBLICATION);

        restOffreEmploiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOffreEmploi.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOffreEmploi))
            )
            .andExpect(status().isOk());

        // Validate the OffreEmploi in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOffreEmploiUpdatableFieldsEquals(partialUpdatedOffreEmploi, getPersistedOffreEmploi(partialUpdatedOffreEmploi));
    }

    @Test
    @Transactional
    void patchNonExistingOffreEmploi() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        offreEmploi.setId(longCount.incrementAndGet());

        // Create the OffreEmploi
        OffreEmploiDTO offreEmploiDTO = offreEmploiMapper.toDto(offreEmploi);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOffreEmploiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, offreEmploiDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(offreEmploiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OffreEmploi in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOffreEmploi() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        offreEmploi.setId(longCount.incrementAndGet());

        // Create the OffreEmploi
        OffreEmploiDTO offreEmploiDTO = offreEmploiMapper.toDto(offreEmploi);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOffreEmploiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(offreEmploiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OffreEmploi in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOffreEmploi() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        offreEmploi.setId(longCount.incrementAndGet());

        // Create the OffreEmploi
        OffreEmploiDTO offreEmploiDTO = offreEmploiMapper.toDto(offreEmploi);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOffreEmploiMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(offreEmploiDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OffreEmploi in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOffreEmploi() throws Exception {
        // Initialize the database
        insertedOffreEmploi = offreEmploiRepository.saveAndFlush(offreEmploi);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the offreEmploi
        restOffreEmploiMockMvc
            .perform(delete(ENTITY_API_URL_ID, offreEmploi.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return offreEmploiRepository.count();
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

    protected OffreEmploi getPersistedOffreEmploi(OffreEmploi offreEmploi) {
        return offreEmploiRepository.findById(offreEmploi.getId()).orElseThrow();
    }

    protected void assertPersistedOffreEmploiToMatchAllProperties(OffreEmploi expectedOffreEmploi) {
        assertOffreEmploiAllPropertiesEquals(expectedOffreEmploi, getPersistedOffreEmploi(expectedOffreEmploi));
    }

    protected void assertPersistedOffreEmploiToMatchUpdatableProperties(OffreEmploi expectedOffreEmploi) {
        assertOffreEmploiAllUpdatablePropertiesEquals(expectedOffreEmploi, getPersistedOffreEmploi(expectedOffreEmploi));
    }
}
