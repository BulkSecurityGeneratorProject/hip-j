package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Master_chef;
import com.mycompany.myapp.repository.Master_chefRepository;

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
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the Master_chefResource REST controller.
 *
 * @see Master_chefResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Master_chefResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_CODE = "SAMPLE_TEXT";
    private static final String UPDATED_CODE = "UPDATED_TEXT";

    private static final DateTime DEFAULT_CREATION_TIME = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_CREATION_TIME = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_CREATION_TIME_STR = dateTimeFormatter.print(DEFAULT_CREATION_TIME);

    private static final DateTime DEFAULT_HANDOVER_TIME = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_HANDOVER_TIME = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_HANDOVER_TIME_STR = dateTimeFormatter.print(DEFAULT_HANDOVER_TIME);

    @Inject
    private Master_chefRepository master_chefRepository;

    private MockMvc restMaster_chefMockMvc;

    private Master_chef master_chef;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Master_chefResource master_chefResource = new Master_chefResource();
        ReflectionTestUtils.setField(master_chefResource, "master_chefRepository", master_chefRepository);
        this.restMaster_chefMockMvc = MockMvcBuilders.standaloneSetup(master_chefResource).build();
    }

    @Before
    public void initTest() {
        master_chef = new Master_chef();
        master_chef.setCode(DEFAULT_CODE);
        master_chef.setCreationTime(DEFAULT_CREATION_TIME);
        master_chef.setHandoverTime(DEFAULT_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void createMaster_chef() throws Exception {
        int databaseSizeBeforeCreate = master_chefRepository.findAll().size();

        // Create the Master_chef
        restMaster_chefMockMvc.perform(post("/api/master_chefs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(master_chef)))
                .andExpect(status().isCreated());

        // Validate the Master_chef in the database
        List<Master_chef> master_chefs = master_chefRepository.findAll();
        assertThat(master_chefs).hasSize(databaseSizeBeforeCreate + 1);
        Master_chef testMaster_chef = master_chefs.get(master_chefs.size() - 1);
        assertThat(testMaster_chef.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMaster_chef.getCreationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_CREATION_TIME);
        assertThat(testMaster_chef.getHandoverTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void getAllMaster_chefs() throws Exception {
        // Initialize the database
        master_chefRepository.saveAndFlush(master_chef);

        // Get all the master_chefs
        restMaster_chefMockMvc.perform(get("/api/master_chefs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(master_chef.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].creationTime").value(hasItem(DEFAULT_CREATION_TIME_STR)))
                .andExpect(jsonPath("$.[*].handoverTime").value(hasItem(DEFAULT_HANDOVER_TIME_STR)));
    }

    @Test
    @Transactional
    public void getMaster_chef() throws Exception {
        // Initialize the database
        master_chefRepository.saveAndFlush(master_chef);

        // Get the master_chef
        restMaster_chefMockMvc.perform(get("/api/master_chefs/{id}", master_chef.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(master_chef.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.creationTime").value(DEFAULT_CREATION_TIME_STR))
            .andExpect(jsonPath("$.handoverTime").value(DEFAULT_HANDOVER_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingMaster_chef() throws Exception {
        // Get the master_chef
        restMaster_chefMockMvc.perform(get("/api/master_chefs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaster_chef() throws Exception {
        // Initialize the database
        master_chefRepository.saveAndFlush(master_chef);

		int databaseSizeBeforeUpdate = master_chefRepository.findAll().size();

        // Update the master_chef
        master_chef.setCode(UPDATED_CODE);
        master_chef.setCreationTime(UPDATED_CREATION_TIME);
        master_chef.setHandoverTime(UPDATED_HANDOVER_TIME);
        restMaster_chefMockMvc.perform(put("/api/master_chefs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(master_chef)))
                .andExpect(status().isOk());

        // Validate the Master_chef in the database
        List<Master_chef> master_chefs = master_chefRepository.findAll();
        assertThat(master_chefs).hasSize(databaseSizeBeforeUpdate);
        Master_chef testMaster_chef = master_chefs.get(master_chefs.size() - 1);
        assertThat(testMaster_chef.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMaster_chef.getCreationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_CREATION_TIME);
        assertThat(testMaster_chef.getHandoverTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void deleteMaster_chef() throws Exception {
        // Initialize the database
        master_chefRepository.saveAndFlush(master_chef);

		int databaseSizeBeforeDelete = master_chefRepository.findAll().size();

        // Get the master_chef
        restMaster_chefMockMvc.perform(delete("/api/master_chefs/{id}", master_chef.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Master_chef> master_chefs = master_chefRepository.findAll();
        assertThat(master_chefs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
