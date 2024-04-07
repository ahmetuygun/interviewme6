package com.interview.me.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.interview.me.IntegrationTest;
import com.interview.me.domain.MembershipLevel;
import com.interview.me.repository.MembershipLevelRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MembershipLevelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MembershipLevelResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SESSION_AMOUNT = 1;
    private static final Integer UPDATED_SESSION_AMOUNT = 2;

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final String ENTITY_API_URL = "/api/membership-levels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MembershipLevelRepository membershipLevelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMembershipLevelMockMvc;

    private MembershipLevel membershipLevel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MembershipLevel createEntity(EntityManager em) {
        MembershipLevel membershipLevel = new MembershipLevel()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .sessionAmount(DEFAULT_SESSION_AMOUNT)
            .price(DEFAULT_PRICE);
        return membershipLevel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MembershipLevel createUpdatedEntity(EntityManager em) {
        MembershipLevel membershipLevel = new MembershipLevel()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .sessionAmount(UPDATED_SESSION_AMOUNT)
            .price(UPDATED_PRICE);
        return membershipLevel;
    }

    @BeforeEach
    public void initTest() {
        membershipLevel = createEntity(em);
    }

    @Test
    @Transactional
    void createMembershipLevel() throws Exception {
        int databaseSizeBeforeCreate = membershipLevelRepository.findAll().size();
        // Create the MembershipLevel
        restMembershipLevelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(membershipLevel))
            )
            .andExpect(status().isCreated());

        // Validate the MembershipLevel in the database
        List<MembershipLevel> membershipLevelList = membershipLevelRepository.findAll();
        assertThat(membershipLevelList).hasSize(databaseSizeBeforeCreate + 1);
        MembershipLevel testMembershipLevel = membershipLevelList.get(membershipLevelList.size() - 1);
        assertThat(testMembershipLevel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMembershipLevel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMembershipLevel.getSessionAmount()).isEqualTo(DEFAULT_SESSION_AMOUNT);
        assertThat(testMembershipLevel.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void createMembershipLevelWithExistingId() throws Exception {
        // Create the MembershipLevel with an existing ID
        membershipLevel.setId(1L);

        int databaseSizeBeforeCreate = membershipLevelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMembershipLevelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(membershipLevel))
            )
            .andExpect(status().isBadRequest());

        // Validate the MembershipLevel in the database
        List<MembershipLevel> membershipLevelList = membershipLevelRepository.findAll();
        assertThat(membershipLevelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMembershipLevels() throws Exception {
        // Initialize the database
        membershipLevelRepository.saveAndFlush(membershipLevel);

        // Get all the membershipLevelList
        restMembershipLevelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(membershipLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].sessionAmount").value(hasItem(DEFAULT_SESSION_AMOUNT)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    void getMembershipLevel() throws Exception {
        // Initialize the database
        membershipLevelRepository.saveAndFlush(membershipLevel);

        // Get the membershipLevel
        restMembershipLevelMockMvc
            .perform(get(ENTITY_API_URL_ID, membershipLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(membershipLevel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.sessionAmount").value(DEFAULT_SESSION_AMOUNT))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingMembershipLevel() throws Exception {
        // Get the membershipLevel
        restMembershipLevelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMembershipLevel() throws Exception {
        // Initialize the database
        membershipLevelRepository.saveAndFlush(membershipLevel);

        int databaseSizeBeforeUpdate = membershipLevelRepository.findAll().size();

        // Update the membershipLevel
        MembershipLevel updatedMembershipLevel = membershipLevelRepository.findById(membershipLevel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMembershipLevel are not directly saved in db
        em.detach(updatedMembershipLevel);
        updatedMembershipLevel
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .sessionAmount(UPDATED_SESSION_AMOUNT)
            .price(UPDATED_PRICE);

        restMembershipLevelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMembershipLevel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMembershipLevel))
            )
            .andExpect(status().isOk());

        // Validate the MembershipLevel in the database
        List<MembershipLevel> membershipLevelList = membershipLevelRepository.findAll();
        assertThat(membershipLevelList).hasSize(databaseSizeBeforeUpdate);
        MembershipLevel testMembershipLevel = membershipLevelList.get(membershipLevelList.size() - 1);
        assertThat(testMembershipLevel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMembershipLevel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMembershipLevel.getSessionAmount()).isEqualTo(UPDATED_SESSION_AMOUNT);
        assertThat(testMembershipLevel.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    void putNonExistingMembershipLevel() throws Exception {
        int databaseSizeBeforeUpdate = membershipLevelRepository.findAll().size();
        membershipLevel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMembershipLevelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, membershipLevel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(membershipLevel))
            )
            .andExpect(status().isBadRequest());

        // Validate the MembershipLevel in the database
        List<MembershipLevel> membershipLevelList = membershipLevelRepository.findAll();
        assertThat(membershipLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMembershipLevel() throws Exception {
        int databaseSizeBeforeUpdate = membershipLevelRepository.findAll().size();
        membershipLevel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMembershipLevelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(membershipLevel))
            )
            .andExpect(status().isBadRequest());

        // Validate the MembershipLevel in the database
        List<MembershipLevel> membershipLevelList = membershipLevelRepository.findAll();
        assertThat(membershipLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMembershipLevel() throws Exception {
        int databaseSizeBeforeUpdate = membershipLevelRepository.findAll().size();
        membershipLevel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMembershipLevelMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(membershipLevel))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MembershipLevel in the database
        List<MembershipLevel> membershipLevelList = membershipLevelRepository.findAll();
        assertThat(membershipLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMembershipLevelWithPatch() throws Exception {
        // Initialize the database
        membershipLevelRepository.saveAndFlush(membershipLevel);

        int databaseSizeBeforeUpdate = membershipLevelRepository.findAll().size();

        // Update the membershipLevel using partial update
        MembershipLevel partialUpdatedMembershipLevel = new MembershipLevel();
        partialUpdatedMembershipLevel.setId(membershipLevel.getId());

        partialUpdatedMembershipLevel.price(UPDATED_PRICE);

        restMembershipLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMembershipLevel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMembershipLevel))
            )
            .andExpect(status().isOk());

        // Validate the MembershipLevel in the database
        List<MembershipLevel> membershipLevelList = membershipLevelRepository.findAll();
        assertThat(membershipLevelList).hasSize(databaseSizeBeforeUpdate);
        MembershipLevel testMembershipLevel = membershipLevelList.get(membershipLevelList.size() - 1);
        assertThat(testMembershipLevel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMembershipLevel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMembershipLevel.getSessionAmount()).isEqualTo(DEFAULT_SESSION_AMOUNT);
        assertThat(testMembershipLevel.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    void fullUpdateMembershipLevelWithPatch() throws Exception {
        // Initialize the database
        membershipLevelRepository.saveAndFlush(membershipLevel);

        int databaseSizeBeforeUpdate = membershipLevelRepository.findAll().size();

        // Update the membershipLevel using partial update
        MembershipLevel partialUpdatedMembershipLevel = new MembershipLevel();
        partialUpdatedMembershipLevel.setId(membershipLevel.getId());

        partialUpdatedMembershipLevel
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .sessionAmount(UPDATED_SESSION_AMOUNT)
            .price(UPDATED_PRICE);

        restMembershipLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMembershipLevel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMembershipLevel))
            )
            .andExpect(status().isOk());

        // Validate the MembershipLevel in the database
        List<MembershipLevel> membershipLevelList = membershipLevelRepository.findAll();
        assertThat(membershipLevelList).hasSize(databaseSizeBeforeUpdate);
        MembershipLevel testMembershipLevel = membershipLevelList.get(membershipLevelList.size() - 1);
        assertThat(testMembershipLevel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMembershipLevel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMembershipLevel.getSessionAmount()).isEqualTo(UPDATED_SESSION_AMOUNT);
        assertThat(testMembershipLevel.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    void patchNonExistingMembershipLevel() throws Exception {
        int databaseSizeBeforeUpdate = membershipLevelRepository.findAll().size();
        membershipLevel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMembershipLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, membershipLevel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(membershipLevel))
            )
            .andExpect(status().isBadRequest());

        // Validate the MembershipLevel in the database
        List<MembershipLevel> membershipLevelList = membershipLevelRepository.findAll();
        assertThat(membershipLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMembershipLevel() throws Exception {
        int databaseSizeBeforeUpdate = membershipLevelRepository.findAll().size();
        membershipLevel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMembershipLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(membershipLevel))
            )
            .andExpect(status().isBadRequest());

        // Validate the MembershipLevel in the database
        List<MembershipLevel> membershipLevelList = membershipLevelRepository.findAll();
        assertThat(membershipLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMembershipLevel() throws Exception {
        int databaseSizeBeforeUpdate = membershipLevelRepository.findAll().size();
        membershipLevel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMembershipLevelMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(membershipLevel))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MembershipLevel in the database
        List<MembershipLevel> membershipLevelList = membershipLevelRepository.findAll();
        assertThat(membershipLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMembershipLevel() throws Exception {
        // Initialize the database
        membershipLevelRepository.saveAndFlush(membershipLevel);

        int databaseSizeBeforeDelete = membershipLevelRepository.findAll().size();

        // Delete the membershipLevel
        restMembershipLevelMockMvc
            .perform(delete(ENTITY_API_URL_ID, membershipLevel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MembershipLevel> membershipLevelList = membershipLevelRepository.findAll();
        assertThat(membershipLevelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
