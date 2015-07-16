package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Five;
import com.mycompany.myapp.repository.FiveRepository;

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
 * Test class for the FiveResource REST controller.
 *
 * @see FiveResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class FiveResourceTest {

    private static final String DEFAULT_A = "SAMPLE_TEXT";
    private static final String UPDATED_A = "UPDATED_TEXT";
    private static final String DEFAULT_S = "SAMPLE_TEXT";
    private static final String UPDATED_S = "UPDATED_TEXT";
    private static final String DEFAULT_D = "SAMPLE_TEXT";
    private static final String UPDATED_D = "UPDATED_TEXT";
    private static final String DEFAULT_F = "SAMPLE_TEXT";
    private static final String UPDATED_F = "UPDATED_TEXT";

    @Inject
    private FiveRepository fiveRepository;

    private MockMvc restFiveMockMvc;

    private Five five;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FiveResource fiveResource = new FiveResource();
        ReflectionTestUtils.setField(fiveResource, "fiveRepository", fiveRepository);
        this.restFiveMockMvc = MockMvcBuilders.standaloneSetup(fiveResource).build();
    }

    @Before
    public void initTest() {
        five = new Five();
        five.setA(DEFAULT_A);
        five.setS(DEFAULT_S);
        five.setD(DEFAULT_D);
        five.setF(DEFAULT_F);
    }

    @Test
    @Transactional
    public void createFive() throws Exception {
        int databaseSizeBeforeCreate = fiveRepository.findAll().size();

        // Create the Five
        restFiveMockMvc.perform(post("/api/fives")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(five)))
                .andExpect(status().isCreated());

        // Validate the Five in the database
        List<Five> fives = fiveRepository.findAll();
        assertThat(fives).hasSize(databaseSizeBeforeCreate + 1);
        Five testFive = fives.get(fives.size() - 1);
        assertThat(testFive.getA()).isEqualTo(DEFAULT_A);
        assertThat(testFive.getS()).isEqualTo(DEFAULT_S);
        assertThat(testFive.getD()).isEqualTo(DEFAULT_D);
        assertThat(testFive.getF()).isEqualTo(DEFAULT_F);
    }

    @Test
    @Transactional
    public void getAllFives() throws Exception {
        // Initialize the database
        fiveRepository.saveAndFlush(five);

        // Get all the fives
        restFiveMockMvc.perform(get("/api/fives"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(five.getId().intValue())))
                .andExpect(jsonPath("$.[*].a").value(hasItem(DEFAULT_A.toString())))
                .andExpect(jsonPath("$.[*].s").value(hasItem(DEFAULT_S.toString())))
                .andExpect(jsonPath("$.[*].d").value(hasItem(DEFAULT_D.toString())))
                .andExpect(jsonPath("$.[*].f").value(hasItem(DEFAULT_F.toString())));
    }

    @Test
    @Transactional
    public void getFive() throws Exception {
        // Initialize the database
        fiveRepository.saveAndFlush(five);

        // Get the five
        restFiveMockMvc.perform(get("/api/fives/{id}", five.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(five.getId().intValue()))
            .andExpect(jsonPath("$.a").value(DEFAULT_A.toString()))
            .andExpect(jsonPath("$.s").value(DEFAULT_S.toString()))
            .andExpect(jsonPath("$.d").value(DEFAULT_D.toString()))
            .andExpect(jsonPath("$.f").value(DEFAULT_F.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFive() throws Exception {
        // Get the five
        restFiveMockMvc.perform(get("/api/fives/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFive() throws Exception {
        // Initialize the database
        fiveRepository.saveAndFlush(five);

		int databaseSizeBeforeUpdate = fiveRepository.findAll().size();

        // Update the five
        five.setA(UPDATED_A);
        five.setS(UPDATED_S);
        five.setD(UPDATED_D);
        five.setF(UPDATED_F);
        restFiveMockMvc.perform(put("/api/fives")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(five)))
                .andExpect(status().isOk());

        // Validate the Five in the database
        List<Five> fives = fiveRepository.findAll();
        assertThat(fives).hasSize(databaseSizeBeforeUpdate);
        Five testFive = fives.get(fives.size() - 1);
        assertThat(testFive.getA()).isEqualTo(UPDATED_A);
        assertThat(testFive.getS()).isEqualTo(UPDATED_S);
        assertThat(testFive.getD()).isEqualTo(UPDATED_D);
        assertThat(testFive.getF()).isEqualTo(UPDATED_F);
    }

    @Test
    @Transactional
    public void deleteFive() throws Exception {
        // Initialize the database
        fiveRepository.saveAndFlush(five);

		int databaseSizeBeforeDelete = fiveRepository.findAll().size();

        // Get the five
        restFiveMockMvc.perform(delete("/api/fives/{id}", five.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Five> fives = fiveRepository.findAll();
        assertThat(fives).hasSize(databaseSizeBeforeDelete - 1);
    }
}
