package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Night;
import com.mycompany.myapp.repository.NightRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the NightResource REST controller.
 *
 * @see NightResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class NightResourceTest {

    private static final String DEFAULT_FIRST = "SAMPLE_TEXT";
    private static final String UPDATED_FIRST = "UPDATED_TEXT";
    private static final String DEFAULT_LAST = "SAMPLE_TEXT";
    private static final String UPDATED_LAST = "UPDATED_TEXT";

    private static final Integer DEFAULT_AGE = 0;
    private static final Integer UPDATED_AGE = 1;
    private static final String DEFAULT_EMAIL = "SAMPLE_TEXT";
    private static final String UPDATED_EMAIL = "UPDATED_TEXT";
    private static final String DEFAULT_MUMMY = "SAMPLE_TEXT";
    private static final String UPDATED_MUMMY = "UPDATED_TEXT";

    private static final Integer DEFAULT_NUMBER = 0;
    private static final Integer UPDATED_NUMBER = 1;

    @Inject
    private NightRepository nightRepository;

    private MockMvc restNightMockMvc;

    private Night night;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NightResource nightResource = new NightResource();
        ReflectionTestUtils.setField(nightResource, "nightRepository", nightRepository);
        this.restNightMockMvc = MockMvcBuilders.standaloneSetup(nightResource).build();
    }

    @Before
    public void initTest() {
        night = new Night();
        night.setFirst(DEFAULT_FIRST);
        night.setLast(DEFAULT_LAST);
        night.setAge(DEFAULT_AGE);
        night.setEmail(DEFAULT_EMAIL);
        night.setMummy(DEFAULT_MUMMY);
        night.setNumber(DEFAULT_NUMBER);
    }

    @Test
    @Transactional
    public void createNight() throws Exception {
        int databaseSizeBeforeCreate = nightRepository.findAll().size();

        // Create the Night
        restNightMockMvc.perform(post("/api/nights")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(night)))
                .andExpect(status().isCreated());

        // Validate the Night in the database
        List<Night> nights = nightRepository.findAll();
        assertThat(nights).hasSize(databaseSizeBeforeCreate + 1);
        Night testNight = nights.get(nights.size() - 1);
        assertThat(testNight.getFirst()).isEqualTo(DEFAULT_FIRST);
        assertThat(testNight.getLast()).isEqualTo(DEFAULT_LAST);
        assertThat(testNight.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testNight.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testNight.getMummy()).isEqualTo(DEFAULT_MUMMY);
        assertThat(testNight.getNumber()).isEqualTo(DEFAULT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllNights() throws Exception {
        // Initialize the database
        nightRepository.saveAndFlush(night);

        // Get all the nights
        restNightMockMvc.perform(get("/api/nights"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(night.getId().intValue())))
                .andExpect(jsonPath("$.[*].first").value(hasItem(DEFAULT_FIRST.toString())))
                .andExpect(jsonPath("$.[*].last").value(hasItem(DEFAULT_LAST.toString())))
                .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].mummy").value(hasItem(DEFAULT_MUMMY.toString())))
                .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)));
    }

    @Test
    @Transactional
    public void getNight() throws Exception {
        // Initialize the database
        nightRepository.saveAndFlush(night);

        // Get the night
        restNightMockMvc.perform(get("/api/nights/{id}", night.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(night.getId().intValue()))
            .andExpect(jsonPath("$.first").value(DEFAULT_FIRST.toString()))
            .andExpect(jsonPath("$.last").value(DEFAULT_LAST.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.mummy").value(DEFAULT_MUMMY.toString()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER));
    }

    @Test
    @Transactional
    public void getNonExistingNight() throws Exception {
        // Get the night
        restNightMockMvc.perform(get("/api/nights/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNight() throws Exception {
        // Initialize the database
        nightRepository.saveAndFlush(night);

		int databaseSizeBeforeUpdate = nightRepository.findAll().size();

        // Update the night
        night.setFirst(UPDATED_FIRST);
        night.setLast(UPDATED_LAST);
        night.setAge(UPDATED_AGE);
        night.setEmail(UPDATED_EMAIL);
        night.setMummy(UPDATED_MUMMY);
        night.setNumber(UPDATED_NUMBER);
        restNightMockMvc.perform(put("/api/nights")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(night)))
                .andExpect(status().isOk());

        // Validate the Night in the database
        List<Night> nights = nightRepository.findAll();
        assertThat(nights).hasSize(databaseSizeBeforeUpdate);
        Night testNight = nights.get(nights.size() - 1);
        assertThat(testNight.getFirst()).isEqualTo(UPDATED_FIRST);
        assertThat(testNight.getLast()).isEqualTo(UPDATED_LAST);
        assertThat(testNight.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testNight.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testNight.getMummy()).isEqualTo(UPDATED_MUMMY);
        assertThat(testNight.getNumber()).isEqualTo(UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void deleteNight() throws Exception {
        // Initialize the database
        nightRepository.saveAndFlush(night);

		int databaseSizeBeforeDelete = nightRepository.findAll().size();

        // Get the night
        restNightMockMvc.perform(delete("/api/nights/{id}", night.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Night> nights = nightRepository.findAll();
        assertThat(nights).hasSize(databaseSizeBeforeDelete - 1);
    }
}
