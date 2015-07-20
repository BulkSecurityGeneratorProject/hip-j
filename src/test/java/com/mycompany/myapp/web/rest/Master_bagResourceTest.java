package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Master_bag;
import com.mycompany.myapp.repository.Master_bagRepository;

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
 * Test class for the Master_bagResource REST controller.
 *
 * @see Master_bagResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Master_bagResourceTest {

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
    private Master_bagRepository master_bagRepository;

    private MockMvc restMaster_bagMockMvc;

    private Master_bag master_bag;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Master_bagResource master_bagResource = new Master_bagResource();
        ReflectionTestUtils.setField(master_bagResource, "master_bagRepository", master_bagRepository);
        this.restMaster_bagMockMvc = MockMvcBuilders.standaloneSetup(master_bagResource).build();
    }

    @Before
    public void initTest() {
        master_bag = new Master_bag();
        master_bag.setCode(DEFAULT_CODE);
        master_bag.setCreationTime(DEFAULT_CREATION_TIME);
        master_bag.setHandoverTime(DEFAULT_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void createMaster_bag() throws Exception {
        int databaseSizeBeforeCreate = master_bagRepository.findAll().size();

        // Create the Master_bag
        restMaster_bagMockMvc.perform(post("/api/master_bags")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(master_bag)))
                .andExpect(status().isCreated());

        // Validate the Master_bag in the database
        List<Master_bag> master_bags = master_bagRepository.findAll();
        assertThat(master_bags).hasSize(databaseSizeBeforeCreate + 1);
        Master_bag testMaster_bag = master_bags.get(master_bags.size() - 1);
        assertThat(testMaster_bag.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMaster_bag.getCreationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_CREATION_TIME);
        assertThat(testMaster_bag.getHandoverTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void getAllMaster_bags() throws Exception {
        // Initialize the database
        master_bagRepository.saveAndFlush(master_bag);

        // Get all the master_bags
        restMaster_bagMockMvc.perform(get("/api/master_bags"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(master_bag.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].creationTime").value(hasItem(DEFAULT_CREATION_TIME_STR)))
                .andExpect(jsonPath("$.[*].handoverTime").value(hasItem(DEFAULT_HANDOVER_TIME_STR)));
    }

    @Test
    @Transactional
    public void getMaster_bag() throws Exception {
        // Initialize the database
        master_bagRepository.saveAndFlush(master_bag);

        // Get the master_bag
        restMaster_bagMockMvc.perform(get("/api/master_bags/{id}", master_bag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(master_bag.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.creationTime").value(DEFAULT_CREATION_TIME_STR))
            .andExpect(jsonPath("$.handoverTime").value(DEFAULT_HANDOVER_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingMaster_bag() throws Exception {
        // Get the master_bag
        restMaster_bagMockMvc.perform(get("/api/master_bags/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaster_bag() throws Exception {
        // Initialize the database
        master_bagRepository.saveAndFlush(master_bag);

		int databaseSizeBeforeUpdate = master_bagRepository.findAll().size();

        // Update the master_bag
        master_bag.setCode(UPDATED_CODE);
        master_bag.setCreationTime(UPDATED_CREATION_TIME);
        master_bag.setHandoverTime(UPDATED_HANDOVER_TIME);
        restMaster_bagMockMvc.perform(put("/api/master_bags")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(master_bag)))
                .andExpect(status().isOk());

        // Validate the Master_bag in the database
        List<Master_bag> master_bags = master_bagRepository.findAll();
        assertThat(master_bags).hasSize(databaseSizeBeforeUpdate);
        Master_bag testMaster_bag = master_bags.get(master_bags.size() - 1);
        assertThat(testMaster_bag.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMaster_bag.getCreationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_CREATION_TIME);
        assertThat(testMaster_bag.getHandoverTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void deleteMaster_bag() throws Exception {
        // Initialize the database
        master_bagRepository.saveAndFlush(master_bag);

		int databaseSizeBeforeDelete = master_bagRepository.findAll().size();

        // Get the master_bag
        restMaster_bagMockMvc.perform(delete("/api/master_bags/{id}", master_bag.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Master_bag> master_bags = master_bagRepository.findAll();
        assertThat(master_bags).hasSize(databaseSizeBeforeDelete - 1);
    }
}
