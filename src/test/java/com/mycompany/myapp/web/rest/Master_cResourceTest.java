package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Master_c;
import com.mycompany.myapp.repository.Master_cRepository;

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
 * Test class for the Master_cResource REST controller.
 *
 * @see Master_cResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Master_cResourceTest {

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
    private Master_cRepository master_cRepository;

    private MockMvc restMaster_cMockMvc;

    private Master_c master_c;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Master_cResource master_cResource = new Master_cResource();
        ReflectionTestUtils.setField(master_cResource, "master_cRepository", master_cRepository);
        this.restMaster_cMockMvc = MockMvcBuilders.standaloneSetup(master_cResource).build();
    }

    @Before
    public void initTest() {
        master_c = new Master_c();
        master_c.setCode(DEFAULT_CODE);
        master_c.setCreationTime(DEFAULT_CREATION_TIME);
        master_c.setHandoverTime(DEFAULT_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void createMaster_c() throws Exception {
        int databaseSizeBeforeCreate = master_cRepository.findAll().size();

        // Create the Master_c
        restMaster_cMockMvc.perform(post("/api/master_cs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(master_c)))
                .andExpect(status().isCreated());

        // Validate the Master_c in the database
        List<Master_c> master_cs = master_cRepository.findAll();
        assertThat(master_cs).hasSize(databaseSizeBeforeCreate + 1);
        Master_c testMaster_c = master_cs.get(master_cs.size() - 1);
        assertThat(testMaster_c.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMaster_c.getCreationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_CREATION_TIME);
        assertThat(testMaster_c.getHandoverTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void getAllMaster_cs() throws Exception {
        // Initialize the database
        master_cRepository.saveAndFlush(master_c);

        // Get all the master_cs
        restMaster_cMockMvc.perform(get("/api/master_cs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(master_c.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].creationTime").value(hasItem(DEFAULT_CREATION_TIME_STR)))
                .andExpect(jsonPath("$.[*].handoverTime").value(hasItem(DEFAULT_HANDOVER_TIME_STR)));
    }

    @Test
    @Transactional
    public void getMaster_c() throws Exception {
        // Initialize the database
        master_cRepository.saveAndFlush(master_c);

        // Get the master_c
        restMaster_cMockMvc.perform(get("/api/master_cs/{id}", master_c.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(master_c.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.creationTime").value(DEFAULT_CREATION_TIME_STR))
            .andExpect(jsonPath("$.handoverTime").value(DEFAULT_HANDOVER_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingMaster_c() throws Exception {
        // Get the master_c
        restMaster_cMockMvc.perform(get("/api/master_cs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaster_c() throws Exception {
        // Initialize the database
        master_cRepository.saveAndFlush(master_c);

		int databaseSizeBeforeUpdate = master_cRepository.findAll().size();

        // Update the master_c
        master_c.setCode(UPDATED_CODE);
        master_c.setCreationTime(UPDATED_CREATION_TIME);
        master_c.setHandoverTime(UPDATED_HANDOVER_TIME);
        restMaster_cMockMvc.perform(put("/api/master_cs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(master_c)))
                .andExpect(status().isOk());

        // Validate the Master_c in the database
        List<Master_c> master_cs = master_cRepository.findAll();
        assertThat(master_cs).hasSize(databaseSizeBeforeUpdate);
        Master_c testMaster_c = master_cs.get(master_cs.size() - 1);
        assertThat(testMaster_c.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMaster_c.getCreationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_CREATION_TIME);
        assertThat(testMaster_c.getHandoverTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void deleteMaster_c() throws Exception {
        // Initialize the database
        master_cRepository.saveAndFlush(master_c);

		int databaseSizeBeforeDelete = master_cRepository.findAll().size();

        // Get the master_c
        restMaster_cMockMvc.perform(delete("/api/master_cs/{id}", master_c.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Master_c> master_cs = master_cRepository.findAll();
        assertThat(master_cs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
