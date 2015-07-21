package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Test04;
import com.mycompany.myapp.repository.Test04Repository;

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
 * Test class for the Test04Resource REST controller.
 *
 * @see Test04Resource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Test04ResourceTest {

    private static final String DEFAULT_QWER = "SAMPLE_TEXT";
    private static final String UPDATED_QWER = "UPDATED_TEXT";
    private static final String DEFAULT_ASDF = "SAMPLE_TEXT";
    private static final String UPDATED_ASDF = "UPDATED_TEXT";
    private static final String DEFAULT_ZXCV = "SAMPLE_TEXT";
    private static final String UPDATED_ZXCV = "UPDATED_TEXT";

    @Inject
    private Test04Repository test04Repository;

    private MockMvc restTest04MockMvc;

    private Test04 test04;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Test04Resource test04Resource = new Test04Resource();
        ReflectionTestUtils.setField(test04Resource, "test04Repository", test04Repository);
        this.restTest04MockMvc = MockMvcBuilders.standaloneSetup(test04Resource).build();
    }

    @Before
    public void initTest() {
        test04 = new Test04();
        test04.setQwer(DEFAULT_QWER);
        test04.setAsdf(DEFAULT_ASDF);
        test04.setZxcv(DEFAULT_ZXCV);
    }

    @Test
    @Transactional
    public void createTest04() throws Exception {
        int databaseSizeBeforeCreate = test04Repository.findAll().size();

        // Create the Test04
        restTest04MockMvc.perform(post("/api/test04s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test04)))
                .andExpect(status().isCreated());

        // Validate the Test04 in the database
        List<Test04> test04s = test04Repository.findAll();
        assertThat(test04s).hasSize(databaseSizeBeforeCreate + 1);
        Test04 testTest04 = test04s.get(test04s.size() - 1);
        assertThat(testTest04.getQwer()).isEqualTo(DEFAULT_QWER);
        assertThat(testTest04.getAsdf()).isEqualTo(DEFAULT_ASDF);
        assertThat(testTest04.getZxcv()).isEqualTo(DEFAULT_ZXCV);
    }

    @Test
    @Transactional
    public void getAllTest04s() throws Exception {
        // Initialize the database
        test04Repository.saveAndFlush(test04);

        // Get all the test04s
        restTest04MockMvc.perform(get("/api/test04s"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(test04.getId().intValue())))
                .andExpect(jsonPath("$.[*].qwer").value(hasItem(DEFAULT_QWER.toString())))
                .andExpect(jsonPath("$.[*].asdf").value(hasItem(DEFAULT_ASDF.toString())))
                .andExpect(jsonPath("$.[*].zxcv").value(hasItem(DEFAULT_ZXCV.toString())));
    }

    @Test
    @Transactional
    public void getTest04() throws Exception {
        // Initialize the database
        test04Repository.saveAndFlush(test04);

        // Get the test04
        restTest04MockMvc.perform(get("/api/test04s/{id}", test04.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(test04.getId().intValue()))
            .andExpect(jsonPath("$.qwer").value(DEFAULT_QWER.toString()))
            .andExpect(jsonPath("$.asdf").value(DEFAULT_ASDF.toString()))
            .andExpect(jsonPath("$.zxcv").value(DEFAULT_ZXCV.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTest04() throws Exception {
        // Get the test04
        restTest04MockMvc.perform(get("/api/test04s/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTest04() throws Exception {
        // Initialize the database
        test04Repository.saveAndFlush(test04);

		int databaseSizeBeforeUpdate = test04Repository.findAll().size();

        // Update the test04
        test04.setQwer(UPDATED_QWER);
        test04.setAsdf(UPDATED_ASDF);
        test04.setZxcv(UPDATED_ZXCV);
        restTest04MockMvc.perform(put("/api/test04s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test04)))
                .andExpect(status().isOk());

        // Validate the Test04 in the database
        List<Test04> test04s = test04Repository.findAll();
        assertThat(test04s).hasSize(databaseSizeBeforeUpdate);
        Test04 testTest04 = test04s.get(test04s.size() - 1);
        assertThat(testTest04.getQwer()).isEqualTo(UPDATED_QWER);
        assertThat(testTest04.getAsdf()).isEqualTo(UPDATED_ASDF);
        assertThat(testTest04.getZxcv()).isEqualTo(UPDATED_ZXCV);
    }

    @Test
    @Transactional
    public void deleteTest04() throws Exception {
        // Initialize the database
        test04Repository.saveAndFlush(test04);

		int databaseSizeBeforeDelete = test04Repository.findAll().size();

        // Get the test04
        restTest04MockMvc.perform(delete("/api/test04s/{id}", test04.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Test04> test04s = test04Repository.findAll();
        assertThat(test04s).hasSize(databaseSizeBeforeDelete - 1);
    }
}
