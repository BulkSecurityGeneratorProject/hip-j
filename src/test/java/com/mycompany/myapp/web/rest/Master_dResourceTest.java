package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Master_d;
import com.mycompany.myapp.repository.Master_dRepository;

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
 * Test class for the Master_dResource REST controller.
 *
 * @see Master_dResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Master_dResourceTest {

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
    private Master_dRepository master_dRepository;

    private MockMvc restMaster_dMockMvc;

    private Master_d master_d;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Master_dResource master_dResource = new Master_dResource();
        ReflectionTestUtils.setField(master_dResource, "master_dRepository", master_dRepository);
        this.restMaster_dMockMvc = MockMvcBuilders.standaloneSetup(master_dResource).build();
    }

    @Before
    public void initTest() {
        master_d = new Master_d();
        master_d.setCode(DEFAULT_CODE);
        master_d.setCreationTime(DEFAULT_CREATION_TIME);
        master_d.setHandoverTime(DEFAULT_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void createMaster_d() throws Exception {
        int databaseSizeBeforeCreate = master_dRepository.findAll().size();

        // Create the Master_d
        restMaster_dMockMvc.perform(post("/api/master_ds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(master_d)))
                .andExpect(status().isCreated());

        // Validate the Master_d in the database
        List<Master_d> master_ds = master_dRepository.findAll();
        assertThat(master_ds).hasSize(databaseSizeBeforeCreate + 1);
        Master_d testMaster_d = master_ds.get(master_ds.size() - 1);
        assertThat(testMaster_d.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMaster_d.getCreationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_CREATION_TIME);
        assertThat(testMaster_d.getHandoverTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void getAllMaster_ds() throws Exception {
        // Initialize the database
        master_dRepository.saveAndFlush(master_d);

        // Get all the master_ds
        restMaster_dMockMvc.perform(get("/api/master_ds"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(master_d.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].creationTime").value(hasItem(DEFAULT_CREATION_TIME_STR)))
                .andExpect(jsonPath("$.[*].handoverTime").value(hasItem(DEFAULT_HANDOVER_TIME_STR)));
    }

    @Test
    @Transactional
    public void getMaster_d() throws Exception {
        // Initialize the database
        master_dRepository.saveAndFlush(master_d);

        // Get the master_d
        restMaster_dMockMvc.perform(get("/api/master_ds/{id}", master_d.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(master_d.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.creationTime").value(DEFAULT_CREATION_TIME_STR))
            .andExpect(jsonPath("$.handoverTime").value(DEFAULT_HANDOVER_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingMaster_d() throws Exception {
        // Get the master_d
        restMaster_dMockMvc.perform(get("/api/master_ds/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaster_d() throws Exception {
        // Initialize the database
        master_dRepository.saveAndFlush(master_d);

		int databaseSizeBeforeUpdate = master_dRepository.findAll().size();

        // Update the master_d
        master_d.setCode(UPDATED_CODE);
        master_d.setCreationTime(UPDATED_CREATION_TIME);
        master_d.setHandoverTime(UPDATED_HANDOVER_TIME);
        restMaster_dMockMvc.perform(put("/api/master_ds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(master_d)))
                .andExpect(status().isOk());

        // Validate the Master_d in the database
        List<Master_d> master_ds = master_dRepository.findAll();
        assertThat(master_ds).hasSize(databaseSizeBeforeUpdate);
        Master_d testMaster_d = master_ds.get(master_ds.size() - 1);
        assertThat(testMaster_d.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMaster_d.getCreationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_CREATION_TIME);
        assertThat(testMaster_d.getHandoverTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void deleteMaster_d() throws Exception {
        // Initialize the database
        master_dRepository.saveAndFlush(master_d);

		int databaseSizeBeforeDelete = master_dRepository.findAll().size();

        // Get the master_d
        restMaster_dMockMvc.perform(delete("/api/master_ds/{id}", master_d.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Master_d> master_ds = master_dRepository.findAll();
        assertThat(master_ds).hasSize(databaseSizeBeforeDelete - 1);
    }
}
