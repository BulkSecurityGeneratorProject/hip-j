package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Test02;
import com.mycompany.myapp.repository.Test02Repository;

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
 * Test class for the Test02Resource REST controller.
 *
 * @see Test02Resource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Test02ResourceTest {

    private static final String DEFAULT_ASDF = "SAMPLE_TEXT";
    private static final String UPDATED_ASDF = "UPDATED_TEXT";
    private static final String DEFAULT_ZXCV = "SAMPLE_TEXT";
    private static final String UPDATED_ZXCV = "UPDATED_TEXT";
    private static final String DEFAULT_QWER = "SAMPLE_TEXT";
    private static final String UPDATED_QWER = "UPDATED_TEXT";

    @Inject
    private Test02Repository test02Repository;

    private MockMvc restTest02MockMvc;

    private Test02 test02;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Test02Resource test02Resource = new Test02Resource();
        ReflectionTestUtils.setField(test02Resource, "test02Repository", test02Repository);
        this.restTest02MockMvc = MockMvcBuilders.standaloneSetup(test02Resource).build();
    }

    @Before
    public void initTest() {
        test02 = new Test02();
        test02.setAsdf(DEFAULT_ASDF);
        test02.setZxcv(DEFAULT_ZXCV);
        test02.setQwer(DEFAULT_QWER);
    }

    @Test
    @Transactional
    public void createTest02() throws Exception {
        int databaseSizeBeforeCreate = test02Repository.findAll().size();

        // Create the Test02
        restTest02MockMvc.perform(post("/api/test02s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test02)))
                .andExpect(status().isCreated());

        // Validate the Test02 in the database
        List<Test02> test02s = test02Repository.findAll();
        assertThat(test02s).hasSize(databaseSizeBeforeCreate + 1);
        Test02 testTest02 = test02s.get(test02s.size() - 1);
        assertThat(testTest02.getAsdf()).isEqualTo(DEFAULT_ASDF);
        assertThat(testTest02.getZxcv()).isEqualTo(DEFAULT_ZXCV);
        assertThat(testTest02.getQwer()).isEqualTo(DEFAULT_QWER);
    }

    @Test
    @Transactional
    public void getAllTest02s() throws Exception {
        // Initialize the database
        test02Repository.saveAndFlush(test02);

        // Get all the test02s
        restTest02MockMvc.perform(get("/api/test02s"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(test02.getId().intValue())))
                .andExpect(jsonPath("$.[*].asdf").value(hasItem(DEFAULT_ASDF.toString())))
                .andExpect(jsonPath("$.[*].zxcv").value(hasItem(DEFAULT_ZXCV.toString())))
                .andExpect(jsonPath("$.[*].qwer").value(hasItem(DEFAULT_QWER.toString())));
    }

    @Test
    @Transactional
    public void getTest02() throws Exception {
        // Initialize the database
        test02Repository.saveAndFlush(test02);

        // Get the test02
        restTest02MockMvc.perform(get("/api/test02s/{id}", test02.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(test02.getId().intValue()))
            .andExpect(jsonPath("$.asdf").value(DEFAULT_ASDF.toString()))
            .andExpect(jsonPath("$.zxcv").value(DEFAULT_ZXCV.toString()))
            .andExpect(jsonPath("$.qwer").value(DEFAULT_QWER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTest02() throws Exception {
        // Get the test02
        restTest02MockMvc.perform(get("/api/test02s/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTest02() throws Exception {
        // Initialize the database
        test02Repository.saveAndFlush(test02);

		int databaseSizeBeforeUpdate = test02Repository.findAll().size();

        // Update the test02
        test02.setAsdf(UPDATED_ASDF);
        test02.setZxcv(UPDATED_ZXCV);
        test02.setQwer(UPDATED_QWER);
        restTest02MockMvc.perform(put("/api/test02s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test02)))
                .andExpect(status().isOk());

        // Validate the Test02 in the database
        List<Test02> test02s = test02Repository.findAll();
        assertThat(test02s).hasSize(databaseSizeBeforeUpdate);
        Test02 testTest02 = test02s.get(test02s.size() - 1);
        assertThat(testTest02.getAsdf()).isEqualTo(UPDATED_ASDF);
        assertThat(testTest02.getZxcv()).isEqualTo(UPDATED_ZXCV);
        assertThat(testTest02.getQwer()).isEqualTo(UPDATED_QWER);
    }

    @Test
    @Transactional
    public void deleteTest02() throws Exception {
        // Initialize the database
        test02Repository.saveAndFlush(test02);

		int databaseSizeBeforeDelete = test02Repository.findAll().size();

        // Get the test02
        restTest02MockMvc.perform(delete("/api/test02s/{id}", test02.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Test02> test02s = test02Repository.findAll();
        assertThat(test02s).hasSize(databaseSizeBeforeDelete - 1);
    }
}
