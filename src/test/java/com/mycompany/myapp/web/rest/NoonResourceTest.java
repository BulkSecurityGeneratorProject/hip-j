package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Noon;
import com.mycompany.myapp.repository.NoonRepository;

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
 * Test class for the NoonResource REST controller.
 *
 * @see NoonResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class NoonResourceTest {

    private static final String DEFAULT_FIRST = "SAMPLE_TEXT";
    private static final String UPDATED_FIRST = "UPDATED_TEXT";
    private static final String DEFAULT_LAST = "SAMPLE_TEXT";
    private static final String UPDATED_LAST = "UPDATED_TEXT";

    private static final Integer DEFAULT_AGE = 0;
    private static final Integer UPDATED_AGE = 1;
    private static final String DEFAULT_EMAIL = "SAMPLE_TEXT";
    private static final String UPDATED_EMAIL = "UPDATED_TEXT";

    @Inject
    private NoonRepository noonRepository;

    private MockMvc restNoonMockMvc;

    private Noon noon;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NoonResource noonResource = new NoonResource();
        ReflectionTestUtils.setField(noonResource, "noonRepository", noonRepository);
        this.restNoonMockMvc = MockMvcBuilders.standaloneSetup(noonResource).build();
    }

    @Before
    public void initTest() {
        noon = new Noon();
        noon.setFirst(DEFAULT_FIRST);
        noon.setLast(DEFAULT_LAST);
        noon.setAge(DEFAULT_AGE);
        noon.setEmail(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createNoon() throws Exception {
        int databaseSizeBeforeCreate = noonRepository.findAll().size();

        // Create the Noon
        restNoonMockMvc.perform(post("/api/noons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(noon)))
                .andExpect(status().isCreated());

        // Validate the Noon in the database
        List<Noon> noons = noonRepository.findAll();
        assertThat(noons).hasSize(databaseSizeBeforeCreate + 1);
        Noon testNoon = noons.get(noons.size() - 1);
        assertThat(testNoon.getFirst()).isEqualTo(DEFAULT_FIRST);
        assertThat(testNoon.getLast()).isEqualTo(DEFAULT_LAST);
        assertThat(testNoon.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testNoon.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllNoons() throws Exception {
        // Initialize the database
        noonRepository.saveAndFlush(noon);

        // Get all the noons
        restNoonMockMvc.perform(get("/api/noons"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(noon.getId().intValue())))
                .andExpect(jsonPath("$.[*].first").value(hasItem(DEFAULT_FIRST.toString())))
                .andExpect(jsonPath("$.[*].last").value(hasItem(DEFAULT_LAST.toString())))
                .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getNoon() throws Exception {
        // Initialize the database
        noonRepository.saveAndFlush(noon);

        // Get the noon
        restNoonMockMvc.perform(get("/api/noons/{id}", noon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(noon.getId().intValue()))
            .andExpect(jsonPath("$.first").value(DEFAULT_FIRST.toString()))
            .andExpect(jsonPath("$.last").value(DEFAULT_LAST.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNoon() throws Exception {
        // Get the noon
        restNoonMockMvc.perform(get("/api/noons/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNoon() throws Exception {
        // Initialize the database
        noonRepository.saveAndFlush(noon);

		int databaseSizeBeforeUpdate = noonRepository.findAll().size();

        // Update the noon
        noon.setFirst(UPDATED_FIRST);
        noon.setLast(UPDATED_LAST);
        noon.setAge(UPDATED_AGE);
        noon.setEmail(UPDATED_EMAIL);
        restNoonMockMvc.perform(put("/api/noons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(noon)))
                .andExpect(status().isOk());

        // Validate the Noon in the database
        List<Noon> noons = noonRepository.findAll();
        assertThat(noons).hasSize(databaseSizeBeforeUpdate);
        Noon testNoon = noons.get(noons.size() - 1);
        assertThat(testNoon.getFirst()).isEqualTo(UPDATED_FIRST);
        assertThat(testNoon.getLast()).isEqualTo(UPDATED_LAST);
        assertThat(testNoon.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testNoon.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void deleteNoon() throws Exception {
        // Initialize the database
        noonRepository.saveAndFlush(noon);

		int databaseSizeBeforeDelete = noonRepository.findAll().size();

        // Get the noon
        restNoonMockMvc.perform(delete("/api/noons/{id}", noon.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Noon> noons = noonRepository.findAll();
        assertThat(noons).hasSize(databaseSizeBeforeDelete - 1);
    }
}
