package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Courier_country_serviceability;
import com.mycompany.myapp.repository.Courier_country_serviceabilityRepository;

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
 * Test class for the Courier_country_serviceabilityResource REST controller.
 *
 * @see Courier_country_serviceabilityResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Courier_country_serviceabilityResourceTest {


    private static final Boolean DEFAULT_IS_ENABLED = false;
    private static final Boolean UPDATED_IS_ENABLED = true;

    private static final Integer DEFAULT_SHIPMENT_CAPACITY = 0;
    private static final Integer UPDATED_SHIPMENT_CAPACITY = 1;

    @Inject
    private Courier_country_serviceabilityRepository courier_country_serviceabilityRepository;

    private MockMvc restCourier_country_serviceabilityMockMvc;

    private Courier_country_serviceability courier_country_serviceability;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Courier_country_serviceabilityResource courier_country_serviceabilityResource = new Courier_country_serviceabilityResource();
        ReflectionTestUtils.setField(courier_country_serviceabilityResource, "courier_country_serviceabilityRepository", courier_country_serviceabilityRepository);
        this.restCourier_country_serviceabilityMockMvc = MockMvcBuilders.standaloneSetup(courier_country_serviceabilityResource).build();
    }

    @Before
    public void initTest() {
        courier_country_serviceability = new Courier_country_serviceability();
        courier_country_serviceability.setIs_enabled(DEFAULT_IS_ENABLED);
        courier_country_serviceability.setShipment_capacity(DEFAULT_SHIPMENT_CAPACITY);
    }

    @Test
    @Transactional
    public void createCourier_country_serviceability() throws Exception {
        int databaseSizeBeforeCreate = courier_country_serviceabilityRepository.findAll().size();

        // Create the Courier_country_serviceability
        restCourier_country_serviceabilityMockMvc.perform(post("/api/courier_country_serviceabilitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(courier_country_serviceability)))
                .andExpect(status().isCreated());

        // Validate the Courier_country_serviceability in the database
        List<Courier_country_serviceability> courier_country_serviceabilitys = courier_country_serviceabilityRepository.findAll();
        assertThat(courier_country_serviceabilitys).hasSize(databaseSizeBeforeCreate + 1);
        Courier_country_serviceability testCourier_country_serviceability = courier_country_serviceabilitys.get(courier_country_serviceabilitys.size() - 1);
        assertThat(testCourier_country_serviceability.getIs_enabled()).isEqualTo(DEFAULT_IS_ENABLED);
        assertThat(testCourier_country_serviceability.getShipment_capacity()).isEqualTo(DEFAULT_SHIPMENT_CAPACITY);
    }

    @Test
    @Transactional
    public void checkShipment_capacityIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(courier_country_serviceabilityRepository.findAll()).hasSize(0);
        // set the field null
        courier_country_serviceability.setShipment_capacity(null);

        // Create the Courier_country_serviceability, which fails.
        restCourier_country_serviceabilityMockMvc.perform(post("/api/courier_country_serviceabilitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(courier_country_serviceability)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Courier_country_serviceability> courier_country_serviceabilitys = courier_country_serviceabilityRepository.findAll();
        assertThat(courier_country_serviceabilitys).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllCourier_country_serviceabilitys() throws Exception {
        // Initialize the database
        courier_country_serviceabilityRepository.saveAndFlush(courier_country_serviceability);

        // Get all the courier_country_serviceabilitys
        restCourier_country_serviceabilityMockMvc.perform(get("/api/courier_country_serviceabilitys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(courier_country_serviceability.getId().intValue())))
                .andExpect(jsonPath("$.[*].is_enabled").value(hasItem(DEFAULT_IS_ENABLED.booleanValue())))
                .andExpect(jsonPath("$.[*].shipment_capacity").value(hasItem(DEFAULT_SHIPMENT_CAPACITY)));
    }

    @Test
    @Transactional
    public void getCourier_country_serviceability() throws Exception {
        // Initialize the database
        courier_country_serviceabilityRepository.saveAndFlush(courier_country_serviceability);

        // Get the courier_country_serviceability
        restCourier_country_serviceabilityMockMvc.perform(get("/api/courier_country_serviceabilitys/{id}", courier_country_serviceability.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(courier_country_serviceability.getId().intValue()))
            .andExpect(jsonPath("$.is_enabled").value(DEFAULT_IS_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.shipment_capacity").value(DEFAULT_SHIPMENT_CAPACITY));
    }

    @Test
    @Transactional
    public void getNonExistingCourier_country_serviceability() throws Exception {
        // Get the courier_country_serviceability
        restCourier_country_serviceabilityMockMvc.perform(get("/api/courier_country_serviceabilitys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourier_country_serviceability() throws Exception {
        // Initialize the database
        courier_country_serviceabilityRepository.saveAndFlush(courier_country_serviceability);

		int databaseSizeBeforeUpdate = courier_country_serviceabilityRepository.findAll().size();

        // Update the courier_country_serviceability
        courier_country_serviceability.setIs_enabled(UPDATED_IS_ENABLED);
        courier_country_serviceability.setShipment_capacity(UPDATED_SHIPMENT_CAPACITY);
        restCourier_country_serviceabilityMockMvc.perform(put("/api/courier_country_serviceabilitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(courier_country_serviceability)))
                .andExpect(status().isOk());

        // Validate the Courier_country_serviceability in the database
        List<Courier_country_serviceability> courier_country_serviceabilitys = courier_country_serviceabilityRepository.findAll();
        assertThat(courier_country_serviceabilitys).hasSize(databaseSizeBeforeUpdate);
        Courier_country_serviceability testCourier_country_serviceability = courier_country_serviceabilitys.get(courier_country_serviceabilitys.size() - 1);
        assertThat(testCourier_country_serviceability.getIs_enabled()).isEqualTo(UPDATED_IS_ENABLED);
        assertThat(testCourier_country_serviceability.getShipment_capacity()).isEqualTo(UPDATED_SHIPMENT_CAPACITY);
    }

    @Test
    @Transactional
    public void deleteCourier_country_serviceability() throws Exception {
        // Initialize the database
        courier_country_serviceabilityRepository.saveAndFlush(courier_country_serviceability);

		int databaseSizeBeforeDelete = courier_country_serviceabilityRepository.findAll().size();

        // Get the courier_country_serviceability
        restCourier_country_serviceabilityMockMvc.perform(delete("/api/courier_country_serviceabilitys/{id}", courier_country_serviceability.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Courier_country_serviceability> courier_country_serviceabilitys = courier_country_serviceabilityRepository.findAll();
        assertThat(courier_country_serviceabilitys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
