package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.OneTwo;
import com.mycompany.myapp.repository.OneTwoRepository;

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
 * Test class for the OneTwoResource REST controller.
 *
 * @see OneTwoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OneTwoResourceTest {

    private static final String DEFAULT_ONE = "SAMPLE_TEXT";
    private static final String UPDATED_ONE = "UPDATED_TEXT";

    @Inject
    private OneTwoRepository oneTwoRepository;

    private MockMvc restOneTwoMockMvc;

    private OneTwo oneTwo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OneTwoResource oneTwoResource = new OneTwoResource();
        ReflectionTestUtils.setField(oneTwoResource, "oneTwoRepository", oneTwoRepository);
        this.restOneTwoMockMvc = MockMvcBuilders.standaloneSetup(oneTwoResource).build();
    }

    @Before
    public void initTest() {
        oneTwo = new OneTwo();
        oneTwo.setOne(DEFAULT_ONE);
    }

    @Test
    @Transactional
    public void createOneTwo() throws Exception {
        int databaseSizeBeforeCreate = oneTwoRepository.findAll().size();

        // Create the OneTwo
        restOneTwoMockMvc.perform(post("/api/oneTwos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oneTwo)))
                .andExpect(status().isCreated());

        // Validate the OneTwo in the database
        List<OneTwo> oneTwos = oneTwoRepository.findAll();
        assertThat(oneTwos).hasSize(databaseSizeBeforeCreate + 1);
        OneTwo testOneTwo = oneTwos.get(oneTwos.size() - 1);
        assertThat(testOneTwo.getOne()).isEqualTo(DEFAULT_ONE);
    }

    @Test
    @Transactional
    public void getAllOneTwos() throws Exception {
        // Initialize the database
        oneTwoRepository.saveAndFlush(oneTwo);

        // Get all the oneTwos
        restOneTwoMockMvc.perform(get("/api/oneTwos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(oneTwo.getId().intValue())))
                .andExpect(jsonPath("$.[*].one").value(hasItem(DEFAULT_ONE.toString())));
    }

    @Test
    @Transactional
    public void getOneTwo() throws Exception {
        // Initialize the database
        oneTwoRepository.saveAndFlush(oneTwo);

        // Get the oneTwo
        restOneTwoMockMvc.perform(get("/api/oneTwos/{id}", oneTwo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(oneTwo.getId().intValue()))
            .andExpect(jsonPath("$.one").value(DEFAULT_ONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOneTwo() throws Exception {
        // Get the oneTwo
        restOneTwoMockMvc.perform(get("/api/oneTwos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOneTwo() throws Exception {
        // Initialize the database
        oneTwoRepository.saveAndFlush(oneTwo);

		int databaseSizeBeforeUpdate = oneTwoRepository.findAll().size();

        // Update the oneTwo
        oneTwo.setOne(UPDATED_ONE);
        restOneTwoMockMvc.perform(put("/api/oneTwos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oneTwo)))
                .andExpect(status().isOk());

        // Validate the OneTwo in the database
        List<OneTwo> oneTwos = oneTwoRepository.findAll();
        assertThat(oneTwos).hasSize(databaseSizeBeforeUpdate);
        OneTwo testOneTwo = oneTwos.get(oneTwos.size() - 1);
        assertThat(testOneTwo.getOne()).isEqualTo(UPDATED_ONE);
    }

    @Test
    @Transactional
    public void deleteOneTwo() throws Exception {
        // Initialize the database
        oneTwoRepository.saveAndFlush(oneTwo);

		int databaseSizeBeforeDelete = oneTwoRepository.findAll().size();

        // Get the oneTwo
        restOneTwoMockMvc.perform(delete("/api/oneTwos/{id}", oneTwo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<OneTwo> oneTwos = oneTwoRepository.findAll();
        assertThat(oneTwos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
