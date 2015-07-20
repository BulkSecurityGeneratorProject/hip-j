package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.OneThree;
import com.mycompany.myapp.repository.OneThreeRepository;

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
 * Test class for the OneThreeResource REST controller.
 *
 * @see OneThreeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OneThreeResourceTest {

    private static final String DEFAULT_THREE_FOUR = "SAMPLE_TEXT";
    private static final String UPDATED_THREE_FOUR = "UPDATED_TEXT";

    @Inject
    private OneThreeRepository oneThreeRepository;

    private MockMvc restOneThreeMockMvc;

    private OneThree oneThree;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OneThreeResource oneThreeResource = new OneThreeResource();
        ReflectionTestUtils.setField(oneThreeResource, "oneThreeRepository", oneThreeRepository);
        this.restOneThreeMockMvc = MockMvcBuilders.standaloneSetup(oneThreeResource).build();
    }

    @Before
    public void initTest() {
        oneThree = new OneThree();
        oneThree.setThreeFour(DEFAULT_THREE_FOUR);
    }

    @Test
    @Transactional
    public void createOneThree() throws Exception {
        int databaseSizeBeforeCreate = oneThreeRepository.findAll().size();

        // Create the OneThree
        restOneThreeMockMvc.perform(post("/api/oneThrees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oneThree)))
                .andExpect(status().isCreated());

        // Validate the OneThree in the database
        List<OneThree> oneThrees = oneThreeRepository.findAll();
        assertThat(oneThrees).hasSize(databaseSizeBeforeCreate + 1);
        OneThree testOneThree = oneThrees.get(oneThrees.size() - 1);
        assertThat(testOneThree.getThreeFour()).isEqualTo(DEFAULT_THREE_FOUR);
    }

    @Test
    @Transactional
    public void getAllOneThrees() throws Exception {
        // Initialize the database
        oneThreeRepository.saveAndFlush(oneThree);

        // Get all the oneThrees
        restOneThreeMockMvc.perform(get("/api/oneThrees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(oneThree.getId().intValue())))
                .andExpect(jsonPath("$.[*].threeFour").value(hasItem(DEFAULT_THREE_FOUR.toString())));
    }

    @Test
    @Transactional
    public void getOneThree() throws Exception {
        // Initialize the database
        oneThreeRepository.saveAndFlush(oneThree);

        // Get the oneThree
        restOneThreeMockMvc.perform(get("/api/oneThrees/{id}", oneThree.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(oneThree.getId().intValue()))
            .andExpect(jsonPath("$.threeFour").value(DEFAULT_THREE_FOUR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOneThree() throws Exception {
        // Get the oneThree
        restOneThreeMockMvc.perform(get("/api/oneThrees/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOneThree() throws Exception {
        // Initialize the database
        oneThreeRepository.saveAndFlush(oneThree);

		int databaseSizeBeforeUpdate = oneThreeRepository.findAll().size();

        // Update the oneThree
        oneThree.setThreeFour(UPDATED_THREE_FOUR);
        restOneThreeMockMvc.perform(put("/api/oneThrees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oneThree)))
                .andExpect(status().isOk());

        // Validate the OneThree in the database
        List<OneThree> oneThrees = oneThreeRepository.findAll();
        assertThat(oneThrees).hasSize(databaseSizeBeforeUpdate);
        OneThree testOneThree = oneThrees.get(oneThrees.size() - 1);
        assertThat(testOneThree.getThreeFour()).isEqualTo(UPDATED_THREE_FOUR);
    }

    @Test
    @Transactional
    public void deleteOneThree() throws Exception {
        // Initialize the database
        oneThreeRepository.saveAndFlush(oneThree);

		int databaseSizeBeforeDelete = oneThreeRepository.findAll().size();

        // Get the oneThree
        restOneThreeMockMvc.perform(delete("/api/oneThrees/{id}", oneThree.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<OneThree> oneThrees = oneThreeRepository.findAll();
        assertThat(oneThrees).hasSize(databaseSizeBeforeDelete - 1);
    }
}
