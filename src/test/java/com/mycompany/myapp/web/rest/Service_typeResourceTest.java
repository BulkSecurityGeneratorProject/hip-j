package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Service_type;
import com.mycompany.myapp.repository.Service_typeRepository;

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
 * Test class for the Service_typeResource REST controller.
 *
 * @see Service_typeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Service_typeResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";

    @Inject
    private Service_typeRepository service_typeRepository;

    private MockMvc restService_typeMockMvc;

    private Service_type service_type;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Service_typeResource service_typeResource = new Service_typeResource();
        ReflectionTestUtils.setField(service_typeResource, "service_typeRepository", service_typeRepository);
        this.restService_typeMockMvc = MockMvcBuilders.standaloneSetup(service_typeResource).build();
    }

    @Before
    public void initTest() {
        service_type = new Service_type();
        service_type.setName(DEFAULT_NAME);
        service_type.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createService_type() throws Exception {
        int databaseSizeBeforeCreate = service_typeRepository.findAll().size();

        // Create the Service_type
        restService_typeMockMvc.perform(post("/api/service_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(service_type)))
                .andExpect(status().isCreated());

        // Validate the Service_type in the database
        List<Service_type> service_types = service_typeRepository.findAll();
        assertThat(service_types).hasSize(databaseSizeBeforeCreate + 1);
        Service_type testService_type = service_types.get(service_types.size() - 1);
        assertThat(testService_type.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testService_type.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(service_typeRepository.findAll()).hasSize(0);
        // set the field null
        service_type.setName(null);

        // Create the Service_type, which fails.
        restService_typeMockMvc.perform(post("/api/service_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(service_type)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Service_type> service_types = service_typeRepository.findAll();
        assertThat(service_types).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllService_types() throws Exception {
        // Initialize the database
        service_typeRepository.saveAndFlush(service_type);

        // Get all the service_types
        restService_typeMockMvc.perform(get("/api/service_types"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(service_type.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getService_type() throws Exception {
        // Initialize the database
        service_typeRepository.saveAndFlush(service_type);

        // Get the service_type
        restService_typeMockMvc.perform(get("/api/service_types/{id}", service_type.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(service_type.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingService_type() throws Exception {
        // Get the service_type
        restService_typeMockMvc.perform(get("/api/service_types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateService_type() throws Exception {
        // Initialize the database
        service_typeRepository.saveAndFlush(service_type);

		int databaseSizeBeforeUpdate = service_typeRepository.findAll().size();

        // Update the service_type
        service_type.setName(UPDATED_NAME);
        service_type.setDescription(UPDATED_DESCRIPTION);
        restService_typeMockMvc.perform(put("/api/service_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(service_type)))
                .andExpect(status().isOk());

        // Validate the Service_type in the database
        List<Service_type> service_types = service_typeRepository.findAll();
        assertThat(service_types).hasSize(databaseSizeBeforeUpdate);
        Service_type testService_type = service_types.get(service_types.size() - 1);
        assertThat(testService_type.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testService_type.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteService_type() throws Exception {
        // Initialize the database
        service_typeRepository.saveAndFlush(service_type);

		int databaseSizeBeforeDelete = service_typeRepository.findAll().size();

        // Get the service_type
        restService_typeMockMvc.perform(delete("/api/service_types/{id}", service_type.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Service_type> service_types = service_typeRepository.findAll();
        assertThat(service_types).hasSize(databaseSizeBeforeDelete - 1);
    }
}
