package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Mewone;
import com.mycompany.myapp.repository.MewoneRepository;

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
 * Test class for the MewoneResource REST controller.
 *
 * @see MewoneResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MewoneResourceTest {

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
    private MewoneRepository mewoneRepository;

    private MockMvc restMewoneMockMvc;

    private Mewone mewone;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MewoneResource mewoneResource = new MewoneResource();
        ReflectionTestUtils.setField(mewoneResource, "mewoneRepository", mewoneRepository);
        this.restMewoneMockMvc = MockMvcBuilders.standaloneSetup(mewoneResource).build();
    }

    @Before
    public void initTest() {
        mewone = new Mewone();
        mewone.setCode(DEFAULT_CODE);
        mewone.setCreationTime(DEFAULT_CREATION_TIME);
        mewone.setHandoverTime(DEFAULT_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void createMewone() throws Exception {
        int databaseSizeBeforeCreate = mewoneRepository.findAll().size();

        // Create the Mewone
        restMewoneMockMvc.perform(post("/api/mewones")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mewone)))
                .andExpect(status().isCreated());

        // Validate the Mewone in the database
        List<Mewone> mewones = mewoneRepository.findAll();
        assertThat(mewones).hasSize(databaseSizeBeforeCreate + 1);
        Mewone testMewone = mewones.get(mewones.size() - 1);
        assertThat(testMewone.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMewone.getCreationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_CREATION_TIME);
        assertThat(testMewone.getHandoverTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void getAllMewones() throws Exception {
        // Initialize the database
        mewoneRepository.saveAndFlush(mewone);

        // Get all the mewones
        restMewoneMockMvc.perform(get("/api/mewones"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(mewone.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].creationTime").value(hasItem(DEFAULT_CREATION_TIME_STR)))
                .andExpect(jsonPath("$.[*].handoverTime").value(hasItem(DEFAULT_HANDOVER_TIME_STR)));
    }

    @Test
    @Transactional
    public void getMewone() throws Exception {
        // Initialize the database
        mewoneRepository.saveAndFlush(mewone);

        // Get the mewone
        restMewoneMockMvc.perform(get("/api/mewones/{id}", mewone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(mewone.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.creationTime").value(DEFAULT_CREATION_TIME_STR))
            .andExpect(jsonPath("$.handoverTime").value(DEFAULT_HANDOVER_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingMewone() throws Exception {
        // Get the mewone
        restMewoneMockMvc.perform(get("/api/mewones/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMewone() throws Exception {
        // Initialize the database
        mewoneRepository.saveAndFlush(mewone);

		int databaseSizeBeforeUpdate = mewoneRepository.findAll().size();

        // Update the mewone
        mewone.setCode(UPDATED_CODE);
        mewone.setCreationTime(UPDATED_CREATION_TIME);
        mewone.setHandoverTime(UPDATED_HANDOVER_TIME);
        restMewoneMockMvc.perform(put("/api/mewones")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mewone)))
                .andExpect(status().isOk());

        // Validate the Mewone in the database
        List<Mewone> mewones = mewoneRepository.findAll();
        assertThat(mewones).hasSize(databaseSizeBeforeUpdate);
        Mewone testMewone = mewones.get(mewones.size() - 1);
        assertThat(testMewone.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMewone.getCreationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_CREATION_TIME);
        assertThat(testMewone.getHandoverTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void deleteMewone() throws Exception {
        // Initialize the database
        mewoneRepository.saveAndFlush(mewone);

		int databaseSizeBeforeDelete = mewoneRepository.findAll().size();

        // Get the mewone
        restMewoneMockMvc.perform(delete("/api/mewones/{id}", mewone.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Mewone> mewones = mewoneRepository.findAll();
        assertThat(mewones).hasSize(databaseSizeBeforeDelete - 1);
    }
}
