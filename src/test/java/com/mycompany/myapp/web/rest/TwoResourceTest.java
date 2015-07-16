package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Two;
import com.mycompany.myapp.repository.TwoRepository;

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
 * Test class for the TwoResource REST controller.
 *
 * @see TwoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TwoResourceTest {

    private static final String DEFAULT_FIRST = "SAMPLE_TEXT";
    private static final String UPDATED_FIRST = "UPDATED_TEXT";
    private static final String DEFAULT_LAST = "SAMPLE_TEXT";
    private static final String UPDATED_LAST = "UPDATED_TEXT";

    private static final Integer DEFAULT_AGE = 0;
    private static final Integer UPDATED_AGE = 1;
    private static final String DEFAULT_EMAIL = "SAMPLE_TEXT";
    private static final String UPDATED_EMAIL = "UPDATED_TEXT";
    private static final String DEFAULT_OYE = "SAMPLE_TEXT";
    private static final String UPDATED_OYE = "UPDATED_TEXT";
    private static final String DEFAULT_EYO = "SAMPLE_TEXT";
    private static final String UPDATED_EYO = "UPDATED_TEXT";

    @Inject
    private TwoRepository twoRepository;

    private MockMvc restTwoMockMvc;

    private Two two;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TwoResource twoResource = new TwoResource();
        ReflectionTestUtils.setField(twoResource, "twoRepository", twoRepository);
        this.restTwoMockMvc = MockMvcBuilders.standaloneSetup(twoResource).build();
    }

    @Before
    public void initTest() {
        two = new Two();
        two.setFirst(DEFAULT_FIRST);
        two.setLast(DEFAULT_LAST);
        two.setAge(DEFAULT_AGE);
        two.setEmail(DEFAULT_EMAIL);
        two.setOye(DEFAULT_OYE);
        two.setEyo(DEFAULT_EYO);
    }

    @Test
    @Transactional
    public void createTwo() throws Exception {
        int databaseSizeBeforeCreate = twoRepository.findAll().size();

        // Create the Two
        restTwoMockMvc.perform(post("/api/twos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(two)))
                .andExpect(status().isCreated());

        // Validate the Two in the database
        List<Two> twos = twoRepository.findAll();
        assertThat(twos).hasSize(databaseSizeBeforeCreate + 1);
        Two testTwo = twos.get(twos.size() - 1);
        assertThat(testTwo.getFirst()).isEqualTo(DEFAULT_FIRST);
        assertThat(testTwo.getLast()).isEqualTo(DEFAULT_LAST);
        assertThat(testTwo.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testTwo.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testTwo.getOye()).isEqualTo(DEFAULT_OYE);
        assertThat(testTwo.getEyo()).isEqualTo(DEFAULT_EYO);
    }

    @Test
    @Transactional
    public void getAllTwos() throws Exception {
        // Initialize the database
        twoRepository.saveAndFlush(two);

        // Get all the twos
        restTwoMockMvc.perform(get("/api/twos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(two.getId().intValue())))
                .andExpect(jsonPath("$.[*].first").value(hasItem(DEFAULT_FIRST.toString())))
                .andExpect(jsonPath("$.[*].last").value(hasItem(DEFAULT_LAST.toString())))
                .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].oye").value(hasItem(DEFAULT_OYE.toString())))
                .andExpect(jsonPath("$.[*].eyo").value(hasItem(DEFAULT_EYO.toString())));
    }

    @Test
    @Transactional
    public void getTwo() throws Exception {
        // Initialize the database
        twoRepository.saveAndFlush(two);

        // Get the two
        restTwoMockMvc.perform(get("/api/twos/{id}", two.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(two.getId().intValue()))
            .andExpect(jsonPath("$.first").value(DEFAULT_FIRST.toString()))
            .andExpect(jsonPath("$.last").value(DEFAULT_LAST.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.oye").value(DEFAULT_OYE.toString()))
            .andExpect(jsonPath("$.eyo").value(DEFAULT_EYO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTwo() throws Exception {
        // Get the two
        restTwoMockMvc.perform(get("/api/twos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTwo() throws Exception {
        // Initialize the database
        twoRepository.saveAndFlush(two);

		int databaseSizeBeforeUpdate = twoRepository.findAll().size();

        // Update the two
        two.setFirst(UPDATED_FIRST);
        two.setLast(UPDATED_LAST);
        two.setAge(UPDATED_AGE);
        two.setEmail(UPDATED_EMAIL);
        two.setOye(UPDATED_OYE);
        two.setEyo(UPDATED_EYO);
        restTwoMockMvc.perform(put("/api/twos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(two)))
                .andExpect(status().isOk());

        // Validate the Two in the database
        List<Two> twos = twoRepository.findAll();
        assertThat(twos).hasSize(databaseSizeBeforeUpdate);
        Two testTwo = twos.get(twos.size() - 1);
        assertThat(testTwo.getFirst()).isEqualTo(UPDATED_FIRST);
        assertThat(testTwo.getLast()).isEqualTo(UPDATED_LAST);
        assertThat(testTwo.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testTwo.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testTwo.getOye()).isEqualTo(UPDATED_OYE);
        assertThat(testTwo.getEyo()).isEqualTo(UPDATED_EYO);
    }

    @Test
    @Transactional
    public void deleteTwo() throws Exception {
        // Initialize the database
        twoRepository.saveAndFlush(two);

		int databaseSizeBeforeDelete = twoRepository.findAll().size();

        // Get the two
        restTwoMockMvc.perform(delete("/api/twos/{id}", two.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Two> twos = twoRepository.findAll();
        assertThat(twos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
