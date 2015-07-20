package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Payment_type;
import com.mycompany.myapp.repository.Payment_typeRepository;

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
 * Test class for the Payment_typeResource REST controller.
 *
 * @see Payment_typeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Payment_typeResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";

    @Inject
    private Payment_typeRepository payment_typeRepository;

    private MockMvc restPayment_typeMockMvc;

    private Payment_type payment_type;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Payment_typeResource payment_typeResource = new Payment_typeResource();
        ReflectionTestUtils.setField(payment_typeResource, "payment_typeRepository", payment_typeRepository);
        this.restPayment_typeMockMvc = MockMvcBuilders.standaloneSetup(payment_typeResource).build();
    }

    @Before
    public void initTest() {
        payment_type = new Payment_type();
        payment_type.setName(DEFAULT_NAME);
        payment_type.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPayment_type() throws Exception {
        int databaseSizeBeforeCreate = payment_typeRepository.findAll().size();

        // Create the Payment_type
        restPayment_typeMockMvc.perform(post("/api/payment_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(payment_type)))
                .andExpect(status().isCreated());

        // Validate the Payment_type in the database
        List<Payment_type> payment_types = payment_typeRepository.findAll();
        assertThat(payment_types).hasSize(databaseSizeBeforeCreate + 1);
        Payment_type testPayment_type = payment_types.get(payment_types.size() - 1);
        assertThat(testPayment_type.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPayment_type.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(payment_typeRepository.findAll()).hasSize(0);
        // set the field null
        payment_type.setName(null);

        // Create the Payment_type, which fails.
        restPayment_typeMockMvc.perform(post("/api/payment_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(payment_type)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Payment_type> payment_types = payment_typeRepository.findAll();
        assertThat(payment_types).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllPayment_types() throws Exception {
        // Initialize the database
        payment_typeRepository.saveAndFlush(payment_type);

        // Get all the payment_types
        restPayment_typeMockMvc.perform(get("/api/payment_types"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(payment_type.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getPayment_type() throws Exception {
        // Initialize the database
        payment_typeRepository.saveAndFlush(payment_type);

        // Get the payment_type
        restPayment_typeMockMvc.perform(get("/api/payment_types/{id}", payment_type.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(payment_type.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPayment_type() throws Exception {
        // Get the payment_type
        restPayment_typeMockMvc.perform(get("/api/payment_types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayment_type() throws Exception {
        // Initialize the database
        payment_typeRepository.saveAndFlush(payment_type);

		int databaseSizeBeforeUpdate = payment_typeRepository.findAll().size();

        // Update the payment_type
        payment_type.setName(UPDATED_NAME);
        payment_type.setDescription(UPDATED_DESCRIPTION);
        restPayment_typeMockMvc.perform(put("/api/payment_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(payment_type)))
                .andExpect(status().isOk());

        // Validate the Payment_type in the database
        List<Payment_type> payment_types = payment_typeRepository.findAll();
        assertThat(payment_types).hasSize(databaseSizeBeforeUpdate);
        Payment_type testPayment_type = payment_types.get(payment_types.size() - 1);
        assertThat(testPayment_type.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPayment_type.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deletePayment_type() throws Exception {
        // Initialize the database
        payment_typeRepository.saveAndFlush(payment_type);

		int databaseSizeBeforeDelete = payment_typeRepository.findAll().size();

        // Get the payment_type
        restPayment_typeMockMvc.perform(delete("/api/payment_types/{id}", payment_type.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Payment_type> payment_types = payment_typeRepository.findAll();
        assertThat(payment_types).hasSize(databaseSizeBeforeDelete - 1);
    }
}
