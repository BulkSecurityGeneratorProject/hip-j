package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Master_bagggg;
import com.mycompany.myapp.repository.Master_baggggRepository;

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
 * Test class for the Master_baggggResource REST controller.
 *
 * @see Master_baggggResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Master_baggggResourceTest {

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
    private Master_baggggRepository master_baggggRepository;

    private MockMvc restMaster_baggggMockMvc;

    private Master_bagggg master_bagggg;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Master_baggggResource master_baggggResource = new Master_baggggResource();
        ReflectionTestUtils.setField(master_baggggResource, "master_baggggRepository", master_baggggRepository);
        this.restMaster_baggggMockMvc = MockMvcBuilders.standaloneSetup(master_baggggResource).build();
    }

    @Before
    public void initTest() {
        master_bagggg = new Master_bagggg();
        master_bagggg.setCode(DEFAULT_CODE);
        master_bagggg.setCreationTime(DEFAULT_CREATION_TIME);
        master_bagggg.setHandoverTime(DEFAULT_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void createMaster_bagggg() throws Exception {
        int databaseSizeBeforeCreate = master_baggggRepository.findAll().size();

        // Create the Master_bagggg
        restMaster_baggggMockMvc.perform(post("/api/master_baggggs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(master_bagggg)))
                .andExpect(status().isCreated());

        // Validate the Master_bagggg in the database
        List<Master_bagggg> master_baggggs = master_baggggRepository.findAll();
        assertThat(master_baggggs).hasSize(databaseSizeBeforeCreate + 1);
        Master_bagggg testMaster_bagggg = master_baggggs.get(master_baggggs.size() - 1);
        assertThat(testMaster_bagggg.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMaster_bagggg.getCreationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_CREATION_TIME);
        assertThat(testMaster_bagggg.getHandoverTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void getAllMaster_baggggs() throws Exception {
        // Initialize the database
        master_baggggRepository.saveAndFlush(master_bagggg);

        // Get all the master_baggggs
        restMaster_baggggMockMvc.perform(get("/api/master_baggggs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(master_bagggg.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].creationTime").value(hasItem(DEFAULT_CREATION_TIME_STR)))
                .andExpect(jsonPath("$.[*].handoverTime").value(hasItem(DEFAULT_HANDOVER_TIME_STR)));
    }

    @Test
    @Transactional
    public void getMaster_bagggg() throws Exception {
        // Initialize the database
        master_baggggRepository.saveAndFlush(master_bagggg);

        // Get the master_bagggg
        restMaster_baggggMockMvc.perform(get("/api/master_baggggs/{id}", master_bagggg.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(master_bagggg.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.creationTime").value(DEFAULT_CREATION_TIME_STR))
            .andExpect(jsonPath("$.handoverTime").value(DEFAULT_HANDOVER_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingMaster_bagggg() throws Exception {
        // Get the master_bagggg
        restMaster_baggggMockMvc.perform(get("/api/master_baggggs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaster_bagggg() throws Exception {
        // Initialize the database
        master_baggggRepository.saveAndFlush(master_bagggg);

		int databaseSizeBeforeUpdate = master_baggggRepository.findAll().size();

        // Update the master_bagggg
        master_bagggg.setCode(UPDATED_CODE);
        master_bagggg.setCreationTime(UPDATED_CREATION_TIME);
        master_bagggg.setHandoverTime(UPDATED_HANDOVER_TIME);
        restMaster_baggggMockMvc.perform(put("/api/master_baggggs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(master_bagggg)))
                .andExpect(status().isOk());

        // Validate the Master_bagggg in the database
        List<Master_bagggg> master_baggggs = master_baggggRepository.findAll();
        assertThat(master_baggggs).hasSize(databaseSizeBeforeUpdate);
        Master_bagggg testMaster_bagggg = master_baggggs.get(master_baggggs.size() - 1);
        assertThat(testMaster_bagggg.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMaster_bagggg.getCreationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_CREATION_TIME);
        assertThat(testMaster_bagggg.getHandoverTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void deleteMaster_bagggg() throws Exception {
        // Initialize the database
        master_baggggRepository.saveAndFlush(master_bagggg);

		int databaseSizeBeforeDelete = master_baggggRepository.findAll().size();

        // Get the master_bagggg
        restMaster_baggggMockMvc.perform(delete("/api/master_baggggs/{id}", master_bagggg.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Master_bagggg> master_baggggs = master_baggggRepository.findAll();
        assertThat(master_baggggs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
