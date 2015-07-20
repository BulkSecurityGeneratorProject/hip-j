package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Courier;
import com.mycompany.myapp.repository.CourierRepository;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CourierResource REST controller.
 *
 * @see CourierResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CourierResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";

    private static final Integer DEFAULT_DAILY_CAPACITY = 0;
    private static final Integer UPDATED_DAILY_CAPACITY = 1;
    private static final String DEFAULT_COLOR_CODE = "SAMPLE_TEXT";
    private static final String UPDATED_COLOR_CODE = "UPDATED_TEXT";

    private static final Boolean DEFAULT_IS_ENABLED = false;
    private static final Boolean UPDATED_IS_ENABLED = true;

    @Inject
    private CourierRepository courierRepository;

    private MockMvc restCourierMockMvc;

    private Courier courier;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CourierResource courierResource = new CourierResource();
        ReflectionTestUtils.setField(courierResource, "courierRepository", courierRepository);
        this.restCourierMockMvc = MockMvcBuilders.standaloneSetup(courierResource).build();
    }

    @Before
    public void initTest() {
        courier = new Courier();
        courier.setName(DEFAULT_NAME);
        courier.setDaily_capacity(DEFAULT_DAILY_CAPACITY);
        courier.setColor_code(DEFAULT_COLOR_CODE);
        courier.setIs_enabled(DEFAULT_IS_ENABLED);
    }

    @Test
    @Transactional
    public void createCourier() throws Exception {
        int databaseSizeBeforeCreate = courierRepository.findAll().size();

        // Create the Courier
        restCourierMockMvc.perform(post("/api/couriers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(courier)))
                .andExpect(status().isCreated());

        // Validate the Courier in the database
        List<Courier> couriers = courierRepository.findAll();
        assertThat(couriers).hasSize(databaseSizeBeforeCreate + 1);
        Courier testCourier = couriers.get(couriers.size() - 1);
        assertThat(testCourier.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCourier.getDaily_capacity()).isEqualTo(DEFAULT_DAILY_CAPACITY);
        assertThat(testCourier.getColor_code()).isEqualTo(DEFAULT_COLOR_CODE);
        assertThat(testCourier.getIs_enabled()).isEqualTo(DEFAULT_IS_ENABLED);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(courierRepository.findAll()).hasSize(0);
        // set the field null
        courier.setName(null);

        // Create the Courier, which fails.
        restCourierMockMvc.perform(post("/api/couriers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(courier)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Courier> couriers = courierRepository.findAll();
        assertThat(couriers).hasSize(0);
    }

    @Test
    @Transactional
    public void checkDaily_capacityIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(courierRepository.findAll()).hasSize(0);
        // set the field null
        courier.setDaily_capacity(null);

        // Create the Courier, which fails.
        restCourierMockMvc.perform(post("/api/couriers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(courier)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Courier> couriers = courierRepository.findAll();
        assertThat(couriers).hasSize(0);
    }

    @Test
    @Transactional
    public void checkColor_codeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(courierRepository.findAll()).hasSize(0);
        // set the field null
        courier.setColor_code(null);

        // Create the Courier, which fails.
        restCourierMockMvc.perform(post("/api/couriers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(courier)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Courier> couriers = courierRepository.findAll();
        assertThat(couriers).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllCouriers() throws Exception {
        // Initialize the database
        courierRepository.saveAndFlush(courier);

        // Get all the couriers
        restCourierMockMvc.perform(get("/api/couriers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(courier.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].daily_capacity").value(hasItem(DEFAULT_DAILY_CAPACITY)))
                .andExpect(jsonPath("$.[*].color_code").value(hasItem(DEFAULT_COLOR_CODE.toString())))
                .andExpect(jsonPath("$.[*].is_enabled").value(hasItem(DEFAULT_IS_ENABLED.booleanValue())));
    }

    @Test
    @Transactional
    public void getCourier() throws Exception {
        // Initialize the database
        courierRepository.saveAndFlush(courier);

        // Get the courier
        restCourierMockMvc.perform(get("/api/couriers/{id}", courier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(courier.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.daily_capacity").value(DEFAULT_DAILY_CAPACITY))
            .andExpect(jsonPath("$.color_code").value(DEFAULT_COLOR_CODE.toString()))
            .andExpect(jsonPath("$.is_enabled").value(DEFAULT_IS_ENABLED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCourier() throws Exception {
        // Get the courier
        restCourierMockMvc.perform(get("/api/couriers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourier() throws Exception {
        // Initialize the database
        courierRepository.saveAndFlush(courier);

		int databaseSizeBeforeUpdate = courierRepository.findAll().size();

        // Update the courier
        courier.setName(UPDATED_NAME);
        courier.setDaily_capacity(UPDATED_DAILY_CAPACITY);
        courier.setColor_code(UPDATED_COLOR_CODE);
        courier.setIs_enabled(UPDATED_IS_ENABLED);
        restCourierMockMvc.perform(put("/api/couriers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(courier)))
                .andExpect(status().isOk());

        // Validate the Courier in the database
        List<Courier> couriers = courierRepository.findAll();
        assertThat(couriers).hasSize(databaseSizeBeforeUpdate);
        Courier testCourier = couriers.get(couriers.size() - 1);
        assertThat(testCourier.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCourier.getDaily_capacity()).isEqualTo(UPDATED_DAILY_CAPACITY);
        assertThat(testCourier.getColor_code()).isEqualTo(UPDATED_COLOR_CODE);
        assertThat(testCourier.getIs_enabled()).isEqualTo(UPDATED_IS_ENABLED);
    }

    @Test
    @Transactional
    public void deleteCourier() throws Exception {
        // Initialize the database
        courierRepository.saveAndFlush(courier);

		int databaseSizeBeforeDelete = courierRepository.findAll().size();

        // Get the courier
        restCourierMockMvc.perform(delete("/api/couriers/{id}", courier.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Courier> couriers = courierRepository.findAll();
        assertThat(couriers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
