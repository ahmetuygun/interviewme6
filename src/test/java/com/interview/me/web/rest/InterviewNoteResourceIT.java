package com.interview.me.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.interview.me.IntegrationTest;
import com.interview.me.domain.InterviewNote;
import com.interview.me.repository.InterviewNoteRepository;
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
 * Integration tests for the {@link InterviewNoteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InterviewNoteResourceIT {

    private static final String DEFAULT_NOTE_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_NOTE_TEXT = "BBBBBBBBBB";

    private static final Integer DEFAULT_RATING = 1;
    private static final Integer UPDATED_RATING = 2;
    private static final Integer SMALLER_RATING = 1 - 1;

    private static final String DEFAULT_ACTION_ITEMS = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_ITEMS = "BBBBBBBBBB";

    private static final String DEFAULT_FEEDBACK = "AAAAAAAAAA";
    private static final String UPDATED_FEEDBACK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/interview-notes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InterviewNoteRepository interviewNoteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInterviewNoteMockMvc;

    private InterviewNote interviewNote;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InterviewNote createEntity(EntityManager em) {
        InterviewNote interviewNote = new InterviewNote()
            .noteText(DEFAULT_NOTE_TEXT)
            .rating(DEFAULT_RATING)
            .actionItems(DEFAULT_ACTION_ITEMS)
            .feedback(DEFAULT_FEEDBACK);
        return interviewNote;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InterviewNote createUpdatedEntity(EntityManager em) {
        InterviewNote interviewNote = new InterviewNote()
            .noteText(UPDATED_NOTE_TEXT)
            .rating(UPDATED_RATING)
            .actionItems(UPDATED_ACTION_ITEMS)
            .feedback(UPDATED_FEEDBACK);
        return interviewNote;
    }

    @BeforeEach
    public void initTest() {
        interviewNote = createEntity(em);
    }

    @Test
    @Transactional
    void createInterviewNote() throws Exception {
        int databaseSizeBeforeCreate = interviewNoteRepository.findAll().size();
        // Create the InterviewNote
        restInterviewNoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(interviewNote)))
            .andExpect(status().isCreated());

        // Validate the InterviewNote in the database
        List<InterviewNote> interviewNoteList = interviewNoteRepository.findAll();
        assertThat(interviewNoteList).hasSize(databaseSizeBeforeCreate + 1);
        InterviewNote testInterviewNote = interviewNoteList.get(interviewNoteList.size() - 1);
        assertThat(testInterviewNote.getNoteText()).isEqualTo(DEFAULT_NOTE_TEXT);
        assertThat(testInterviewNote.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testInterviewNote.getActionItems()).isEqualTo(DEFAULT_ACTION_ITEMS);
        assertThat(testInterviewNote.getFeedback()).isEqualTo(DEFAULT_FEEDBACK);
    }

    @Test
    @Transactional
    void createInterviewNoteWithExistingId() throws Exception {
        // Create the InterviewNote with an existing ID
        interviewNote.setId(1L);

        int databaseSizeBeforeCreate = interviewNoteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInterviewNoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(interviewNote)))
            .andExpect(status().isBadRequest());

        // Validate the InterviewNote in the database
        List<InterviewNote> interviewNoteList = interviewNoteRepository.findAll();
        assertThat(interviewNoteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInterviewNotes() throws Exception {
        // Initialize the database
        interviewNoteRepository.saveAndFlush(interviewNote);

        // Get all the interviewNoteList
        restInterviewNoteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(interviewNote.getId().intValue())))
            .andExpect(jsonPath("$.[*].noteText").value(hasItem(DEFAULT_NOTE_TEXT)))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].actionItems").value(hasItem(DEFAULT_ACTION_ITEMS)))
            .andExpect(jsonPath("$.[*].feedback").value(hasItem(DEFAULT_FEEDBACK)));
    }

    @Test
    @Transactional
    void getInterviewNote() throws Exception {
        // Initialize the database
        interviewNoteRepository.saveAndFlush(interviewNote);

        // Get the interviewNote
        restInterviewNoteMockMvc
            .perform(get(ENTITY_API_URL_ID, interviewNote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(interviewNote.getId().intValue()))
            .andExpect(jsonPath("$.noteText").value(DEFAULT_NOTE_TEXT))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING))
            .andExpect(jsonPath("$.actionItems").value(DEFAULT_ACTION_ITEMS))
            .andExpect(jsonPath("$.feedback").value(DEFAULT_FEEDBACK));
    }

    @Test
    @Transactional
    void getInterviewNotesByIdFiltering() throws Exception {
        // Initialize the database
        interviewNoteRepository.saveAndFlush(interviewNote);

        Long id = interviewNote.getId();

        defaultInterviewNoteShouldBeFound("id.equals=" + id);
        defaultInterviewNoteShouldNotBeFound("id.notEquals=" + id);

        defaultInterviewNoteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInterviewNoteShouldNotBeFound("id.greaterThan=" + id);

        defaultInterviewNoteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInterviewNoteShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllInterviewNotesByNoteTextIsEqualToSomething() throws Exception {
        // Initialize the database
        interviewNoteRepository.saveAndFlush(interviewNote);

        // Get all the interviewNoteList where noteText equals to DEFAULT_NOTE_TEXT
        defaultInterviewNoteShouldBeFound("noteText.equals=" + DEFAULT_NOTE_TEXT);

        // Get all the interviewNoteList where noteText equals to UPDATED_NOTE_TEXT
        defaultInterviewNoteShouldNotBeFound("noteText.equals=" + UPDATED_NOTE_TEXT);
    }

    @Test
    @Transactional
    void getAllInterviewNotesByNoteTextIsInShouldWork() throws Exception {
        // Initialize the database
        interviewNoteRepository.saveAndFlush(interviewNote);

        // Get all the interviewNoteList where noteText in DEFAULT_NOTE_TEXT or UPDATED_NOTE_TEXT
        defaultInterviewNoteShouldBeFound("noteText.in=" + DEFAULT_NOTE_TEXT + "," + UPDATED_NOTE_TEXT);

        // Get all the interviewNoteList where noteText equals to UPDATED_NOTE_TEXT
        defaultInterviewNoteShouldNotBeFound("noteText.in=" + UPDATED_NOTE_TEXT);
    }

    @Test
    @Transactional
    void getAllInterviewNotesByNoteTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        interviewNoteRepository.saveAndFlush(interviewNote);

        // Get all the interviewNoteList where noteText is not null
        defaultInterviewNoteShouldBeFound("noteText.specified=true");

        // Get all the interviewNoteList where noteText is null
        defaultInterviewNoteShouldNotBeFound("noteText.specified=false");
    }

    @Test
    @Transactional
    void getAllInterviewNotesByNoteTextContainsSomething() throws Exception {
        // Initialize the database
        interviewNoteRepository.saveAndFlush(interviewNote);

        // Get all the interviewNoteList where noteText contains DEFAULT_NOTE_TEXT
        defaultInterviewNoteShouldBeFound("noteText.contains=" + DEFAULT_NOTE_TEXT);

        // Get all the interviewNoteList where noteText contains UPDATED_NOTE_TEXT
        defaultInterviewNoteShouldNotBeFound("noteText.contains=" + UPDATED_NOTE_TEXT);
    }

    @Test
    @Transactional
    void getAllInterviewNotesByNoteTextNotContainsSomething() throws Exception {
        // Initialize the database
        interviewNoteRepository.saveAndFlush(interviewNote);

        // Get all the interviewNoteList where noteText does not contain DEFAULT_NOTE_TEXT
        defaultInterviewNoteShouldNotBeFound("noteText.doesNotContain=" + DEFAULT_NOTE_TEXT);

        // Get all the interviewNoteList where noteText does not contain UPDATED_NOTE_TEXT
        defaultInterviewNoteShouldBeFound("noteText.doesNotContain=" + UPDATED_NOTE_TEXT);
    }

    @Test
    @Transactional
    void getAllInterviewNotesByRatingIsEqualToSomething() throws Exception {
        // Initialize the database
        interviewNoteRepository.saveAndFlush(interviewNote);

        // Get all the interviewNoteList where rating equals to DEFAULT_RATING
        defaultInterviewNoteShouldBeFound("rating.equals=" + DEFAULT_RATING);

        // Get all the interviewNoteList where rating equals to UPDATED_RATING
        defaultInterviewNoteShouldNotBeFound("rating.equals=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    void getAllInterviewNotesByRatingIsInShouldWork() throws Exception {
        // Initialize the database
        interviewNoteRepository.saveAndFlush(interviewNote);

        // Get all the interviewNoteList where rating in DEFAULT_RATING or UPDATED_RATING
        defaultInterviewNoteShouldBeFound("rating.in=" + DEFAULT_RATING + "," + UPDATED_RATING);

        // Get all the interviewNoteList where rating equals to UPDATED_RATING
        defaultInterviewNoteShouldNotBeFound("rating.in=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    void getAllInterviewNotesByRatingIsNullOrNotNull() throws Exception {
        // Initialize the database
        interviewNoteRepository.saveAndFlush(interviewNote);

        // Get all the interviewNoteList where rating is not null
        defaultInterviewNoteShouldBeFound("rating.specified=true");

        // Get all the interviewNoteList where rating is null
        defaultInterviewNoteShouldNotBeFound("rating.specified=false");
    }

    @Test
    @Transactional
    void getAllInterviewNotesByRatingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        interviewNoteRepository.saveAndFlush(interviewNote);

        // Get all the interviewNoteList where rating is greater than or equal to DEFAULT_RATING
        defaultInterviewNoteShouldBeFound("rating.greaterThanOrEqual=" + DEFAULT_RATING);

        // Get all the interviewNoteList where rating is greater than or equal to UPDATED_RATING
        defaultInterviewNoteShouldNotBeFound("rating.greaterThanOrEqual=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    void getAllInterviewNotesByRatingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        interviewNoteRepository.saveAndFlush(interviewNote);

        // Get all the interviewNoteList where rating is less than or equal to DEFAULT_RATING
        defaultInterviewNoteShouldBeFound("rating.lessThanOrEqual=" + DEFAULT_RATING);

        // Get all the interviewNoteList where rating is less than or equal to SMALLER_RATING
        defaultInterviewNoteShouldNotBeFound("rating.lessThanOrEqual=" + SMALLER_RATING);
    }

    @Test
    @Transactional
    void getAllInterviewNotesByRatingIsLessThanSomething() throws Exception {
        // Initialize the database
        interviewNoteRepository.saveAndFlush(interviewNote);

        // Get all the interviewNoteList where rating is less than DEFAULT_RATING
        defaultInterviewNoteShouldNotBeFound("rating.lessThan=" + DEFAULT_RATING);

        // Get all the interviewNoteList where rating is less than UPDATED_RATING
        defaultInterviewNoteShouldBeFound("rating.lessThan=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    void getAllInterviewNotesByRatingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        interviewNoteRepository.saveAndFlush(interviewNote);

        // Get all the interviewNoteList where rating is greater than DEFAULT_RATING
        defaultInterviewNoteShouldNotBeFound("rating.greaterThan=" + DEFAULT_RATING);

        // Get all the interviewNoteList where rating is greater than SMALLER_RATING
        defaultInterviewNoteShouldBeFound("rating.greaterThan=" + SMALLER_RATING);
    }

    @Test
    @Transactional
    void getAllInterviewNotesByActionItemsIsEqualToSomething() throws Exception {
        // Initialize the database
        interviewNoteRepository.saveAndFlush(interviewNote);

        // Get all the interviewNoteList where actionItems equals to DEFAULT_ACTION_ITEMS
        defaultInterviewNoteShouldBeFound("actionItems.equals=" + DEFAULT_ACTION_ITEMS);

        // Get all the interviewNoteList where actionItems equals to UPDATED_ACTION_ITEMS
        defaultInterviewNoteShouldNotBeFound("actionItems.equals=" + UPDATED_ACTION_ITEMS);
    }

    @Test
    @Transactional
    void getAllInterviewNotesByActionItemsIsInShouldWork() throws Exception {
        // Initialize the database
        interviewNoteRepository.saveAndFlush(interviewNote);

        // Get all the interviewNoteList where actionItems in DEFAULT_ACTION_ITEMS or UPDATED_ACTION_ITEMS
        defaultInterviewNoteShouldBeFound("actionItems.in=" + DEFAULT_ACTION_ITEMS + "," + UPDATED_ACTION_ITEMS);

        // Get all the interviewNoteList where actionItems equals to UPDATED_ACTION_ITEMS
        defaultInterviewNoteShouldNotBeFound("actionItems.in=" + UPDATED_ACTION_ITEMS);
    }

    @Test
    @Transactional
    void getAllInterviewNotesByActionItemsIsNullOrNotNull() throws Exception {
        // Initialize the database
        interviewNoteRepository.saveAndFlush(interviewNote);

        // Get all the interviewNoteList where actionItems is not null
        defaultInterviewNoteShouldBeFound("actionItems.specified=true");

        // Get all the interviewNoteList where actionItems is null
        defaultInterviewNoteShouldNotBeFound("actionItems.specified=false");
    }

    @Test
    @Transactional
    void getAllInterviewNotesByActionItemsContainsSomething() throws Exception {
        // Initialize the database
        interviewNoteRepository.saveAndFlush(interviewNote);

        // Get all the interviewNoteList where actionItems contains DEFAULT_ACTION_ITEMS
        defaultInterviewNoteShouldBeFound("actionItems.contains=" + DEFAULT_ACTION_ITEMS);

        // Get all the interviewNoteList where actionItems contains UPDATED_ACTION_ITEMS
        defaultInterviewNoteShouldNotBeFound("actionItems.contains=" + UPDATED_ACTION_ITEMS);
    }

    @Test
    @Transactional
    void getAllInterviewNotesByActionItemsNotContainsSomething() throws Exception {
        // Initialize the database
        interviewNoteRepository.saveAndFlush(interviewNote);

        // Get all the interviewNoteList where actionItems does not contain DEFAULT_ACTION_ITEMS
        defaultInterviewNoteShouldNotBeFound("actionItems.doesNotContain=" + DEFAULT_ACTION_ITEMS);

        // Get all the interviewNoteList where actionItems does not contain UPDATED_ACTION_ITEMS
        defaultInterviewNoteShouldBeFound("actionItems.doesNotContain=" + UPDATED_ACTION_ITEMS);
    }

    @Test
    @Transactional
    void getAllInterviewNotesByFeedbackIsEqualToSomething() throws Exception {
        // Initialize the database
        interviewNoteRepository.saveAndFlush(interviewNote);

        // Get all the interviewNoteList where feedback equals to DEFAULT_FEEDBACK
        defaultInterviewNoteShouldBeFound("feedback.equals=" + DEFAULT_FEEDBACK);

        // Get all the interviewNoteList where feedback equals to UPDATED_FEEDBACK
        defaultInterviewNoteShouldNotBeFound("feedback.equals=" + UPDATED_FEEDBACK);
    }

    @Test
    @Transactional
    void getAllInterviewNotesByFeedbackIsInShouldWork() throws Exception {
        // Initialize the database
        interviewNoteRepository.saveAndFlush(interviewNote);

        // Get all the interviewNoteList where feedback in DEFAULT_FEEDBACK or UPDATED_FEEDBACK
        defaultInterviewNoteShouldBeFound("feedback.in=" + DEFAULT_FEEDBACK + "," + UPDATED_FEEDBACK);

        // Get all the interviewNoteList where feedback equals to UPDATED_FEEDBACK
        defaultInterviewNoteShouldNotBeFound("feedback.in=" + UPDATED_FEEDBACK);
    }

    @Test
    @Transactional
    void getAllInterviewNotesByFeedbackIsNullOrNotNull() throws Exception {
        // Initialize the database
        interviewNoteRepository.saveAndFlush(interviewNote);

        // Get all the interviewNoteList where feedback is not null
        defaultInterviewNoteShouldBeFound("feedback.specified=true");

        // Get all the interviewNoteList where feedback is null
        defaultInterviewNoteShouldNotBeFound("feedback.specified=false");
    }

    @Test
    @Transactional
    void getAllInterviewNotesByFeedbackContainsSomething() throws Exception {
        // Initialize the database
        interviewNoteRepository.saveAndFlush(interviewNote);

        // Get all the interviewNoteList where feedback contains DEFAULT_FEEDBACK
        defaultInterviewNoteShouldBeFound("feedback.contains=" + DEFAULT_FEEDBACK);

        // Get all the interviewNoteList where feedback contains UPDATED_FEEDBACK
        defaultInterviewNoteShouldNotBeFound("feedback.contains=" + UPDATED_FEEDBACK);
    }

    @Test
    @Transactional
    void getAllInterviewNotesByFeedbackNotContainsSomething() throws Exception {
        // Initialize the database
        interviewNoteRepository.saveAndFlush(interviewNote);

        // Get all the interviewNoteList where feedback does not contain DEFAULT_FEEDBACK
        defaultInterviewNoteShouldNotBeFound("feedback.doesNotContain=" + DEFAULT_FEEDBACK);

        // Get all the interviewNoteList where feedback does not contain UPDATED_FEEDBACK
        defaultInterviewNoteShouldBeFound("feedback.doesNotContain=" + UPDATED_FEEDBACK);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInterviewNoteShouldBeFound(String filter) throws Exception {
        restInterviewNoteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(interviewNote.getId().intValue())))
            .andExpect(jsonPath("$.[*].noteText").value(hasItem(DEFAULT_NOTE_TEXT)))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].actionItems").value(hasItem(DEFAULT_ACTION_ITEMS)))
            .andExpect(jsonPath("$.[*].feedback").value(hasItem(DEFAULT_FEEDBACK)));

        // Check, that the count call also returns 1
        restInterviewNoteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInterviewNoteShouldNotBeFound(String filter) throws Exception {
        restInterviewNoteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInterviewNoteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingInterviewNote() throws Exception {
        // Get the interviewNote
        restInterviewNoteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInterviewNote() throws Exception {
        // Initialize the database
        interviewNoteRepository.saveAndFlush(interviewNote);

        int databaseSizeBeforeUpdate = interviewNoteRepository.findAll().size();

        // Update the interviewNote
        InterviewNote updatedInterviewNote = interviewNoteRepository.findById(interviewNote.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedInterviewNote are not directly saved in db
        em.detach(updatedInterviewNote);
        updatedInterviewNote
            .noteText(UPDATED_NOTE_TEXT)
            .rating(UPDATED_RATING)
            .actionItems(UPDATED_ACTION_ITEMS)
            .feedback(UPDATED_FEEDBACK);

        restInterviewNoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInterviewNote.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedInterviewNote))
            )
            .andExpect(status().isOk());

        // Validate the InterviewNote in the database
        List<InterviewNote> interviewNoteList = interviewNoteRepository.findAll();
        assertThat(interviewNoteList).hasSize(databaseSizeBeforeUpdate);
        InterviewNote testInterviewNote = interviewNoteList.get(interviewNoteList.size() - 1);
        assertThat(testInterviewNote.getNoteText()).isEqualTo(UPDATED_NOTE_TEXT);
        assertThat(testInterviewNote.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testInterviewNote.getActionItems()).isEqualTo(UPDATED_ACTION_ITEMS);
        assertThat(testInterviewNote.getFeedback()).isEqualTo(UPDATED_FEEDBACK);
    }

    @Test
    @Transactional
    void putNonExistingInterviewNote() throws Exception {
        int databaseSizeBeforeUpdate = interviewNoteRepository.findAll().size();
        interviewNote.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInterviewNoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, interviewNote.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(interviewNote))
            )
            .andExpect(status().isBadRequest());

        // Validate the InterviewNote in the database
        List<InterviewNote> interviewNoteList = interviewNoteRepository.findAll();
        assertThat(interviewNoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInterviewNote() throws Exception {
        int databaseSizeBeforeUpdate = interviewNoteRepository.findAll().size();
        interviewNote.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInterviewNoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(interviewNote))
            )
            .andExpect(status().isBadRequest());

        // Validate the InterviewNote in the database
        List<InterviewNote> interviewNoteList = interviewNoteRepository.findAll();
        assertThat(interviewNoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInterviewNote() throws Exception {
        int databaseSizeBeforeUpdate = interviewNoteRepository.findAll().size();
        interviewNote.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInterviewNoteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(interviewNote)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the InterviewNote in the database
        List<InterviewNote> interviewNoteList = interviewNoteRepository.findAll();
        assertThat(interviewNoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInterviewNoteWithPatch() throws Exception {
        // Initialize the database
        interviewNoteRepository.saveAndFlush(interviewNote);

        int databaseSizeBeforeUpdate = interviewNoteRepository.findAll().size();

        // Update the interviewNote using partial update
        InterviewNote partialUpdatedInterviewNote = new InterviewNote();
        partialUpdatedInterviewNote.setId(interviewNote.getId());

        partialUpdatedInterviewNote.noteText(UPDATED_NOTE_TEXT).rating(UPDATED_RATING);

        restInterviewNoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInterviewNote.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInterviewNote))
            )
            .andExpect(status().isOk());

        // Validate the InterviewNote in the database
        List<InterviewNote> interviewNoteList = interviewNoteRepository.findAll();
        assertThat(interviewNoteList).hasSize(databaseSizeBeforeUpdate);
        InterviewNote testInterviewNote = interviewNoteList.get(interviewNoteList.size() - 1);
        assertThat(testInterviewNote.getNoteText()).isEqualTo(UPDATED_NOTE_TEXT);
        assertThat(testInterviewNote.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testInterviewNote.getActionItems()).isEqualTo(DEFAULT_ACTION_ITEMS);
        assertThat(testInterviewNote.getFeedback()).isEqualTo(DEFAULT_FEEDBACK);
    }

    @Test
    @Transactional
    void fullUpdateInterviewNoteWithPatch() throws Exception {
        // Initialize the database
        interviewNoteRepository.saveAndFlush(interviewNote);

        int databaseSizeBeforeUpdate = interviewNoteRepository.findAll().size();

        // Update the interviewNote using partial update
        InterviewNote partialUpdatedInterviewNote = new InterviewNote();
        partialUpdatedInterviewNote.setId(interviewNote.getId());

        partialUpdatedInterviewNote
            .noteText(UPDATED_NOTE_TEXT)
            .rating(UPDATED_RATING)
            .actionItems(UPDATED_ACTION_ITEMS)
            .feedback(UPDATED_FEEDBACK);

        restInterviewNoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInterviewNote.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInterviewNote))
            )
            .andExpect(status().isOk());

        // Validate the InterviewNote in the database
        List<InterviewNote> interviewNoteList = interviewNoteRepository.findAll();
        assertThat(interviewNoteList).hasSize(databaseSizeBeforeUpdate);
        InterviewNote testInterviewNote = interviewNoteList.get(interviewNoteList.size() - 1);
        assertThat(testInterviewNote.getNoteText()).isEqualTo(UPDATED_NOTE_TEXT);
        assertThat(testInterviewNote.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testInterviewNote.getActionItems()).isEqualTo(UPDATED_ACTION_ITEMS);
        assertThat(testInterviewNote.getFeedback()).isEqualTo(UPDATED_FEEDBACK);
    }

    @Test
    @Transactional
    void patchNonExistingInterviewNote() throws Exception {
        int databaseSizeBeforeUpdate = interviewNoteRepository.findAll().size();
        interviewNote.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInterviewNoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, interviewNote.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(interviewNote))
            )
            .andExpect(status().isBadRequest());

        // Validate the InterviewNote in the database
        List<InterviewNote> interviewNoteList = interviewNoteRepository.findAll();
        assertThat(interviewNoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInterviewNote() throws Exception {
        int databaseSizeBeforeUpdate = interviewNoteRepository.findAll().size();
        interviewNote.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInterviewNoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(interviewNote))
            )
            .andExpect(status().isBadRequest());

        // Validate the InterviewNote in the database
        List<InterviewNote> interviewNoteList = interviewNoteRepository.findAll();
        assertThat(interviewNoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInterviewNote() throws Exception {
        int databaseSizeBeforeUpdate = interviewNoteRepository.findAll().size();
        interviewNote.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInterviewNoteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(interviewNote))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InterviewNote in the database
        List<InterviewNote> interviewNoteList = interviewNoteRepository.findAll();
        assertThat(interviewNoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInterviewNote() throws Exception {
        // Initialize the database
        interviewNoteRepository.saveAndFlush(interviewNote);

        int databaseSizeBeforeDelete = interviewNoteRepository.findAll().size();

        // Delete the interviewNote
        restInterviewNoteMockMvc
            .perform(delete(ENTITY_API_URL_ID, interviewNote.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InterviewNote> interviewNoteList = interviewNoteRepository.findAll();
        assertThat(interviewNoteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
