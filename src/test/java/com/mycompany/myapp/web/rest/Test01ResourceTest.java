package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Test01;
import com.mycompany.myapp.repository.Test01Repository;

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
 * Test class for the Test01Resource REST controller.
 *
 * @see Test01Resource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Test01ResourceTest {

    private static final String DEFAULT_ASDF = "SAMPLE_TEXT";
    private static final String UPDATED_ASDF = "UPDATED_TEXT";
    private static final String DEFAULT_QWER = "SAMPLE_TEXT";
    private static final String UPDATED_QWER = "UPDATED_TEXT";

    @Inject
    private Test01Repository test01Repository;

    private MockMvc restTest01MockMvc;

    private Test01 test01;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Test01Resource test01Resource = new Test01Resource();
        ReflectionTestUtils.setField(test01Resource, "test01Repository", test01Repository);
        this.restTest01MockMvc = MockMvcBuilders.standaloneSetup(test01Resource).build();
    }

    @Before
    public void initTest() {
        test01 = new Test01();
        test01.setAsdf(DEFAULT_ASDF);
        test01.setQwer(DEFAULT_QWER);
    }

    @Test
    @Transactional
    public void createTest01() throws Exception {
        int databaseSizeBeforeCreate = test01Repository.findAll().size();

        // Create the Test01
        restTest01MockMvc.perform(post("/api/test01s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test01)))
                .andExpect(status().isCreated());

        // Validate the Test01 in the database
        List<Test01> test01s = test01Repository.findAll();
        assertThat(test01s).hasSize(databaseSizeBeforeCreate + 1);
        Test01 testTest01 = test01s.get(test01s.size() - 1);
        assertThat(testTest01.getAsdf()).isEqualTo(DEFAULT_ASDF);
        assertThat(testTest01.getQwer()).isEqualTo(DEFAULT_QWER);
    }

    @Test
    @Transactional
    public void getAllTest01s() throws Exception {
        // Initialize the database
        test01Repository.saveAndFlush(test01);

        // Get all the test01s
        restTest01MockMvc.perform(get("/api/test01s"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(test01.getId().intValue())))
                .andExpect(jsonPath("$.[*].asdf").value(hasItem(DEFAULT_ASDF.toString())))
                .andExpect(jsonPath("$.[*].qwer").value(hasItem(DEFAULT_QWER.toString())));
    }

    @Test
    @Transactional
    public void getTest01() throws Exception {
        // Initialize the database
        test01Repository.saveAndFlush(test01);

        // Get the test01
        restTest01MockMvc.perform(get("/api/test01s/{id}", test01.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(test01.getId().intValue()))
            .andExpect(jsonPath("$.asdf").value(DEFAULT_ASDF.toString()))
            .andExpect(jsonPath("$.qwer").value(DEFAULT_QWER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTest01() throws Exception {
        // Get the test01
        restTest01MockMvc.perform(get("/api/test01s/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTest01() throws Exception {
        // Initialize the database
        test01Repository.saveAndFlush(test01);

		int databaseSizeBeforeUpdate = test01Repository.findAll().size();

        // Update the test01
        test01.setAsdf(UPDATED_ASDF);
        test01.setQwer(UPDATED_QWER);
        restTest01MockMvc.perform(put("/api/test01s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test01)))
                .andExpect(status().isOk());

        // Validate the Test01 in the database
        List<Test01> test01s = test01Repository.findAll();
        assertThat(test01s).hasSize(databaseSizeBeforeUpdate);
        Test01 testTest01 = test01s.get(test01s.size() - 1);
        assertThat(testTest01.getAsdf()).isEqualTo(UPDATED_ASDF);
        assertThat(testTest01.getQwer()).isEqualTo(UPDATED_QWER);
    }

    @Test
    @Transactional
    public void deleteTest01() throws Exception {
        // Initialize the database
        test01Repository.saveAndFlush(test01);

		int databaseSizeBeforeDelete = test01Repository.findAll().size();

        // Get the test01
        restTest01MockMvc.perform(delete("/api/test01s/{id}", test01.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Test01> test01s = test01Repository.findAll();
        assertThat(test01s).hasSize(databaseSizeBeforeDelete - 1);
    }
}
