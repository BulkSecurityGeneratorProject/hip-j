package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Jewone;
import com.mycompany.myapp.repository.JewoneRepository;

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
 * Test class for the JewoneResource REST controller.
 *
 * @see JewoneResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class JewoneResourceTest {

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
    private JewoneRepository jewoneRepository;

    private MockMvc restJewoneMockMvc;

    private Jewone jewone;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JewoneResource jewoneResource = new JewoneResource();
        ReflectionTestUtils.setField(jewoneResource, "jewoneRepository", jewoneRepository);
        this.restJewoneMockMvc = MockMvcBuilders.standaloneSetup(jewoneResource).build();
    }

    @Before
    public void initTest() {
        jewone = new Jewone();
        jewone.setCode(DEFAULT_CODE);
        jewone.setCreationTime(DEFAULT_CREATION_TIME);
        jewone.setHandoverTime(DEFAULT_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void createJewone() throws Exception {
        int databaseSizeBeforeCreate = jewoneRepository.findAll().size();

        // Create the Jewone
        restJewoneMockMvc.perform(post("/api/jewones")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jewone)))
                .andExpect(status().isCreated());

        // Validate the Jewone in the database
        List<Jewone> jewones = jewoneRepository.findAll();
        assertThat(jewones).hasSize(databaseSizeBeforeCreate + 1);
        Jewone testJewone = jewones.get(jewones.size() - 1);
        assertThat(testJewone.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testJewone.getCreationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_CREATION_TIME);
        assertThat(testJewone.getHandoverTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void getAllJewones() throws Exception {
        // Initialize the database
        jewoneRepository.saveAndFlush(jewone);

        // Get all the jewones
        restJewoneMockMvc.perform(get("/api/jewones"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(jewone.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].creationTime").value(hasItem(DEFAULT_CREATION_TIME_STR)))
                .andExpect(jsonPath("$.[*].handoverTime").value(hasItem(DEFAULT_HANDOVER_TIME_STR)));
    }

    @Test
    @Transactional
    public void getJewone() throws Exception {
        // Initialize the database
        jewoneRepository.saveAndFlush(jewone);

        // Get the jewone
        restJewoneMockMvc.perform(get("/api/jewones/{id}", jewone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(jewone.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.creationTime").value(DEFAULT_CREATION_TIME_STR))
            .andExpect(jsonPath("$.handoverTime").value(DEFAULT_HANDOVER_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingJewone() throws Exception {
        // Get the jewone
        restJewoneMockMvc.perform(get("/api/jewones/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJewone() throws Exception {
        // Initialize the database
        jewoneRepository.saveAndFlush(jewone);

		int databaseSizeBeforeUpdate = jewoneRepository.findAll().size();

        // Update the jewone
        jewone.setCode(UPDATED_CODE);
        jewone.setCreationTime(UPDATED_CREATION_TIME);
        jewone.setHandoverTime(UPDATED_HANDOVER_TIME);
        restJewoneMockMvc.perform(put("/api/jewones")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jewone)))
                .andExpect(status().isOk());

        // Validate the Jewone in the database
        List<Jewone> jewones = jewoneRepository.findAll();
        assertThat(jewones).hasSize(databaseSizeBeforeUpdate);
        Jewone testJewone = jewones.get(jewones.size() - 1);
        assertThat(testJewone.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testJewone.getCreationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_CREATION_TIME);
        assertThat(testJewone.getHandoverTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void deleteJewone() throws Exception {
        // Initialize the database
        jewoneRepository.saveAndFlush(jewone);

		int databaseSizeBeforeDelete = jewoneRepository.findAll().size();

        // Get the jewone
        restJewoneMockMvc.perform(delete("/api/jewones/{id}", jewone.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Jewone> jewones = jewoneRepository.findAll();
        assertThat(jewones).hasSize(databaseSizeBeforeDelete - 1);
    }
}
