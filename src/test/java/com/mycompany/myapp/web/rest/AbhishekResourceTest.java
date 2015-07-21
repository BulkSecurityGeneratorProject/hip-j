package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Abhishek;
import com.mycompany.myapp.repository.AbhishekRepository;

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
 * Test class for the AbhishekResource REST controller.
 *
 * @see AbhishekResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AbhishekResourceTest {

    private static final String DEFAULT_FIRSTNAME = "SAMPLE_TEXT";
    private static final String UPDATED_FIRSTNAME = "UPDATED_TEXT";
    private static final String DEFAULT_LASTNAME = "SAMPLE_TEXT";
    private static final String UPDATED_LASTNAME = "UPDATED_TEXT";
    private static final String DEFAULT_AGE = "SAMPLE_TEXT";
    private static final String UPDATED_AGE = "UPDATED_TEXT";

    @Inject
    private AbhishekRepository abhishekRepository;

    private MockMvc restAbhishekMockMvc;

    private Abhishek abhishek;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AbhishekResource abhishekResource = new AbhishekResource();
        ReflectionTestUtils.setField(abhishekResource, "abhishekRepository", abhishekRepository);
        this.restAbhishekMockMvc = MockMvcBuilders.standaloneSetup(abhishekResource).build();
    }

    @Before
    public void initTest() {
        abhishek = new Abhishek();
        abhishek.setFirstname(DEFAULT_FIRSTNAME);
        abhishek.setLastname(DEFAULT_LASTNAME);
        abhishek.setAge(DEFAULT_AGE);
    }

    @Test
    @Transactional
    public void createAbhishek() throws Exception {
        int databaseSizeBeforeCreate = abhishekRepository.findAll().size();

        // Create the Abhishek
        restAbhishekMockMvc.perform(post("/api/abhisheks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(abhishek)))
                .andExpect(status().isCreated());

        // Validate the Abhishek in the database
        List<Abhishek> abhisheks = abhishekRepository.findAll();
        assertThat(abhisheks).hasSize(databaseSizeBeforeCreate + 1);
        Abhishek testAbhishek = abhisheks.get(abhisheks.size() - 1);
        assertThat(testAbhishek.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testAbhishek.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testAbhishek.getAge()).isEqualTo(DEFAULT_AGE);
    }

    @Test
    @Transactional
    public void getAllAbhisheks() throws Exception {
        // Initialize the database
        abhishekRepository.saveAndFlush(abhishek);

        // Get all the abhisheks
        restAbhishekMockMvc.perform(get("/api/abhisheks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(abhishek.getId().intValue())))
                .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME.toString())))
                .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME.toString())))
                .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE.toString())));
    }

    @Test
    @Transactional
    public void getAbhishek() throws Exception {
        // Initialize the database
        abhishekRepository.saveAndFlush(abhishek);

        // Get the abhishek
        restAbhishekMockMvc.perform(get("/api/abhisheks/{id}", abhishek.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(abhishek.getId().intValue()))
            .andExpect(jsonPath("$.firstname").value(DEFAULT_FIRSTNAME.toString()))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAbhishek() throws Exception {
        // Get the abhishek
        restAbhishekMockMvc.perform(get("/api/abhisheks/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAbhishek() throws Exception {
        // Initialize the database
        abhishekRepository.saveAndFlush(abhishek);

		int databaseSizeBeforeUpdate = abhishekRepository.findAll().size();

        // Update the abhishek
        abhishek.setFirstname(UPDATED_FIRSTNAME);
        abhishek.setLastname(UPDATED_LASTNAME);
        abhishek.setAge(UPDATED_AGE);
        restAbhishekMockMvc.perform(put("/api/abhisheks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(abhishek)))
                .andExpect(status().isOk());

        // Validate the Abhishek in the database
        List<Abhishek> abhisheks = abhishekRepository.findAll();
        assertThat(abhisheks).hasSize(databaseSizeBeforeUpdate);
        Abhishek testAbhishek = abhisheks.get(abhisheks.size() - 1);
        assertThat(testAbhishek.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testAbhishek.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testAbhishek.getAge()).isEqualTo(UPDATED_AGE);
    }

    @Test
    @Transactional
    public void deleteAbhishek() throws Exception {
        // Initialize the database
        abhishekRepository.saveAndFlush(abhishek);

		int databaseSizeBeforeDelete = abhishekRepository.findAll().size();

        // Get the abhishek
        restAbhishekMockMvc.perform(delete("/api/abhisheks/{id}", abhishek.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Abhishek> abhisheks = abhishekRepository.findAll();
        assertThat(abhisheks).hasSize(databaseSizeBeforeDelete - 1);
    }
}
