package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.YeNaya;
import com.mycompany.myapp.repository.YeNayaRepository;

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
 * Test class for the YeNayaResource REST controller.
 *
 * @see YeNayaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class YeNayaResourceTest {

    private static final String DEFAULT_FIRSTNAME = "SAMPLE_TEXT";
    private static final String UPDATED_FIRSTNAME = "UPDATED_TEXT";
    private static final String DEFAULT_LASTNAME = "SAMPLE_TEXT";
    private static final String UPDATED_LASTNAME = "UPDATED_TEXT";

    private static final Integer DEFAULT_AGE = 0;
    private static final Integer UPDATED_AGE = 1;
    private static final String DEFAULT_EMAIL = "SAMPLE_TEXT";
    private static final String UPDATED_EMAIL = "UPDATED_TEXT";

    @Inject
    private YeNayaRepository yeNayaRepository;

    private MockMvc restYeNayaMockMvc;

    private YeNaya yeNaya;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        YeNayaResource yeNayaResource = new YeNayaResource();
        ReflectionTestUtils.setField(yeNayaResource, "yeNayaRepository", yeNayaRepository);
        this.restYeNayaMockMvc = MockMvcBuilders.standaloneSetup(yeNayaResource).build();
    }

    @Before
    public void initTest() {
        yeNaya = new YeNaya();
        yeNaya.setFirstname(DEFAULT_FIRSTNAME);
        yeNaya.setLastname(DEFAULT_LASTNAME);
        yeNaya.setAge(DEFAULT_AGE);
        yeNaya.setEmail(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createYeNaya() throws Exception {
        int databaseSizeBeforeCreate = yeNayaRepository.findAll().size();

        // Create the YeNaya
        restYeNayaMockMvc.perform(post("/api/yeNayas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yeNaya)))
                .andExpect(status().isCreated());

        // Validate the YeNaya in the database
        List<YeNaya> yeNayas = yeNayaRepository.findAll();
        assertThat(yeNayas).hasSize(databaseSizeBeforeCreate + 1);
        YeNaya testYeNaya = yeNayas.get(yeNayas.size() - 1);
        assertThat(testYeNaya.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testYeNaya.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testYeNaya.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testYeNaya.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllYeNayas() throws Exception {
        // Initialize the database
        yeNayaRepository.saveAndFlush(yeNaya);

        // Get all the yeNayas
        restYeNayaMockMvc.perform(get("/api/yeNayas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(yeNaya.getId().intValue())))
                .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME.toString())))
                .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME.toString())))
                .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getYeNaya() throws Exception {
        // Initialize the database
        yeNayaRepository.saveAndFlush(yeNaya);

        // Get the yeNaya
        restYeNayaMockMvc.perform(get("/api/yeNayas/{id}", yeNaya.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(yeNaya.getId().intValue()))
            .andExpect(jsonPath("$.firstname").value(DEFAULT_FIRSTNAME.toString()))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingYeNaya() throws Exception {
        // Get the yeNaya
        restYeNayaMockMvc.perform(get("/api/yeNayas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateYeNaya() throws Exception {
        // Initialize the database
        yeNayaRepository.saveAndFlush(yeNaya);

		int databaseSizeBeforeUpdate = yeNayaRepository.findAll().size();

        // Update the yeNaya
        yeNaya.setFirstname(UPDATED_FIRSTNAME);
        yeNaya.setLastname(UPDATED_LASTNAME);
        yeNaya.setAge(UPDATED_AGE);
        yeNaya.setEmail(UPDATED_EMAIL);
        restYeNayaMockMvc.perform(put("/api/yeNayas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yeNaya)))
                .andExpect(status().isOk());

        // Validate the YeNaya in the database
        List<YeNaya> yeNayas = yeNayaRepository.findAll();
        assertThat(yeNayas).hasSize(databaseSizeBeforeUpdate);
        YeNaya testYeNaya = yeNayas.get(yeNayas.size() - 1);
        assertThat(testYeNaya.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testYeNaya.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testYeNaya.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testYeNaya.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void deleteYeNaya() throws Exception {
        // Initialize the database
        yeNayaRepository.saveAndFlush(yeNaya);

		int databaseSizeBeforeDelete = yeNayaRepository.findAll().size();

        // Get the yeNaya
        restYeNayaMockMvc.perform(delete("/api/yeNayas/{id}", yeNaya.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<YeNaya> yeNayas = yeNayaRepository.findAll();
        assertThat(yeNayas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
