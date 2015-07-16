package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Hoohaa;
import com.mycompany.myapp.repository.HoohaaRepository;

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
 * Test class for the HoohaaResource REST controller.
 *
 * @see HoohaaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HoohaaResourceTest {

    private static final String DEFAULT_A = "SAMPLE_TEXT";
    private static final String UPDATED_A = "UPDATED_TEXT";

    @Inject
    private HoohaaRepository hoohaaRepository;

    private MockMvc restHoohaaMockMvc;

    private Hoohaa hoohaa;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HoohaaResource hoohaaResource = new HoohaaResource();
        ReflectionTestUtils.setField(hoohaaResource, "hoohaaRepository", hoohaaRepository);
        this.restHoohaaMockMvc = MockMvcBuilders.standaloneSetup(hoohaaResource).build();
    }

    @Before
    public void initTest() {
        hoohaa = new Hoohaa();
        hoohaa.setA(DEFAULT_A);
    }

    @Test
    @Transactional
    public void createHoohaa() throws Exception {
        int databaseSizeBeforeCreate = hoohaaRepository.findAll().size();

        // Create the Hoohaa
        restHoohaaMockMvc.perform(post("/api/hoohaas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hoohaa)))
                .andExpect(status().isCreated());

        // Validate the Hoohaa in the database
        List<Hoohaa> hoohaas = hoohaaRepository.findAll();
        assertThat(hoohaas).hasSize(databaseSizeBeforeCreate + 1);
        Hoohaa testHoohaa = hoohaas.get(hoohaas.size() - 1);
        assertThat(testHoohaa.getA()).isEqualTo(DEFAULT_A);
    }

    @Test
    @Transactional
    public void getAllHoohaas() throws Exception {
        // Initialize the database
        hoohaaRepository.saveAndFlush(hoohaa);

        // Get all the hoohaas
        restHoohaaMockMvc.perform(get("/api/hoohaas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hoohaa.getId().intValue())))
                .andExpect(jsonPath("$.[*].a").value(hasItem(DEFAULT_A.toString())));
    }

    @Test
    @Transactional
    public void getHoohaa() throws Exception {
        // Initialize the database
        hoohaaRepository.saveAndFlush(hoohaa);

        // Get the hoohaa
        restHoohaaMockMvc.perform(get("/api/hoohaas/{id}", hoohaa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hoohaa.getId().intValue()))
            .andExpect(jsonPath("$.a").value(DEFAULT_A.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHoohaa() throws Exception {
        // Get the hoohaa
        restHoohaaMockMvc.perform(get("/api/hoohaas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHoohaa() throws Exception {
        // Initialize the database
        hoohaaRepository.saveAndFlush(hoohaa);

		int databaseSizeBeforeUpdate = hoohaaRepository.findAll().size();

        // Update the hoohaa
        hoohaa.setA(UPDATED_A);
        restHoohaaMockMvc.perform(put("/api/hoohaas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hoohaa)))
                .andExpect(status().isOk());

        // Validate the Hoohaa in the database
        List<Hoohaa> hoohaas = hoohaaRepository.findAll();
        assertThat(hoohaas).hasSize(databaseSizeBeforeUpdate);
        Hoohaa testHoohaa = hoohaas.get(hoohaas.size() - 1);
        assertThat(testHoohaa.getA()).isEqualTo(UPDATED_A);
    }

    @Test
    @Transactional
    public void deleteHoohaa() throws Exception {
        // Initialize the database
        hoohaaRepository.saveAndFlush(hoohaa);

		int databaseSizeBeforeDelete = hoohaaRepository.findAll().size();

        // Get the hoohaa
        restHoohaaMockMvc.perform(delete("/api/hoohaas/{id}", hoohaa.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Hoohaa> hoohaas = hoohaaRepository.findAll();
        assertThat(hoohaas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
