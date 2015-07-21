package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Test05;
import com.mycompany.myapp.repository.Test05Repository;

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
 * Test class for the Test05Resource REST controller.
 *
 * @see Test05Resource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Test05ResourceTest {

    private static final String DEFAULT_QWER = "SAMPLE_TEXT";
    private static final String UPDATED_QWER = "UPDATED_TEXT";
    private static final String DEFAULT_ASDF = "SAMPLE_TEXT";
    private static final String UPDATED_ASDF = "UPDATED_TEXT";
    private static final String DEFAULT_ZXCV = "SAMPLE_TEXT";
    private static final String UPDATED_ZXCV = "UPDATED_TEXT";

    @Inject
    private Test05Repository test05Repository;

    private MockMvc restTest05MockMvc;

    private Test05 test05;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Test05Resource test05Resource = new Test05Resource();
        ReflectionTestUtils.setField(test05Resource, "test05Repository", test05Repository);
        this.restTest05MockMvc = MockMvcBuilders.standaloneSetup(test05Resource).build();
    }

    @Before
    public void initTest() {
        test05 = new Test05();
        test05.setQwer(DEFAULT_QWER);
        test05.setAsdf(DEFAULT_ASDF);
        test05.setZxcv(DEFAULT_ZXCV);
    }

    @Test
    @Transactional
    public void createTest05() throws Exception {
        int databaseSizeBeforeCreate = test05Repository.findAll().size();

        // Create the Test05
        restTest05MockMvc.perform(post("/api/test05s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test05)))
                .andExpect(status().isCreated());

        // Validate the Test05 in the database
        List<Test05> test05s = test05Repository.findAll();
        assertThat(test05s).hasSize(databaseSizeBeforeCreate + 1);
        Test05 testTest05 = test05s.get(test05s.size() - 1);
        assertThat(testTest05.getQwer()).isEqualTo(DEFAULT_QWER);
        assertThat(testTest05.getAsdf()).isEqualTo(DEFAULT_ASDF);
        assertThat(testTest05.getZxcv()).isEqualTo(DEFAULT_ZXCV);
    }

    @Test
    @Transactional
    public void getAllTest05s() throws Exception {
        // Initialize the database
        test05Repository.saveAndFlush(test05);

        // Get all the test05s
        restTest05MockMvc.perform(get("/api/test05s"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(test05.getId().intValue())))
                .andExpect(jsonPath("$.[*].qwer").value(hasItem(DEFAULT_QWER.toString())))
                .andExpect(jsonPath("$.[*].asdf").value(hasItem(DEFAULT_ASDF.toString())))
                .andExpect(jsonPath("$.[*].zxcv").value(hasItem(DEFAULT_ZXCV.toString())));
    }

    @Test
    @Transactional
    public void getTest05() throws Exception {
        // Initialize the database
        test05Repository.saveAndFlush(test05);

        // Get the test05
        restTest05MockMvc.perform(get("/api/test05s/{id}", test05.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(test05.getId().intValue()))
            .andExpect(jsonPath("$.qwer").value(DEFAULT_QWER.toString()))
            .andExpect(jsonPath("$.asdf").value(DEFAULT_ASDF.toString()))
            .andExpect(jsonPath("$.zxcv").value(DEFAULT_ZXCV.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTest05() throws Exception {
        // Get the test05
        restTest05MockMvc.perform(get("/api/test05s/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTest05() throws Exception {
        // Initialize the database
        test05Repository.saveAndFlush(test05);

		int databaseSizeBeforeUpdate = test05Repository.findAll().size();

        // Update the test05
        test05.setQwer(UPDATED_QWER);
        test05.setAsdf(UPDATED_ASDF);
        test05.setZxcv(UPDATED_ZXCV);
        restTest05MockMvc.perform(put("/api/test05s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test05)))
                .andExpect(status().isOk());

        // Validate the Test05 in the database
        List<Test05> test05s = test05Repository.findAll();
        assertThat(test05s).hasSize(databaseSizeBeforeUpdate);
        Test05 testTest05 = test05s.get(test05s.size() - 1);
        assertThat(testTest05.getQwer()).isEqualTo(UPDATED_QWER);
        assertThat(testTest05.getAsdf()).isEqualTo(UPDATED_ASDF);
        assertThat(testTest05.getZxcv()).isEqualTo(UPDATED_ZXCV);
    }

    @Test
    @Transactional
    public void deleteTest05() throws Exception {
        // Initialize the database
        test05Repository.saveAndFlush(test05);

		int databaseSizeBeforeDelete = test05Repository.findAll().size();

        // Get the test05
        restTest05MockMvc.perform(delete("/api/test05s/{id}", test05.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Test05> test05s = test05Repository.findAll();
        assertThat(test05s).hasSize(databaseSizeBeforeDelete - 1);
    }
}
