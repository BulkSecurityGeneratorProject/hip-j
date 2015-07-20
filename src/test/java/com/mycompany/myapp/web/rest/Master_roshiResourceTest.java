package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Master_roshi;
import com.mycompany.myapp.repository.Master_roshiRepository;

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
 * Test class for the Master_roshiResource REST controller.
 *
 * @see Master_roshiResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Master_roshiResourceTest {

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
    private Master_roshiRepository master_roshiRepository;

    private MockMvc restMaster_roshiMockMvc;

    private Master_roshi master_roshi;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Master_roshiResource master_roshiResource = new Master_roshiResource();
        ReflectionTestUtils.setField(master_roshiResource, "master_roshiRepository", master_roshiRepository);
        this.restMaster_roshiMockMvc = MockMvcBuilders.standaloneSetup(master_roshiResource).build();
    }

    @Before
    public void initTest() {
        master_roshi = new Master_roshi();
        master_roshi.setCode(DEFAULT_CODE);
        master_roshi.setCreationTime(DEFAULT_CREATION_TIME);
        master_roshi.setHandoverTime(DEFAULT_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void createMaster_roshi() throws Exception {
        int databaseSizeBeforeCreate = master_roshiRepository.findAll().size();

        // Create the Master_roshi
        restMaster_roshiMockMvc.perform(post("/api/master_roshis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(master_roshi)))
                .andExpect(status().isCreated());

        // Validate the Master_roshi in the database
        List<Master_roshi> master_roshis = master_roshiRepository.findAll();
        assertThat(master_roshis).hasSize(databaseSizeBeforeCreate + 1);
        Master_roshi testMaster_roshi = master_roshis.get(master_roshis.size() - 1);
        assertThat(testMaster_roshi.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMaster_roshi.getCreationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_CREATION_TIME);
        assertThat(testMaster_roshi.getHandoverTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void getAllMaster_roshis() throws Exception {
        // Initialize the database
        master_roshiRepository.saveAndFlush(master_roshi);

        // Get all the master_roshis
        restMaster_roshiMockMvc.perform(get("/api/master_roshis"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(master_roshi.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].creationTime").value(hasItem(DEFAULT_CREATION_TIME_STR)))
                .andExpect(jsonPath("$.[*].handoverTime").value(hasItem(DEFAULT_HANDOVER_TIME_STR)));
    }

    @Test
    @Transactional
    public void getMaster_roshi() throws Exception {
        // Initialize the database
        master_roshiRepository.saveAndFlush(master_roshi);

        // Get the master_roshi
        restMaster_roshiMockMvc.perform(get("/api/master_roshis/{id}", master_roshi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(master_roshi.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.creationTime").value(DEFAULT_CREATION_TIME_STR))
            .andExpect(jsonPath("$.handoverTime").value(DEFAULT_HANDOVER_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingMaster_roshi() throws Exception {
        // Get the master_roshi
        restMaster_roshiMockMvc.perform(get("/api/master_roshis/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaster_roshi() throws Exception {
        // Initialize the database
        master_roshiRepository.saveAndFlush(master_roshi);

		int databaseSizeBeforeUpdate = master_roshiRepository.findAll().size();

        // Update the master_roshi
        master_roshi.setCode(UPDATED_CODE);
        master_roshi.setCreationTime(UPDATED_CREATION_TIME);
        master_roshi.setHandoverTime(UPDATED_HANDOVER_TIME);
        restMaster_roshiMockMvc.perform(put("/api/master_roshis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(master_roshi)))
                .andExpect(status().isOk());

        // Validate the Master_roshi in the database
        List<Master_roshi> master_roshis = master_roshiRepository.findAll();
        assertThat(master_roshis).hasSize(databaseSizeBeforeUpdate);
        Master_roshi testMaster_roshi = master_roshis.get(master_roshis.size() - 1);
        assertThat(testMaster_roshi.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMaster_roshi.getCreationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_CREATION_TIME);
        assertThat(testMaster_roshi.getHandoverTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void deleteMaster_roshi() throws Exception {
        // Initialize the database
        master_roshiRepository.saveAndFlush(master_roshi);

		int databaseSizeBeforeDelete = master_roshiRepository.findAll().size();

        // Get the master_roshi
        restMaster_roshiMockMvc.perform(delete("/api/master_roshis/{id}", master_roshi.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Master_roshi> master_roshis = master_roshiRepository.findAll();
        assertThat(master_roshis).hasSize(databaseSizeBeforeDelete - 1);
    }
}
