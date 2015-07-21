package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Master_baggg;
import com.mycompany.myapp.repository.Master_bagggRepository;

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
 * Test class for the Master_bagggResource REST controller.
 *
 * @see Master_bagggResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Master_bagggResourceTest {

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
    private Master_bagggRepository master_bagggRepository;

    private MockMvc restMaster_bagggMockMvc;

    private Master_baggg master_baggg;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Master_bagggResource master_bagggResource = new Master_bagggResource();
        ReflectionTestUtils.setField(master_bagggResource, "master_bagggRepository", master_bagggRepository);
        this.restMaster_bagggMockMvc = MockMvcBuilders.standaloneSetup(master_bagggResource).build();
    }

    @Before
    public void initTest() {
        master_baggg = new Master_baggg();
        master_baggg.setCode(DEFAULT_CODE);
        master_baggg.setCreationTime(DEFAULT_CREATION_TIME);
        master_baggg.setHandoverTime(DEFAULT_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void createMaster_baggg() throws Exception {
        int databaseSizeBeforeCreate = master_bagggRepository.findAll().size();

        // Create the Master_baggg
        restMaster_bagggMockMvc.perform(post("/api/master_bagggs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(master_baggg)))
                .andExpect(status().isCreated());

        // Validate the Master_baggg in the database
        List<Master_baggg> master_bagggs = master_bagggRepository.findAll();
        assertThat(master_bagggs).hasSize(databaseSizeBeforeCreate + 1);
        Master_baggg testMaster_baggg = master_bagggs.get(master_bagggs.size() - 1);
        assertThat(testMaster_baggg.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMaster_baggg.getCreationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_CREATION_TIME);
        assertThat(testMaster_baggg.getHandoverTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void getAllMaster_bagggs() throws Exception {
        // Initialize the database
        master_bagggRepository.saveAndFlush(master_baggg);

        // Get all the master_bagggs
        restMaster_bagggMockMvc.perform(get("/api/master_bagggs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(master_baggg.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].creationTime").value(hasItem(DEFAULT_CREATION_TIME_STR)))
                .andExpect(jsonPath("$.[*].handoverTime").value(hasItem(DEFAULT_HANDOVER_TIME_STR)));
    }

    @Test
    @Transactional
    public void getMaster_baggg() throws Exception {
        // Initialize the database
        master_bagggRepository.saveAndFlush(master_baggg);

        // Get the master_baggg
        restMaster_bagggMockMvc.perform(get("/api/master_bagggs/{id}", master_baggg.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(master_baggg.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.creationTime").value(DEFAULT_CREATION_TIME_STR))
            .andExpect(jsonPath("$.handoverTime").value(DEFAULT_HANDOVER_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingMaster_baggg() throws Exception {
        // Get the master_baggg
        restMaster_bagggMockMvc.perform(get("/api/master_bagggs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaster_baggg() throws Exception {
        // Initialize the database
        master_bagggRepository.saveAndFlush(master_baggg);

		int databaseSizeBeforeUpdate = master_bagggRepository.findAll().size();

        // Update the master_baggg
        master_baggg.setCode(UPDATED_CODE);
        master_baggg.setCreationTime(UPDATED_CREATION_TIME);
        master_baggg.setHandoverTime(UPDATED_HANDOVER_TIME);
        restMaster_bagggMockMvc.perform(put("/api/master_bagggs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(master_baggg)))
                .andExpect(status().isOk());

        // Validate the Master_baggg in the database
        List<Master_baggg> master_bagggs = master_bagggRepository.findAll();
        assertThat(master_bagggs).hasSize(databaseSizeBeforeUpdate);
        Master_baggg testMaster_baggg = master_bagggs.get(master_bagggs.size() - 1);
        assertThat(testMaster_baggg.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMaster_baggg.getCreationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_CREATION_TIME);
        assertThat(testMaster_baggg.getHandoverTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void deleteMaster_baggg() throws Exception {
        // Initialize the database
        master_bagggRepository.saveAndFlush(master_baggg);

		int databaseSizeBeforeDelete = master_bagggRepository.findAll().size();

        // Get the master_baggg
        restMaster_bagggMockMvc.perform(delete("/api/master_bagggs/{id}", master_baggg.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Master_baggg> master_bagggs = master_bagggRepository.findAll();
        assertThat(master_bagggs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
