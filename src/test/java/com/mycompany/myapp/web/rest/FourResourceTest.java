package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Four;
import com.mycompany.myapp.repository.FourRepository;

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
 * Test class for the FourResource REST controller.
 *
 * @see FourResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class FourResourceTest {

    private static final String DEFAULT_FIRSTNAME = "SAMPLE_TEXT";
    private static final String UPDATED_FIRSTNAME = "UPDATED_TEXT";
    private static final String DEFAULT_LASTNAME = "SAMPLE_TEXT";
    private static final String UPDATED_LASTNAME = "UPDATED_TEXT";

    private static final Integer DEFAULT_AGE = 0;
    private static final Integer UPDATED_AGE = 1;
    private static final String DEFAULT_EMAIL = "SAMPLE_TEXT";
    private static final String UPDATED_EMAIL = "UPDATED_TEXT";
    private static final String DEFAULT_COUNTRY = "SAMPLE_TEXT";
    private static final String UPDATED_COUNTRY = "UPDATED_TEXT";

    @Inject
    private FourRepository fourRepository;

    private MockMvc restFourMockMvc;

    private Four four;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FourResource fourResource = new FourResource();
        ReflectionTestUtils.setField(fourResource, "fourRepository", fourRepository);
        this.restFourMockMvc = MockMvcBuilders.standaloneSetup(fourResource).build();
    }

    @Before
    public void initTest() {
        four = new Four();
        four.setFirstname(DEFAULT_FIRSTNAME);
        four.setLastname(DEFAULT_LASTNAME);
        four.setAge(DEFAULT_AGE);
        four.setEmail(DEFAULT_EMAIL);
        four.setCountry(DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    public void createFour() throws Exception {
        int databaseSizeBeforeCreate = fourRepository.findAll().size();

        // Create the Four
        restFourMockMvc.perform(post("/api/fours")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(four)))
                .andExpect(status().isCreated());

        // Validate the Four in the database
        List<Four> fours = fourRepository.findAll();
        assertThat(fours).hasSize(databaseSizeBeforeCreate + 1);
        Four testFour = fours.get(fours.size() - 1);
        assertThat(testFour.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testFour.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testFour.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testFour.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testFour.getCountry()).isEqualTo(DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllFours() throws Exception {
        // Initialize the database
        fourRepository.saveAndFlush(four);

        // Get all the fours
        restFourMockMvc.perform(get("/api/fours"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(four.getId().intValue())))
                .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME.toString())))
                .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME.toString())))
                .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())));
    }

    @Test
    @Transactional
    public void getFour() throws Exception {
        // Initialize the database
        fourRepository.saveAndFlush(four);

        // Get the four
        restFourMockMvc.perform(get("/api/fours/{id}", four.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(four.getId().intValue()))
            .andExpect(jsonPath("$.firstname").value(DEFAULT_FIRSTNAME.toString()))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFour() throws Exception {
        // Get the four
        restFourMockMvc.perform(get("/api/fours/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFour() throws Exception {
        // Initialize the database
        fourRepository.saveAndFlush(four);

		int databaseSizeBeforeUpdate = fourRepository.findAll().size();

        // Update the four
        four.setFirstname(UPDATED_FIRSTNAME);
        four.setLastname(UPDATED_LASTNAME);
        four.setAge(UPDATED_AGE);
        four.setEmail(UPDATED_EMAIL);
        four.setCountry(UPDATED_COUNTRY);
        restFourMockMvc.perform(put("/api/fours")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(four)))
                .andExpect(status().isOk());

        // Validate the Four in the database
        List<Four> fours = fourRepository.findAll();
        assertThat(fours).hasSize(databaseSizeBeforeUpdate);
        Four testFour = fours.get(fours.size() - 1);
        assertThat(testFour.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testFour.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testFour.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testFour.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testFour.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void deleteFour() throws Exception {
        // Initialize the database
        fourRepository.saveAndFlush(four);

		int databaseSizeBeforeDelete = fourRepository.findAll().size();

        // Get the four
        restFourMockMvc.perform(delete("/api/fours/{id}", four.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Four> fours = fourRepository.findAll();
        assertThat(fours).hasSize(databaseSizeBeforeDelete - 1);
    }
}
