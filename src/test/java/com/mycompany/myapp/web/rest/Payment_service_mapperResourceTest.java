package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Payment_service_mapper;
import com.mycompany.myapp.repository.Payment_service_mapperRepository;

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
 * Test class for the Payment_service_mapperResource REST controller.
 *
 * @see Payment_service_mapperResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Payment_service_mapperResourceTest {

    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";

    @Inject
    private Payment_service_mapperRepository payment_service_mapperRepository;

    private MockMvc restPayment_service_mapperMockMvc;

    private Payment_service_mapper payment_service_mapper;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Payment_service_mapperResource payment_service_mapperResource = new Payment_service_mapperResource();
        ReflectionTestUtils.setField(payment_service_mapperResource, "payment_service_mapperRepository", payment_service_mapperRepository);
        this.restPayment_service_mapperMockMvc = MockMvcBuilders.standaloneSetup(payment_service_mapperResource).build();
    }

    @Before
    public void initTest() {
        payment_service_mapper = new Payment_service_mapper();
        payment_service_mapper.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPayment_service_mapper() throws Exception {
        int databaseSizeBeforeCreate = payment_service_mapperRepository.findAll().size();

        // Create the Payment_service_mapper
        restPayment_service_mapperMockMvc.perform(post("/api/payment_service_mappers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(payment_service_mapper)))
                .andExpect(status().isCreated());

        // Validate the Payment_service_mapper in the database
        List<Payment_service_mapper> payment_service_mappers = payment_service_mapperRepository.findAll();
        assertThat(payment_service_mappers).hasSize(databaseSizeBeforeCreate + 1);
        Payment_service_mapper testPayment_service_mapper = payment_service_mappers.get(payment_service_mappers.size() - 1);
        assertThat(testPayment_service_mapper.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPayment_service_mappers() throws Exception {
        // Initialize the database
        payment_service_mapperRepository.saveAndFlush(payment_service_mapper);

        // Get all the payment_service_mappers
        restPayment_service_mapperMockMvc.perform(get("/api/payment_service_mappers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(payment_service_mapper.getId().intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getPayment_service_mapper() throws Exception {
        // Initialize the database
        payment_service_mapperRepository.saveAndFlush(payment_service_mapper);

        // Get the payment_service_mapper
        restPayment_service_mapperMockMvc.perform(get("/api/payment_service_mappers/{id}", payment_service_mapper.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(payment_service_mapper.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPayment_service_mapper() throws Exception {
        // Get the payment_service_mapper
        restPayment_service_mapperMockMvc.perform(get("/api/payment_service_mappers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayment_service_mapper() throws Exception {
        // Initialize the database
        payment_service_mapperRepository.saveAndFlush(payment_service_mapper);

		int databaseSizeBeforeUpdate = payment_service_mapperRepository.findAll().size();

        // Update the payment_service_mapper
        payment_service_mapper.setDescription(UPDATED_DESCRIPTION);
        restPayment_service_mapperMockMvc.perform(put("/api/payment_service_mappers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(payment_service_mapper)))
                .andExpect(status().isOk());

        // Validate the Payment_service_mapper in the database
        List<Payment_service_mapper> payment_service_mappers = payment_service_mapperRepository.findAll();
        assertThat(payment_service_mappers).hasSize(databaseSizeBeforeUpdate);
        Payment_service_mapper testPayment_service_mapper = payment_service_mappers.get(payment_service_mappers.size() - 1);
        assertThat(testPayment_service_mapper.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deletePayment_service_mapper() throws Exception {
        // Initialize the database
        payment_service_mapperRepository.saveAndFlush(payment_service_mapper);

		int databaseSizeBeforeDelete = payment_service_mapperRepository.findAll().size();

        // Get the payment_service_mapper
        restPayment_service_mapperMockMvc.perform(delete("/api/payment_service_mappers/{id}", payment_service_mapper.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Payment_service_mapper> payment_service_mappers = payment_service_mapperRepository.findAll();
        assertThat(payment_service_mappers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
