package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Realdil;
import com.mycompany.myapp.repository.RealdilRepository;

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
 * Test class for the RealdilResource REST controller.
 *
 * @see RealdilResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RealdilResourceTest {

    private static final String DEFAULT_FIRSTNAME = "SAMPLE_TEXT";
    private static final String UPDATED_FIRSTNAME = "UPDATED_TEXT";
    private static final String DEFAULT_LASTNAME = "SAMPLE_TEXT";
    private static final String UPDATED_LASTNAME = "UPDATED_TEXT";

    private static final Integer DEFAULT_AGE = 0;
    private static final Integer UPDATED_AGE = 1;
    private static final String DEFAULT_EMAIL = "SAMPLE_TEXT";
    private static final String UPDATED_EMAIL = "UPDATED_TEXT";

    @Inject
    private RealdilRepository realdilRepository;

    private MockMvc restRealdilMockMvc;

    private Realdil realdil;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RealdilResource realdilResource = new RealdilResource();
        ReflectionTestUtils.setField(realdilResource, "realdilRepository", realdilRepository);
        this.restRealdilMockMvc = MockMvcBuilders.standaloneSetup(realdilResource).build();
    }

    @Before
    public void initTest() {
        realdil = new Realdil();
        realdil.setFirstname(DEFAULT_FIRSTNAME);
        realdil.setLastname(DEFAULT_LASTNAME);
        realdil.setAge(DEFAULT_AGE);
        realdil.setEmail(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createRealdil() throws Exception {
        int databaseSizeBeforeCreate = realdilRepository.findAll().size();

        // Create the Realdil
        restRealdilMockMvc.perform(post("/api/realdils")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(realdil)))
                .andExpect(status().isCreated());

        // Validate the Realdil in the database
        List<Realdil> realdils = realdilRepository.findAll();
        assertThat(realdils).hasSize(databaseSizeBeforeCreate + 1);
        Realdil testRealdil = realdils.get(realdils.size() - 1);
        assertThat(testRealdil.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testRealdil.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testRealdil.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testRealdil.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllRealdils() throws Exception {
        // Initialize the database
        realdilRepository.saveAndFlush(realdil);

        // Get all the realdils
        restRealdilMockMvc.perform(get("/api/realdils"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(realdil.getId().intValue())))
                .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME.toString())))
                .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME.toString())))
                .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getRealdil() throws Exception {
        // Initialize the database
        realdilRepository.saveAndFlush(realdil);

        // Get the realdil
        restRealdilMockMvc.perform(get("/api/realdils/{id}", realdil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(realdil.getId().intValue()))
            .andExpect(jsonPath("$.firstname").value(DEFAULT_FIRSTNAME.toString()))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRealdil() throws Exception {
        // Get the realdil
        restRealdilMockMvc.perform(get("/api/realdils/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRealdil() throws Exception {
        // Initialize the database
        realdilRepository.saveAndFlush(realdil);

		int databaseSizeBeforeUpdate = realdilRepository.findAll().size();

        // Update the realdil
        realdil.setFirstname(UPDATED_FIRSTNAME);
        realdil.setLastname(UPDATED_LASTNAME);
        realdil.setAge(UPDATED_AGE);
        realdil.setEmail(UPDATED_EMAIL);
        restRealdilMockMvc.perform(put("/api/realdils")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(realdil)))
                .andExpect(status().isOk());

        // Validate the Realdil in the database
        List<Realdil> realdils = realdilRepository.findAll();
        assertThat(realdils).hasSize(databaseSizeBeforeUpdate);
        Realdil testRealdil = realdils.get(realdils.size() - 1);
        assertThat(testRealdil.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testRealdil.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testRealdil.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testRealdil.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void deleteRealdil() throws Exception {
        // Initialize the database
        realdilRepository.saveAndFlush(realdil);

		int databaseSizeBeforeDelete = realdilRepository.findAll().size();

        // Get the realdil
        restRealdilMockMvc.perform(delete("/api/realdils/{id}", realdil.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Realdil> realdils = realdilRepository.findAll();
        assertThat(realdils).hasSize(databaseSizeBeforeDelete - 1);
    }
}
