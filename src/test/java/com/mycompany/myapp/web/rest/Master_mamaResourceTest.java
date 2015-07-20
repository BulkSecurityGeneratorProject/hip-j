package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Master_mama;
import com.mycompany.myapp.repository.Master_mamaRepository;

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
 * Test class for the Master_mamaResource REST controller.
 *
 * @see Master_mamaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Master_mamaResourceTest {

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
    private Master_mamaRepository master_mamaRepository;

    private MockMvc restMaster_mamaMockMvc;

    private Master_mama master_mama;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Master_mamaResource master_mamaResource = new Master_mamaResource();
        ReflectionTestUtils.setField(master_mamaResource, "master_mamaRepository", master_mamaRepository);
        this.restMaster_mamaMockMvc = MockMvcBuilders.standaloneSetup(master_mamaResource).build();
    }

    @Before
    public void initTest() {
        master_mama = new Master_mama();
        master_mama.setCode(DEFAULT_CODE);
        master_mama.setCreationTime(DEFAULT_CREATION_TIME);
        master_mama.setHandoverTime(DEFAULT_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void createMaster_mama() throws Exception {
        int databaseSizeBeforeCreate = master_mamaRepository.findAll().size();

        // Create the Master_mama
        restMaster_mamaMockMvc.perform(post("/api/master_mamas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(master_mama)))
                .andExpect(status().isCreated());

        // Validate the Master_mama in the database
        List<Master_mama> master_mamas = master_mamaRepository.findAll();
        assertThat(master_mamas).hasSize(databaseSizeBeforeCreate + 1);
        Master_mama testMaster_mama = master_mamas.get(master_mamas.size() - 1);
        assertThat(testMaster_mama.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMaster_mama.getCreationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_CREATION_TIME);
        assertThat(testMaster_mama.getHandoverTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void getAllMaster_mamas() throws Exception {
        // Initialize the database
        master_mamaRepository.saveAndFlush(master_mama);

        // Get all the master_mamas
        restMaster_mamaMockMvc.perform(get("/api/master_mamas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(master_mama.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].creationTime").value(hasItem(DEFAULT_CREATION_TIME_STR)))
                .andExpect(jsonPath("$.[*].handoverTime").value(hasItem(DEFAULT_HANDOVER_TIME_STR)));
    }

    @Test
    @Transactional
    public void getMaster_mama() throws Exception {
        // Initialize the database
        master_mamaRepository.saveAndFlush(master_mama);

        // Get the master_mama
        restMaster_mamaMockMvc.perform(get("/api/master_mamas/{id}", master_mama.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(master_mama.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.creationTime").value(DEFAULT_CREATION_TIME_STR))
            .andExpect(jsonPath("$.handoverTime").value(DEFAULT_HANDOVER_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingMaster_mama() throws Exception {
        // Get the master_mama
        restMaster_mamaMockMvc.perform(get("/api/master_mamas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaster_mama() throws Exception {
        // Initialize the database
        master_mamaRepository.saveAndFlush(master_mama);

		int databaseSizeBeforeUpdate = master_mamaRepository.findAll().size();

        // Update the master_mama
        master_mama.setCode(UPDATED_CODE);
        master_mama.setCreationTime(UPDATED_CREATION_TIME);
        master_mama.setHandoverTime(UPDATED_HANDOVER_TIME);
        restMaster_mamaMockMvc.perform(put("/api/master_mamas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(master_mama)))
                .andExpect(status().isOk());

        // Validate the Master_mama in the database
        List<Master_mama> master_mamas = master_mamaRepository.findAll();
        assertThat(master_mamas).hasSize(databaseSizeBeforeUpdate);
        Master_mama testMaster_mama = master_mamas.get(master_mamas.size() - 1);
        assertThat(testMaster_mama.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMaster_mama.getCreationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_CREATION_TIME);
        assertThat(testMaster_mama.getHandoverTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void deleteMaster_mama() throws Exception {
        // Initialize the database
        master_mamaRepository.saveAndFlush(master_mama);

		int databaseSizeBeforeDelete = master_mamaRepository.findAll().size();

        // Get the master_mama
        restMaster_mamaMockMvc.perform(delete("/api/master_mamas/{id}", master_mama.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Master_mama> master_mamas = master_mamaRepository.findAll();
        assertThat(master_mamas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
