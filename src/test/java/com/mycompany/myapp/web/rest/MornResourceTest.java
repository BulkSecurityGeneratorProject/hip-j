package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Morn;
import com.mycompany.myapp.repository.MornRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MornResource REST controller.
 *
 * @see MornResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MornResourceTest {

    private static final String DEFAULT_FIRST = "SAMPLE_TEXT";
    private static final String UPDATED_FIRST = "UPDATED_TEXT";
    private static final String DEFAULT_LAST = "SAMPLE_TEXT";
    private static final String UPDATED_LAST = "UPDATED_TEXT";

    private static final Integer DEFAULT_AGE = 0;
    private static final Integer UPDATED_AGE = 1;
    private static final String DEFAULT_EMAIL = "SAMPLE_TEXT";
    private static final String UPDATED_EMAIL = "UPDATED_TEXT";
    private static final String DEFAULT_PAPA = "SAMPLE_TEXT";
    private static final String UPDATED_PAPA = "UPDATED_TEXT";

    @Inject
    private MornRepository mornRepository;

    private MockMvc restMornMockMvc;

    private Morn morn;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MornResource mornResource = new MornResource();
        ReflectionTestUtils.setField(mornResource, "mornRepository", mornRepository);
        this.restMornMockMvc = MockMvcBuilders.standaloneSetup(mornResource).build();
    }

    @Before
    public void initTest() {
        morn = new Morn();
        morn.setFirst(DEFAULT_FIRST);
        morn.setLast(DEFAULT_LAST);
        morn.setAge(DEFAULT_AGE);
        morn.setEmail(DEFAULT_EMAIL);
        morn.setPapa(DEFAULT_PAPA);
    }

    @Test
    @Transactional
    public void createMorn() throws Exception {
        int databaseSizeBeforeCreate = mornRepository.findAll().size();

        // Create the Morn
        restMornMockMvc.perform(post("/api/morns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(morn)))
                .andExpect(status().isCreated());

        // Validate the Morn in the database
        List<Morn> morns = mornRepository.findAll();
        assertThat(morns).hasSize(databaseSizeBeforeCreate + 1);
        Morn testMorn = morns.get(morns.size() - 1);
        assertThat(testMorn.getFirst()).isEqualTo(DEFAULT_FIRST);
        assertThat(testMorn.getLast()).isEqualTo(DEFAULT_LAST);
        assertThat(testMorn.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testMorn.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testMorn.getPapa()).isEqualTo(DEFAULT_PAPA);
    }

    @Test
    @Transactional
    public void getAllMorns() throws Exception {
        // Initialize the database
        mornRepository.saveAndFlush(morn);

        // Get all the morns
        restMornMockMvc.perform(get("/api/morns"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(morn.getId().intValue())))
                .andExpect(jsonPath("$.[*].first").value(hasItem(DEFAULT_FIRST.toString())))
                .andExpect(jsonPath("$.[*].last").value(hasItem(DEFAULT_LAST.toString())))
                .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].papa").value(hasItem(DEFAULT_PAPA.toString())));
    }

    @Test
    @Transactional
    public void getMorn() throws Exception {
        // Initialize the database
        mornRepository.saveAndFlush(morn);

        // Get the morn
        restMornMockMvc.perform(get("/api/morns/{id}", morn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(morn.getId().intValue()))
            .andExpect(jsonPath("$.first").value(DEFAULT_FIRST.toString()))
            .andExpect(jsonPath("$.last").value(DEFAULT_LAST.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.papa").value(DEFAULT_PAPA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMorn() throws Exception {
        // Get the morn
        restMornMockMvc.perform(get("/api/morns/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMorn() throws Exception {
        // Initialize the database
        mornRepository.saveAndFlush(morn);

		int databaseSizeBeforeUpdate = mornRepository.findAll().size();

        // Update the morn
        morn.setFirst(UPDATED_FIRST);
        morn.setLast(UPDATED_LAST);
        morn.setAge(UPDATED_AGE);
        morn.setEmail(UPDATED_EMAIL);
        morn.setPapa(UPDATED_PAPA);
        restMornMockMvc.perform(put("/api/morns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(morn)))
                .andExpect(status().isOk());

        // Validate the Morn in the database
        List<Morn> morns = mornRepository.findAll();
        assertThat(morns).hasSize(databaseSizeBeforeUpdate);
        Morn testMorn = morns.get(morns.size() - 1);
        assertThat(testMorn.getFirst()).isEqualTo(UPDATED_FIRST);
        assertThat(testMorn.getLast()).isEqualTo(UPDATED_LAST);
        assertThat(testMorn.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testMorn.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMorn.getPapa()).isEqualTo(UPDATED_PAPA);
    }

    @Test
    @Transactional
    public void deleteMorn() throws Exception {
        // Initialize the database
        mornRepository.saveAndFlush(morn);

		int databaseSizeBeforeDelete = mornRepository.findAll().size();

        // Get the morn
        restMornMockMvc.perform(delete("/api/morns/{id}", morn.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Morn> morns = mornRepository.findAll();
        assertThat(morns).hasSize(databaseSizeBeforeDelete - 1);
    }
}
