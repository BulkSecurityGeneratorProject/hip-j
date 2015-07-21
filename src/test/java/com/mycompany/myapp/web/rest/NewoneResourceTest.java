package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Newone;
import com.mycompany.myapp.repository.NewoneRepository;

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
 * Test class for the NewoneResource REST controller.
 *
 * @see NewoneResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class NewoneResourceTest {

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
    private NewoneRepository newoneRepository;

    private MockMvc restNewoneMockMvc;

    private Newone newone;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NewoneResource newoneResource = new NewoneResource();
        ReflectionTestUtils.setField(newoneResource, "newoneRepository", newoneRepository);
        this.restNewoneMockMvc = MockMvcBuilders.standaloneSetup(newoneResource).build();
    }

    @Before
    public void initTest() {
        newone = new Newone();
        newone.setCode(DEFAULT_CODE);
        newone.setCreationTime(DEFAULT_CREATION_TIME);
        newone.setHandoverTime(DEFAULT_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void createNewone() throws Exception {
        int databaseSizeBeforeCreate = newoneRepository.findAll().size();

        // Create the Newone
        restNewoneMockMvc.perform(post("/api/newones")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(newone)))
                .andExpect(status().isCreated());

        // Validate the Newone in the database
        List<Newone> newones = newoneRepository.findAll();
        assertThat(newones).hasSize(databaseSizeBeforeCreate + 1);
        Newone testNewone = newones.get(newones.size() - 1);
        assertThat(testNewone.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testNewone.getCreationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_CREATION_TIME);
        assertThat(testNewone.getHandoverTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void getAllNewones() throws Exception {
        // Initialize the database
        newoneRepository.saveAndFlush(newone);

        // Get all the newones
        restNewoneMockMvc.perform(get("/api/newones"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(newone.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].creationTime").value(hasItem(DEFAULT_CREATION_TIME_STR)))
                .andExpect(jsonPath("$.[*].handoverTime").value(hasItem(DEFAULT_HANDOVER_TIME_STR)));
    }

    @Test
    @Transactional
    public void getNewone() throws Exception {
        // Initialize the database
        newoneRepository.saveAndFlush(newone);

        // Get the newone
        restNewoneMockMvc.perform(get("/api/newones/{id}", newone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(newone.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.creationTime").value(DEFAULT_CREATION_TIME_STR))
            .andExpect(jsonPath("$.handoverTime").value(DEFAULT_HANDOVER_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingNewone() throws Exception {
        // Get the newone
        restNewoneMockMvc.perform(get("/api/newones/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNewone() throws Exception {
        // Initialize the database
        newoneRepository.saveAndFlush(newone);

		int databaseSizeBeforeUpdate = newoneRepository.findAll().size();

        // Update the newone
        newone.setCode(UPDATED_CODE);
        newone.setCreationTime(UPDATED_CREATION_TIME);
        newone.setHandoverTime(UPDATED_HANDOVER_TIME);
        restNewoneMockMvc.perform(put("/api/newones")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(newone)))
                .andExpect(status().isOk());

        // Validate the Newone in the database
        List<Newone> newones = newoneRepository.findAll();
        assertThat(newones).hasSize(databaseSizeBeforeUpdate);
        Newone testNewone = newones.get(newones.size() - 1);
        assertThat(testNewone.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNewone.getCreationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_CREATION_TIME);
        assertThat(testNewone.getHandoverTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void deleteNewone() throws Exception {
        // Initialize the database
        newoneRepository.saveAndFlush(newone);

		int databaseSizeBeforeDelete = newoneRepository.findAll().size();

        // Get the newone
        restNewoneMockMvc.perform(delete("/api/newones/{id}", newone.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Newone> newones = newoneRepository.findAll();
        assertThat(newones).hasSize(databaseSizeBeforeDelete - 1);
    }
}
