package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Courier_pincode_serviceability;
import com.mycompany.myapp.repository.Courier_pincode_serviceabilityRepository;

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
 * Test class for the Courier_pincode_serviceabilityResource REST controller.
 *
 * @see Courier_pincode_serviceabilityResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Courier_pincode_serviceabilityResourceTest {

    private static final String DEFAULT_PINCODE = "SAMPLE_TEXT";
    private static final String UPDATED_PINCODE = "UPDATED_TEXT";

    private static final Integer DEFAULT_CAPACITY = 0;
    private static final Integer UPDATED_CAPACITY = 1;

    @Inject
    private Courier_pincode_serviceabilityRepository courier_pincode_serviceabilityRepository;

    private MockMvc restCourier_pincode_serviceabilityMockMvc;

    private Courier_pincode_serviceability courier_pincode_serviceability;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Courier_pincode_serviceabilityResource courier_pincode_serviceabilityResource = new Courier_pincode_serviceabilityResource();
        ReflectionTestUtils.setField(courier_pincode_serviceabilityResource, "courier_pincode_serviceabilityRepository", courier_pincode_serviceabilityRepository);
        this.restCourier_pincode_serviceabilityMockMvc = MockMvcBuilders.standaloneSetup(courier_pincode_serviceabilityResource).build();
    }

    @Before
    public void initTest() {
        courier_pincode_serviceability = new Courier_pincode_serviceability();
        courier_pincode_serviceability.setPincode(DEFAULT_PINCODE);
        courier_pincode_serviceability.setCapacity(DEFAULT_CAPACITY);
    }

    @Test
    @Transactional
    public void createCourier_pincode_serviceability() throws Exception {
        int databaseSizeBeforeCreate = courier_pincode_serviceabilityRepository.findAll().size();

        // Create the Courier_pincode_serviceability
        restCourier_pincode_serviceabilityMockMvc.perform(post("/api/courier_pincode_serviceabilitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(courier_pincode_serviceability)))
                .andExpect(status().isCreated());

        // Validate the Courier_pincode_serviceability in the database
        List<Courier_pincode_serviceability> courier_pincode_serviceabilitys = courier_pincode_serviceabilityRepository.findAll();
        assertThat(courier_pincode_serviceabilitys).hasSize(databaseSizeBeforeCreate + 1);
        Courier_pincode_serviceability testCourier_pincode_serviceability = courier_pincode_serviceabilitys.get(courier_pincode_serviceabilitys.size() - 1);
        assertThat(testCourier_pincode_serviceability.getPincode()).isEqualTo(DEFAULT_PINCODE);
        assertThat(testCourier_pincode_serviceability.getCapacity()).isEqualTo(DEFAULT_CAPACITY);
    }

    @Test
    @Transactional
    public void checkPincodeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(courier_pincode_serviceabilityRepository.findAll()).hasSize(0);
        // set the field null
        courier_pincode_serviceability.setPincode(null);

        // Create the Courier_pincode_serviceability, which fails.
        restCourier_pincode_serviceabilityMockMvc.perform(post("/api/courier_pincode_serviceabilitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(courier_pincode_serviceability)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Courier_pincode_serviceability> courier_pincode_serviceabilitys = courier_pincode_serviceabilityRepository.findAll();
        assertThat(courier_pincode_serviceabilitys).hasSize(0);
    }

    @Test
    @Transactional
    public void checkCapacityIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(courier_pincode_serviceabilityRepository.findAll()).hasSize(0);
        // set the field null
        courier_pincode_serviceability.setCapacity(null);

        // Create the Courier_pincode_serviceability, which fails.
        restCourier_pincode_serviceabilityMockMvc.perform(post("/api/courier_pincode_serviceabilitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(courier_pincode_serviceability)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Courier_pincode_serviceability> courier_pincode_serviceabilitys = courier_pincode_serviceabilityRepository.findAll();
        assertThat(courier_pincode_serviceabilitys).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllCourier_pincode_serviceabilitys() throws Exception {
        // Initialize the database
        courier_pincode_serviceabilityRepository.saveAndFlush(courier_pincode_serviceability);

        // Get all the courier_pincode_serviceabilitys
        restCourier_pincode_serviceabilityMockMvc.perform(get("/api/courier_pincode_serviceabilitys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(courier_pincode_serviceability.getId().intValue())))
                .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE.toString())))
                .andExpect(jsonPath("$.[*].capacity").value(hasItem(DEFAULT_CAPACITY)));
    }

    @Test
    @Transactional
    public void getCourier_pincode_serviceability() throws Exception {
        // Initialize the database
        courier_pincode_serviceabilityRepository.saveAndFlush(courier_pincode_serviceability);

        // Get the courier_pincode_serviceability
        restCourier_pincode_serviceabilityMockMvc.perform(get("/api/courier_pincode_serviceabilitys/{id}", courier_pincode_serviceability.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(courier_pincode_serviceability.getId().intValue()))
            .andExpect(jsonPath("$.pincode").value(DEFAULT_PINCODE.toString()))
            .andExpect(jsonPath("$.capacity").value(DEFAULT_CAPACITY));
    }

    @Test
    @Transactional
    public void getNonExistingCourier_pincode_serviceability() throws Exception {
        // Get the courier_pincode_serviceability
        restCourier_pincode_serviceabilityMockMvc.perform(get("/api/courier_pincode_serviceabilitys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourier_pincode_serviceability() throws Exception {
        // Initialize the database
        courier_pincode_serviceabilityRepository.saveAndFlush(courier_pincode_serviceability);

		int databaseSizeBeforeUpdate = courier_pincode_serviceabilityRepository.findAll().size();

        // Update the courier_pincode_serviceability
        courier_pincode_serviceability.setPincode(UPDATED_PINCODE);
        courier_pincode_serviceability.setCapacity(UPDATED_CAPACITY);
        restCourier_pincode_serviceabilityMockMvc.perform(put("/api/courier_pincode_serviceabilitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(courier_pincode_serviceability)))
                .andExpect(status().isOk());

        // Validate the Courier_pincode_serviceability in the database
        List<Courier_pincode_serviceability> courier_pincode_serviceabilitys = courier_pincode_serviceabilityRepository.findAll();
        assertThat(courier_pincode_serviceabilitys).hasSize(databaseSizeBeforeUpdate);
        Courier_pincode_serviceability testCourier_pincode_serviceability = courier_pincode_serviceabilitys.get(courier_pincode_serviceabilitys.size() - 1);
        assertThat(testCourier_pincode_serviceability.getPincode()).isEqualTo(UPDATED_PINCODE);
        assertThat(testCourier_pincode_serviceability.getCapacity()).isEqualTo(UPDATED_CAPACITY);
    }

    @Test
    @Transactional
    public void deleteCourier_pincode_serviceability() throws Exception {
        // Initialize the database
        courier_pincode_serviceabilityRepository.saveAndFlush(courier_pincode_serviceability);

		int databaseSizeBeforeDelete = courier_pincode_serviceabilityRepository.findAll().size();

        // Get the courier_pincode_serviceability
        restCourier_pincode_serviceabilityMockMvc.perform(delete("/api/courier_pincode_serviceabilitys/{id}", courier_pincode_serviceability.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Courier_pincode_serviceability> courier_pincode_serviceabilitys = courier_pincode_serviceabilityRepository.findAll();
        assertThat(courier_pincode_serviceabilitys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
