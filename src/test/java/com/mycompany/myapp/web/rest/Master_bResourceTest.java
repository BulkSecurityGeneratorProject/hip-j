package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Master_b;
import com.mycompany.myapp.repository.Master_bRepository;

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
 * Test class for the Master_bResource REST controller.
 *
 * @see Master_bResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Master_bResourceTest {

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
    private Master_bRepository master_bRepository;

    private MockMvc restMaster_bMockMvc;

    private Master_b master_b;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Master_bResource master_bResource = new Master_bResource();
        ReflectionTestUtils.setField(master_bResource, "master_bRepository", master_bRepository);
        this.restMaster_bMockMvc = MockMvcBuilders.standaloneSetup(master_bResource).build();
    }

    @Before
    public void initTest() {
        master_b = new Master_b();
        master_b.setCode(DEFAULT_CODE);
        master_b.setCreationTime(DEFAULT_CREATION_TIME);
        master_b.setHandoverTime(DEFAULT_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void createMaster_b() throws Exception {
        int databaseSizeBeforeCreate = master_bRepository.findAll().size();

        // Create the Master_b
        restMaster_bMockMvc.perform(post("/api/master_bs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(master_b)))
                .andExpect(status().isCreated());

        // Validate the Master_b in the database
        List<Master_b> master_bs = master_bRepository.findAll();
        assertThat(master_bs).hasSize(databaseSizeBeforeCreate + 1);
        Master_b testMaster_b = master_bs.get(master_bs.size() - 1);
        assertThat(testMaster_b.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMaster_b.getCreationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_CREATION_TIME);
        assertThat(testMaster_b.getHandoverTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void getAllMaster_bs() throws Exception {
        // Initialize the database
        master_bRepository.saveAndFlush(master_b);

        // Get all the master_bs
        restMaster_bMockMvc.perform(get("/api/master_bs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(master_b.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].creationTime").value(hasItem(DEFAULT_CREATION_TIME_STR)))
                .andExpect(jsonPath("$.[*].handoverTime").value(hasItem(DEFAULT_HANDOVER_TIME_STR)));
    }

    @Test
    @Transactional
    public void getMaster_b() throws Exception {
        // Initialize the database
        master_bRepository.saveAndFlush(master_b);

        // Get the master_b
        restMaster_bMockMvc.perform(get("/api/master_bs/{id}", master_b.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(master_b.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.creationTime").value(DEFAULT_CREATION_TIME_STR))
            .andExpect(jsonPath("$.handoverTime").value(DEFAULT_HANDOVER_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingMaster_b() throws Exception {
        // Get the master_b
        restMaster_bMockMvc.perform(get("/api/master_bs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaster_b() throws Exception {
        // Initialize the database
        master_bRepository.saveAndFlush(master_b);

		int databaseSizeBeforeUpdate = master_bRepository.findAll().size();

        // Update the master_b
        master_b.setCode(UPDATED_CODE);
        master_b.setCreationTime(UPDATED_CREATION_TIME);
        master_b.setHandoverTime(UPDATED_HANDOVER_TIME);
        restMaster_bMockMvc.perform(put("/api/master_bs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(master_b)))
                .andExpect(status().isOk());

        // Validate the Master_b in the database
        List<Master_b> master_bs = master_bRepository.findAll();
        assertThat(master_bs).hasSize(databaseSizeBeforeUpdate);
        Master_b testMaster_b = master_bs.get(master_bs.size() - 1);
        assertThat(testMaster_b.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMaster_b.getCreationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_CREATION_TIME);
        assertThat(testMaster_b.getHandoverTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void deleteMaster_b() throws Exception {
        // Initialize the database
        master_bRepository.saveAndFlush(master_b);

		int databaseSizeBeforeDelete = master_bRepository.findAll().size();

        // Get the master_b
        restMaster_bMockMvc.perform(delete("/api/master_bs/{id}", master_b.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Master_b> master_bs = master_bRepository.findAll();
        assertThat(master_bs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
