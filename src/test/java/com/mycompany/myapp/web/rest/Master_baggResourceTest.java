package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Master_bagg;
import com.mycompany.myapp.repository.Master_baggRepository;

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
 * Test class for the Master_baggResource REST controller.
 *
 * @see Master_baggResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Master_baggResourceTest {

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
    private Master_baggRepository master_baggRepository;

    private MockMvc restMaster_baggMockMvc;

    private Master_bagg master_bagg;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Master_baggResource master_baggResource = new Master_baggResource();
        ReflectionTestUtils.setField(master_baggResource, "master_baggRepository", master_baggRepository);
        this.restMaster_baggMockMvc = MockMvcBuilders.standaloneSetup(master_baggResource).build();
    }

    @Before
    public void initTest() {
        master_bagg = new Master_bagg();
        master_bagg.setCode(DEFAULT_CODE);
        master_bagg.setCreationTime(DEFAULT_CREATION_TIME);
        master_bagg.setHandoverTime(DEFAULT_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void createMaster_bagg() throws Exception {
        int databaseSizeBeforeCreate = master_baggRepository.findAll().size();

        // Create the Master_bagg
        restMaster_baggMockMvc.perform(post("/api/master_baggs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(master_bagg)))
                .andExpect(status().isCreated());

        // Validate the Master_bagg in the database
        List<Master_bagg> master_baggs = master_baggRepository.findAll();
        assertThat(master_baggs).hasSize(databaseSizeBeforeCreate + 1);
        Master_bagg testMaster_bagg = master_baggs.get(master_baggs.size() - 1);
        assertThat(testMaster_bagg.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMaster_bagg.getCreationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_CREATION_TIME);
        assertThat(testMaster_bagg.getHandoverTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void getAllMaster_baggs() throws Exception {
        // Initialize the database
        master_baggRepository.saveAndFlush(master_bagg);

        // Get all the master_baggs
        restMaster_baggMockMvc.perform(get("/api/master_baggs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(master_bagg.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].creationTime").value(hasItem(DEFAULT_CREATION_TIME_STR)))
                .andExpect(jsonPath("$.[*].handoverTime").value(hasItem(DEFAULT_HANDOVER_TIME_STR)));
    }

    @Test
    @Transactional
    public void getMaster_bagg() throws Exception {
        // Initialize the database
        master_baggRepository.saveAndFlush(master_bagg);

        // Get the master_bagg
        restMaster_baggMockMvc.perform(get("/api/master_baggs/{id}", master_bagg.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(master_bagg.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.creationTime").value(DEFAULT_CREATION_TIME_STR))
            .andExpect(jsonPath("$.handoverTime").value(DEFAULT_HANDOVER_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingMaster_bagg() throws Exception {
        // Get the master_bagg
        restMaster_baggMockMvc.perform(get("/api/master_baggs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaster_bagg() throws Exception {
        // Initialize the database
        master_baggRepository.saveAndFlush(master_bagg);

		int databaseSizeBeforeUpdate = master_baggRepository.findAll().size();

        // Update the master_bagg
        master_bagg.setCode(UPDATED_CODE);
        master_bagg.setCreationTime(UPDATED_CREATION_TIME);
        master_bagg.setHandoverTime(UPDATED_HANDOVER_TIME);
        restMaster_baggMockMvc.perform(put("/api/master_baggs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(master_bagg)))
                .andExpect(status().isOk());

        // Validate the Master_bagg in the database
        List<Master_bagg> master_baggs = master_baggRepository.findAll();
        assertThat(master_baggs).hasSize(databaseSizeBeforeUpdate);
        Master_bagg testMaster_bagg = master_baggs.get(master_baggs.size() - 1);
        assertThat(testMaster_bagg.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMaster_bagg.getCreationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_CREATION_TIME);
        assertThat(testMaster_bagg.getHandoverTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void deleteMaster_bagg() throws Exception {
        // Initialize the database
        master_baggRepository.saveAndFlush(master_bagg);

		int databaseSizeBeforeDelete = master_baggRepository.findAll().size();

        // Get the master_bagg
        restMaster_baggMockMvc.perform(delete("/api/master_baggs/{id}", master_bagg.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Master_bagg> master_baggs = master_baggRepository.findAll();
        assertThat(master_baggs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
