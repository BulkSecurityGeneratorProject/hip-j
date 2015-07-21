package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Test03;
import com.mycompany.myapp.repository.Test03Repository;

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
 * Test class for the Test03Resource REST controller.
 *
 * @see Test03Resource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Test03ResourceTest {

    private static final String DEFAULT_ASDF = "SAMPLE_TEXT";
    private static final String UPDATED_ASDF = "UPDATED_TEXT";

    @Inject
    private Test03Repository test03Repository;

    private MockMvc restTest03MockMvc;

    private Test03 test03;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Test03Resource test03Resource = new Test03Resource();
        ReflectionTestUtils.setField(test03Resource, "test03Repository", test03Repository);
        this.restTest03MockMvc = MockMvcBuilders.standaloneSetup(test03Resource).build();
    }

    @Before
    public void initTest() {
        test03 = new Test03();
        test03.setAsdf(DEFAULT_ASDF);
    }

    @Test
    @Transactional
    public void createTest03() throws Exception {
        int databaseSizeBeforeCreate = test03Repository.findAll().size();

        // Create the Test03
        restTest03MockMvc.perform(post("/api/test03s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test03)))
                .andExpect(status().isCreated());

        // Validate the Test03 in the database
        List<Test03> test03s = test03Repository.findAll();
        assertThat(test03s).hasSize(databaseSizeBeforeCreate + 1);
        Test03 testTest03 = test03s.get(test03s.size() - 1);
        assertThat(testTest03.getAsdf()).isEqualTo(DEFAULT_ASDF);
    }

    @Test
    @Transactional
    public void getAllTest03s() throws Exception {
        // Initialize the database
        test03Repository.saveAndFlush(test03);

        // Get all the test03s
        restTest03MockMvc.perform(get("/api/test03s"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(test03.getId().intValue())))
                .andExpect(jsonPath("$.[*].asdf").value(hasItem(DEFAULT_ASDF.toString())));
    }

    @Test
    @Transactional
    public void getTest03() throws Exception {
        // Initialize the database
        test03Repository.saveAndFlush(test03);

        // Get the test03
        restTest03MockMvc.perform(get("/api/test03s/{id}", test03.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(test03.getId().intValue()))
            .andExpect(jsonPath("$.asdf").value(DEFAULT_ASDF.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTest03() throws Exception {
        // Get the test03
        restTest03MockMvc.perform(get("/api/test03s/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTest03() throws Exception {
        // Initialize the database
        test03Repository.saveAndFlush(test03);

		int databaseSizeBeforeUpdate = test03Repository.findAll().size();

        // Update the test03
        test03.setAsdf(UPDATED_ASDF);
        restTest03MockMvc.perform(put("/api/test03s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test03)))
                .andExpect(status().isOk());

        // Validate the Test03 in the database
        List<Test03> test03s = test03Repository.findAll();
        assertThat(test03s).hasSize(databaseSizeBeforeUpdate);
        Test03 testTest03 = test03s.get(test03s.size() - 1);
        assertThat(testTest03.getAsdf()).isEqualTo(UPDATED_ASDF);
    }

    @Test
    @Transactional
    public void deleteTest03() throws Exception {
        // Initialize the database
        test03Repository.saveAndFlush(test03);

		int databaseSizeBeforeDelete = test03Repository.findAll().size();

        // Get the test03
        restTest03MockMvc.perform(delete("/api/test03s/{id}", test03.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Test03> test03s = test03Repository.findAll();
        assertThat(test03s).hasSize(databaseSizeBeforeDelete - 1);
    }
}
