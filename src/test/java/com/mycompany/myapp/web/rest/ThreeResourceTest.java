package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Three;
import com.mycompany.myapp.repository.ThreeRepository;

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
 * Test class for the ThreeResource REST controller.
 *
 * @see ThreeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ThreeResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_COUNTRY = "SAMPLE_TEXT";
    private static final String UPDATED_COUNTRY = "UPDATED_TEXT";

    private static final Integer DEFAULT_AGE = 0;
    private static final Integer UPDATED_AGE = 1;

    private static final Integer DEFAULT_MATCHES = 0;
    private static final Integer UPDATED_MATCHES = 1;

    private static final Integer DEFAULT_CATCHES = 0;
    private static final Integer UPDATED_CATCHES = 1;
    private static final String DEFAULT_EMAIL = "SAMPLE_TEXT";
    private static final String UPDATED_EMAIL = "UPDATED_TEXT";

    @Inject
    private ThreeRepository threeRepository;

    private MockMvc restThreeMockMvc;

    private Three three;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ThreeResource threeResource = new ThreeResource();
        ReflectionTestUtils.setField(threeResource, "threeRepository", threeRepository);
        this.restThreeMockMvc = MockMvcBuilders.standaloneSetup(threeResource).build();
    }

    @Before
    public void initTest() {
        three = new Three();
        three.setName(DEFAULT_NAME);
        three.setCountry(DEFAULT_COUNTRY);
        three.setAge(DEFAULT_AGE);
        three.setMatches(DEFAULT_MATCHES);
        three.setCatches(DEFAULT_CATCHES);
        three.setEmail(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createThree() throws Exception {
        int databaseSizeBeforeCreate = threeRepository.findAll().size();

        // Create the Three
        restThreeMockMvc.perform(post("/api/threes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(three)))
                .andExpect(status().isCreated());

        // Validate the Three in the database
        List<Three> threes = threeRepository.findAll();
        assertThat(threes).hasSize(databaseSizeBeforeCreate + 1);
        Three testThree = threes.get(threes.size() - 1);
        assertThat(testThree.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testThree.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testThree.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testThree.getMatches()).isEqualTo(DEFAULT_MATCHES);
        assertThat(testThree.getCatches()).isEqualTo(DEFAULT_CATCHES);
        assertThat(testThree.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllThrees() throws Exception {
        // Initialize the database
        threeRepository.saveAndFlush(three);

        // Get all the threes
        restThreeMockMvc.perform(get("/api/threes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(three.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
                .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
                .andExpect(jsonPath("$.[*].matches").value(hasItem(DEFAULT_MATCHES)))
                .andExpect(jsonPath("$.[*].catches").value(hasItem(DEFAULT_CATCHES)))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getThree() throws Exception {
        // Initialize the database
        threeRepository.saveAndFlush(three);

        // Get the three
        restThreeMockMvc.perform(get("/api/threes/{id}", three.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(three.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.matches").value(DEFAULT_MATCHES))
            .andExpect(jsonPath("$.catches").value(DEFAULT_CATCHES))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingThree() throws Exception {
        // Get the three
        restThreeMockMvc.perform(get("/api/threes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThree() throws Exception {
        // Initialize the database
        threeRepository.saveAndFlush(three);

		int databaseSizeBeforeUpdate = threeRepository.findAll().size();

        // Update the three
        three.setName(UPDATED_NAME);
        three.setCountry(UPDATED_COUNTRY);
        three.setAge(UPDATED_AGE);
        three.setMatches(UPDATED_MATCHES);
        three.setCatches(UPDATED_CATCHES);
        three.setEmail(UPDATED_EMAIL);
        restThreeMockMvc.perform(put("/api/threes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(three)))
                .andExpect(status().isOk());

        // Validate the Three in the database
        List<Three> threes = threeRepository.findAll();
        assertThat(threes).hasSize(databaseSizeBeforeUpdate);
        Three testThree = threes.get(threes.size() - 1);
        assertThat(testThree.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testThree.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testThree.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testThree.getMatches()).isEqualTo(UPDATED_MATCHES);
        assertThat(testThree.getCatches()).isEqualTo(UPDATED_CATCHES);
        assertThat(testThree.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void deleteThree() throws Exception {
        // Initialize the database
        threeRepository.saveAndFlush(three);

		int databaseSizeBeforeDelete = threeRepository.findAll().size();

        // Get the three
        restThreeMockMvc.perform(delete("/api/threes/{id}", three.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Three> threes = threeRepository.findAll();
        assertThat(threes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
