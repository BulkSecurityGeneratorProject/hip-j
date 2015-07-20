package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Fc_serviceability;
import com.mycompany.myapp.repository.Fc_serviceabilityRepository;

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
 * Test class for the Fc_serviceabilityResource REST controller.
 *
 * @see Fc_serviceabilityResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Fc_serviceabilityResourceTest {


    private static final Boolean DEFAULT_IS_ENABLED = false;
    private static final Boolean UPDATED_IS_ENABLED = true;
    private static final String DEFAULT_SHIPMENT_CUTOFF_TIME = "SAMPLE_TEXT";
    private static final String UPDATED_SHIPMENT_CUTOFF_TIME = "UPDATED_TEXT";

    @Inject
    private Fc_serviceabilityRepository fc_serviceabilityRepository;

    private MockMvc restFc_serviceabilityMockMvc;

    private Fc_serviceability fc_serviceability;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Fc_serviceabilityResource fc_serviceabilityResource = new Fc_serviceabilityResource();
        ReflectionTestUtils.setField(fc_serviceabilityResource, "fc_serviceabilityRepository", fc_serviceabilityRepository);
        this.restFc_serviceabilityMockMvc = MockMvcBuilders.standaloneSetup(fc_serviceabilityResource).build();
    }

    @Before
    public void initTest() {
        fc_serviceability = new Fc_serviceability();
        fc_serviceability.setIs_enabled(DEFAULT_IS_ENABLED);
        fc_serviceability.setShipment_cutoff_time(DEFAULT_SHIPMENT_CUTOFF_TIME);
    }

    @Test
    @Transactional
    public void createFc_serviceability() throws Exception {
        int databaseSizeBeforeCreate = fc_serviceabilityRepository.findAll().size();

        // Create the Fc_serviceability
        restFc_serviceabilityMockMvc.perform(post("/api/fc_serviceabilitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fc_serviceability)))
                .andExpect(status().isCreated());

        // Validate the Fc_serviceability in the database
        List<Fc_serviceability> fc_serviceabilitys = fc_serviceabilityRepository.findAll();
        assertThat(fc_serviceabilitys).hasSize(databaseSizeBeforeCreate + 1);
        Fc_serviceability testFc_serviceability = fc_serviceabilitys.get(fc_serviceabilitys.size() - 1);
        assertThat(testFc_serviceability.getIs_enabled()).isEqualTo(DEFAULT_IS_ENABLED);
        assertThat(testFc_serviceability.getShipment_cutoff_time()).isEqualTo(DEFAULT_SHIPMENT_CUTOFF_TIME);
    }

    @Test
    @Transactional
    public void checkShipment_cutoff_timeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(fc_serviceabilityRepository.findAll()).hasSize(0);
        // set the field null
        fc_serviceability.setShipment_cutoff_time(null);

        // Create the Fc_serviceability, which fails.
        restFc_serviceabilityMockMvc.perform(post("/api/fc_serviceabilitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fc_serviceability)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Fc_serviceability> fc_serviceabilitys = fc_serviceabilityRepository.findAll();
        assertThat(fc_serviceabilitys).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllFc_serviceabilitys() throws Exception {
        // Initialize the database
        fc_serviceabilityRepository.saveAndFlush(fc_serviceability);

        // Get all the fc_serviceabilitys
        restFc_serviceabilityMockMvc.perform(get("/api/fc_serviceabilitys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(fc_serviceability.getId().intValue())))
                .andExpect(jsonPath("$.[*].is_enabled").value(hasItem(DEFAULT_IS_ENABLED.booleanValue())))
                .andExpect(jsonPath("$.[*].shipment_cutoff_time").value(hasItem(DEFAULT_SHIPMENT_CUTOFF_TIME.toString())));
    }

    @Test
    @Transactional
    public void getFc_serviceability() throws Exception {
        // Initialize the database
        fc_serviceabilityRepository.saveAndFlush(fc_serviceability);

        // Get the fc_serviceability
        restFc_serviceabilityMockMvc.perform(get("/api/fc_serviceabilitys/{id}", fc_serviceability.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(fc_serviceability.getId().intValue()))
            .andExpect(jsonPath("$.is_enabled").value(DEFAULT_IS_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.shipment_cutoff_time").value(DEFAULT_SHIPMENT_CUTOFF_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFc_serviceability() throws Exception {
        // Get the fc_serviceability
        restFc_serviceabilityMockMvc.perform(get("/api/fc_serviceabilitys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFc_serviceability() throws Exception {
        // Initialize the database
        fc_serviceabilityRepository.saveAndFlush(fc_serviceability);

		int databaseSizeBeforeUpdate = fc_serviceabilityRepository.findAll().size();

        // Update the fc_serviceability
        fc_serviceability.setIs_enabled(UPDATED_IS_ENABLED);
        fc_serviceability.setShipment_cutoff_time(UPDATED_SHIPMENT_CUTOFF_TIME);
        restFc_serviceabilityMockMvc.perform(put("/api/fc_serviceabilitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fc_serviceability)))
                .andExpect(status().isOk());

        // Validate the Fc_serviceability in the database
        List<Fc_serviceability> fc_serviceabilitys = fc_serviceabilityRepository.findAll();
        assertThat(fc_serviceabilitys).hasSize(databaseSizeBeforeUpdate);
        Fc_serviceability testFc_serviceability = fc_serviceabilitys.get(fc_serviceabilitys.size() - 1);
        assertThat(testFc_serviceability.getIs_enabled()).isEqualTo(UPDATED_IS_ENABLED);
        assertThat(testFc_serviceability.getShipment_cutoff_time()).isEqualTo(UPDATED_SHIPMENT_CUTOFF_TIME);
    }

    @Test
    @Transactional
    public void deleteFc_serviceability() throws Exception {
        // Initialize the database
        fc_serviceabilityRepository.saveAndFlush(fc_serviceability);

		int databaseSizeBeforeDelete = fc_serviceabilityRepository.findAll().size();

        // Get the fc_serviceability
        restFc_serviceabilityMockMvc.perform(delete("/api/fc_serviceabilitys/{id}", fc_serviceability.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Fc_serviceability> fc_serviceabilitys = fc_serviceabilityRepository.findAll();
        assertThat(fc_serviceabilitys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
