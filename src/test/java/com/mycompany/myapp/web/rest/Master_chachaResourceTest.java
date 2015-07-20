package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Master_chacha;
import com.mycompany.myapp.repository.Master_chachaRepository;

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
 * Test class for the Master_chachaResource REST controller.
 *
 * @see Master_chachaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Master_chachaResourceTest {

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
    private Master_chachaRepository master_chachaRepository;

    private MockMvc restMaster_chachaMockMvc;

    private Master_chacha master_chacha;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Master_chachaResource master_chachaResource = new Master_chachaResource();
        ReflectionTestUtils.setField(master_chachaResource, "master_chachaRepository", master_chachaRepository);
        this.restMaster_chachaMockMvc = MockMvcBuilders.standaloneSetup(master_chachaResource).build();
    }

    @Before
    public void initTest() {
        master_chacha = new Master_chacha();
        master_chacha.setCode(DEFAULT_CODE);
        master_chacha.setCreationTime(DEFAULT_CREATION_TIME);
        master_chacha.setHandoverTime(DEFAULT_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void createMaster_chacha() throws Exception {
        int databaseSizeBeforeCreate = master_chachaRepository.findAll().size();

        // Create the Master_chacha
        restMaster_chachaMockMvc.perform(post("/api/master_chachas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(master_chacha)))
                .andExpect(status().isCreated());

        // Validate the Master_chacha in the database
        List<Master_chacha> master_chachas = master_chachaRepository.findAll();
        assertThat(master_chachas).hasSize(databaseSizeBeforeCreate + 1);
        Master_chacha testMaster_chacha = master_chachas.get(master_chachas.size() - 1);
        assertThat(testMaster_chacha.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMaster_chacha.getCreationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_CREATION_TIME);
        assertThat(testMaster_chacha.getHandoverTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void getAllMaster_chachas() throws Exception {
        // Initialize the database
        master_chachaRepository.saveAndFlush(master_chacha);

        // Get all the master_chachas
        restMaster_chachaMockMvc.perform(get("/api/master_chachas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(master_chacha.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].creationTime").value(hasItem(DEFAULT_CREATION_TIME_STR)))
                .andExpect(jsonPath("$.[*].handoverTime").value(hasItem(DEFAULT_HANDOVER_TIME_STR)));
    }

    @Test
    @Transactional
    public void getMaster_chacha() throws Exception {
        // Initialize the database
        master_chachaRepository.saveAndFlush(master_chacha);

        // Get the master_chacha
        restMaster_chachaMockMvc.perform(get("/api/master_chachas/{id}", master_chacha.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(master_chacha.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.creationTime").value(DEFAULT_CREATION_TIME_STR))
            .andExpect(jsonPath("$.handoverTime").value(DEFAULT_HANDOVER_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingMaster_chacha() throws Exception {
        // Get the master_chacha
        restMaster_chachaMockMvc.perform(get("/api/master_chachas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaster_chacha() throws Exception {
        // Initialize the database
        master_chachaRepository.saveAndFlush(master_chacha);

		int databaseSizeBeforeUpdate = master_chachaRepository.findAll().size();

        // Update the master_chacha
        master_chacha.setCode(UPDATED_CODE);
        master_chacha.setCreationTime(UPDATED_CREATION_TIME);
        master_chacha.setHandoverTime(UPDATED_HANDOVER_TIME);
        restMaster_chachaMockMvc.perform(put("/api/master_chachas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(master_chacha)))
                .andExpect(status().isOk());

        // Validate the Master_chacha in the database
        List<Master_chacha> master_chachas = master_chachaRepository.findAll();
        assertThat(master_chachas).hasSize(databaseSizeBeforeUpdate);
        Master_chacha testMaster_chacha = master_chachas.get(master_chachas.size() - 1);
        assertThat(testMaster_chacha.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMaster_chacha.getCreationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_CREATION_TIME);
        assertThat(testMaster_chacha.getHandoverTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_HANDOVER_TIME);
    }

    @Test
    @Transactional
    public void deleteMaster_chacha() throws Exception {
        // Initialize the database
        master_chachaRepository.saveAndFlush(master_chacha);

		int databaseSizeBeforeDelete = master_chachaRepository.findAll().size();

        // Get the master_chacha
        restMaster_chachaMockMvc.perform(delete("/api/master_chachas/{id}", master_chacha.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Master_chacha> master_chachas = master_chachaRepository.findAll();
        assertThat(master_chachas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
