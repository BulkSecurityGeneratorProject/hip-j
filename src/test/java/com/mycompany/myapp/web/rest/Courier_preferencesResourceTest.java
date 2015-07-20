package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Courier_preferences;
import com.mycompany.myapp.repository.Courier_preferencesRepository;

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
 * Test class for the Courier_preferencesResource REST controller.
 *
 * @see Courier_preferencesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Courier_preferencesResourceTest {


    private static final Integer DEFAULT_PRIORITY = 0;
    private static final Integer UPDATED_PRIORITY = 1;

    private static final Integer DEFAULT_SHIPMENT_LIMIT = 0;
    private static final Integer UPDATED_SHIPMENT_LIMIT = 1;

    private static final Boolean DEFAULT_IS_ENABLED = false;
    private static final Boolean UPDATED_IS_ENABLED = true;

    @Inject
    private Courier_preferencesRepository courier_preferencesRepository;

    private MockMvc restCourier_preferencesMockMvc;

    private Courier_preferences courier_preferences;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Courier_preferencesResource courier_preferencesResource = new Courier_preferencesResource();
        ReflectionTestUtils.setField(courier_preferencesResource, "courier_preferencesRepository", courier_preferencesRepository);
        this.restCourier_preferencesMockMvc = MockMvcBuilders.standaloneSetup(courier_preferencesResource).build();
    }

    @Before
    public void initTest() {
        courier_preferences = new Courier_preferences();
        courier_preferences.setPriority(DEFAULT_PRIORITY);
        courier_preferences.setShipment_limit(DEFAULT_SHIPMENT_LIMIT);
        courier_preferences.setIs_enabled(DEFAULT_IS_ENABLED);
    }

    @Test
    @Transactional
    public void createCourier_preferences() throws Exception {
        int databaseSizeBeforeCreate = courier_preferencesRepository.findAll().size();

        // Create the Courier_preferences
        restCourier_preferencesMockMvc.perform(post("/api/courier_preferencess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(courier_preferences)))
                .andExpect(status().isCreated());

        // Validate the Courier_preferences in the database
        List<Courier_preferences> courier_preferencess = courier_preferencesRepository.findAll();
        assertThat(courier_preferencess).hasSize(databaseSizeBeforeCreate + 1);
        Courier_preferences testCourier_preferences = courier_preferencess.get(courier_preferencess.size() - 1);
        assertThat(testCourier_preferences.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testCourier_preferences.getShipment_limit()).isEqualTo(DEFAULT_SHIPMENT_LIMIT);
        assertThat(testCourier_preferences.getIs_enabled()).isEqualTo(DEFAULT_IS_ENABLED);
    }

    @Test
    @Transactional
    public void checkPriorityIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(courier_preferencesRepository.findAll()).hasSize(0);
        // set the field null
        courier_preferences.setPriority(null);

        // Create the Courier_preferences, which fails.
        restCourier_preferencesMockMvc.perform(post("/api/courier_preferencess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(courier_preferences)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Courier_preferences> courier_preferencess = courier_preferencesRepository.findAll();
        assertThat(courier_preferencess).hasSize(0);
    }

    @Test
    @Transactional
    public void checkShipment_limitIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(courier_preferencesRepository.findAll()).hasSize(0);
        // set the field null
        courier_preferences.setShipment_limit(null);

        // Create the Courier_preferences, which fails.
        restCourier_preferencesMockMvc.perform(post("/api/courier_preferencess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(courier_preferences)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Courier_preferences> courier_preferencess = courier_preferencesRepository.findAll();
        assertThat(courier_preferencess).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllCourier_preferencess() throws Exception {
        // Initialize the database
        courier_preferencesRepository.saveAndFlush(courier_preferences);

        // Get all the courier_preferencess
        restCourier_preferencesMockMvc.perform(get("/api/courier_preferencess"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(courier_preferences.getId().intValue())))
                .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
                .andExpect(jsonPath("$.[*].shipment_limit").value(hasItem(DEFAULT_SHIPMENT_LIMIT)))
                .andExpect(jsonPath("$.[*].is_enabled").value(hasItem(DEFAULT_IS_ENABLED.booleanValue())));
    }

    @Test
    @Transactional
    public void getCourier_preferences() throws Exception {
        // Initialize the database
        courier_preferencesRepository.saveAndFlush(courier_preferences);

        // Get the courier_preferences
        restCourier_preferencesMockMvc.perform(get("/api/courier_preferencess/{id}", courier_preferences.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(courier_preferences.getId().intValue()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY))
            .andExpect(jsonPath("$.shipment_limit").value(DEFAULT_SHIPMENT_LIMIT))
            .andExpect(jsonPath("$.is_enabled").value(DEFAULT_IS_ENABLED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCourier_preferences() throws Exception {
        // Get the courier_preferences
        restCourier_preferencesMockMvc.perform(get("/api/courier_preferencess/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourier_preferences() throws Exception {
        // Initialize the database
        courier_preferencesRepository.saveAndFlush(courier_preferences);

		int databaseSizeBeforeUpdate = courier_preferencesRepository.findAll().size();

        // Update the courier_preferences
        courier_preferences.setPriority(UPDATED_PRIORITY);
        courier_preferences.setShipment_limit(UPDATED_SHIPMENT_LIMIT);
        courier_preferences.setIs_enabled(UPDATED_IS_ENABLED);
        restCourier_preferencesMockMvc.perform(put("/api/courier_preferencess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(courier_preferences)))
                .andExpect(status().isOk());

        // Validate the Courier_preferences in the database
        List<Courier_preferences> courier_preferencess = courier_preferencesRepository.findAll();
        assertThat(courier_preferencess).hasSize(databaseSizeBeforeUpdate);
        Courier_preferences testCourier_preferences = courier_preferencess.get(courier_preferencess.size() - 1);
        assertThat(testCourier_preferences.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testCourier_preferences.getShipment_limit()).isEqualTo(UPDATED_SHIPMENT_LIMIT);
        assertThat(testCourier_preferences.getIs_enabled()).isEqualTo(UPDATED_IS_ENABLED);
    }

    @Test
    @Transactional
    public void deleteCourier_preferences() throws Exception {
        // Initialize the database
        courier_preferencesRepository.saveAndFlush(courier_preferences);

		int databaseSizeBeforeDelete = courier_preferencesRepository.findAll().size();

        // Get the courier_preferences
        restCourier_preferencesMockMvc.perform(delete("/api/courier_preferencess/{id}", courier_preferences.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Courier_preferences> courier_preferencess = courier_preferencesRepository.findAll();
        assertThat(courier_preferencess).hasSize(databaseSizeBeforeDelete - 1);
    }
}
