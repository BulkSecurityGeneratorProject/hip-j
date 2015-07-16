package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Sssss;
import com.mycompany.myapp.repository.SssssRepository;

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
 * Test class for the SssssResource REST controller.
 *
 * @see SssssResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SssssResourceTest {

    private static final String DEFAULT_WWEWWE = "SAMPLE_TEXT";
    private static final String UPDATED_WWEWWE = "UPDATED_TEXT";

    @Inject
    private SssssRepository sssssRepository;

    private MockMvc restSssssMockMvc;

    private Sssss sssss;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SssssResource sssssResource = new SssssResource();
        ReflectionTestUtils.setField(sssssResource, "sssssRepository", sssssRepository);
        this.restSssssMockMvc = MockMvcBuilders.standaloneSetup(sssssResource).build();
    }

    @Before
    public void initTest() {
        sssss = new Sssss();
        sssss.setWwewwe(DEFAULT_WWEWWE);
    }

    @Test
    @Transactional
    public void createSssss() throws Exception {
        int databaseSizeBeforeCreate = sssssRepository.findAll().size();

        // Create the Sssss
        restSssssMockMvc.perform(post("/api/ssssss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sssss)))
                .andExpect(status().isCreated());

        // Validate the Sssss in the database
        List<Sssss> ssssss = sssssRepository.findAll();
        assertThat(ssssss).hasSize(databaseSizeBeforeCreate + 1);
        Sssss testSssss = ssssss.get(ssssss.size() - 1);
        assertThat(testSssss.getWwewwe()).isEqualTo(DEFAULT_WWEWWE);
    }

    @Test
    @Transactional
    public void getAllSsssss() throws Exception {
        // Initialize the database
        sssssRepository.saveAndFlush(sssss);

        // Get all the ssssss
        restSssssMockMvc.perform(get("/api/ssssss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(sssss.getId().intValue())))
                .andExpect(jsonPath("$.[*].wwewwe").value(hasItem(DEFAULT_WWEWWE.toString())));
    }

    @Test
    @Transactional
    public void getSssss() throws Exception {
        // Initialize the database
        sssssRepository.saveAndFlush(sssss);

        // Get the sssss
        restSssssMockMvc.perform(get("/api/ssssss/{id}", sssss.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(sssss.getId().intValue()))
            .andExpect(jsonPath("$.wwewwe").value(DEFAULT_WWEWWE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSssss() throws Exception {
        // Get the sssss
        restSssssMockMvc.perform(get("/api/ssssss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSssss() throws Exception {
        // Initialize the database
        sssssRepository.saveAndFlush(sssss);

		int databaseSizeBeforeUpdate = sssssRepository.findAll().size();

        // Update the sssss
        sssss.setWwewwe(UPDATED_WWEWWE);
        restSssssMockMvc.perform(put("/api/ssssss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sssss)))
                .andExpect(status().isOk());

        // Validate the Sssss in the database
        List<Sssss> ssssss = sssssRepository.findAll();
        assertThat(ssssss).hasSize(databaseSizeBeforeUpdate);
        Sssss testSssss = ssssss.get(ssssss.size() - 1);
        assertThat(testSssss.getWwewwe()).isEqualTo(UPDATED_WWEWWE);
    }

    @Test
    @Transactional
    public void deleteSssss() throws Exception {
        // Initialize the database
        sssssRepository.saveAndFlush(sssss);

		int databaseSizeBeforeDelete = sssssRepository.findAll().size();

        // Get the sssss
        restSssssMockMvc.perform(delete("/api/ssssss/{id}", sssss.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Sssss> ssssss = sssssRepository.findAll();
        assertThat(ssssss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
