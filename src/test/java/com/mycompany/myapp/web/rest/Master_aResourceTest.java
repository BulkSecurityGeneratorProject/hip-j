package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Master_a;
import com.mycompany.myapp.repository.Master_aRepository;

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
 * Test class for the Master_aResource REST controller.
 *
 * @see Master_aResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Master_aResourceTest {

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
    private Master_aRepository master_aRepository;

    private MockMvc restMaster_aMockMvc;

    private Master_a master_a;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Master_aResource master_aResource = new Master_aResource();
        ReflectionTestUtils.setField(master_aResource, "master_aRepository", master_aRepository);
        this.restMaster_aMockMvc = MockMvcBuilders.standaloneSetup(master_aResource).build();
    }

    @Before
    public void initTest() {
        master_a = new Master_a();
        master_a.setCode(DEFAULT_CODE);
        master_a.setCreationTime(DEFAULT_CREATION_TIME);
        master_a.setHandoverTime(DEFAULT_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void createMaster_a() throws Exception {
        int databaseSizeBeforeCreate = master_aRepository.findAll().size();

        // Create the Master_a
        restMaster_aMockMvc.perform(post("/api/master_as")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(master_a)))
                .andExpect(status().isCreated());

        // Validate the Master_a in the database
        List<Master_a> master_as = master_aRepository.findAll();
        assertThat(master_as).hasSize(databaseSizeBeforeCreate + 1);
        Master_a testMaster_a = master_as.get(master_as.size() - 1);
        assertThat(testMaster_a.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMaster_a.getCreationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_CREATION_TIME);
        assertThat(testMaster_a.getHandoverTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void getAllMaster_as() throws Exception {
        // Initialize the database
        master_aRepository.saveAndFlush(master_a);

        // Get all the master_as
        restMaster_aMockMvc.perform(get("/api/master_as"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(master_a.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].creationTime").value(hasItem(DEFAULT_CREATION_TIME_STR)))
                .andExpect(jsonPath("$.[*].handoverTime").value(hasItem(DEFAULT_HANDOVER_TIME_STR)));
    }

    @Test
    @Transactional
    public void getMaster_a() throws Exception {
        // Initialize the database
        master_aRepository.saveAndFlush(master_a);

        // Get the master_a
        restMaster_aMockMvc.perform(get("/api/master_as/{id}", master_a.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(master_a.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.creationTime").value(DEFAULT_CREATION_TIME_STR))
            .andExpect(jsonPath("$.handoverTime").value(DEFAULT_HANDOVER_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingMaster_a() throws Exception {
        // Get the master_a
        restMaster_aMockMvc.perform(get("/api/master_as/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaster_a() throws Exception {
        // Initialize the database
        master_aRepository.saveAndFlush(master_a);

		int databaseSizeBeforeUpdate = master_aRepository.findAll().size();

        // Update the master_a
        master_a.setCode(UPDATED_CODE);
        master_a.setCreationTime(UPDATED_CREATION_TIME);
        master_a.setHandoverTime(UPDATED_HANDOVER_TIME);
        restMaster_aMockMvc.perform(put("/api/master_as")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(master_a)))
                .andExpect(status().isOk());

        // Validate the Master_a in the database
        List<Master_a> master_as = master_aRepository.findAll();
        assertThat(master_as).hasSize(databaseSizeBeforeUpdate);
        Master_a testMaster_a = master_as.get(master_as.size() - 1);
        assertThat(testMaster_a.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMaster_a.getCreationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_CREATION_TIME);
        assertThat(testMaster_a.getHandoverTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void deleteMaster_a() throws Exception {
        // Initialize the database
        master_aRepository.saveAndFlush(master_a);

		int databaseSizeBeforeDelete = master_aRepository.findAll().size();

        // Get the master_a
        restMaster_aMockMvc.perform(delete("/api/master_as/{id}", master_a.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Master_a> master_as = master_aRepository.findAll();
        assertThat(master_as).hasSize(databaseSizeBeforeDelete - 1);
    }
}
