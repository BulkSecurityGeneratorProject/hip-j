package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Stock_airwaybill;
import com.mycompany.myapp.repository.Stock_airwaybillRepository;

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
 * Test class for the Stock_airwaybillResource REST controller.
 *
 * @see Stock_airwaybillResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Stock_airwaybillResourceTest {

    private static final String DEFAULT_AWB = "SAMPLE_TEXT";
    private static final String UPDATED_AWB = "UPDATED_TEXT";
    private static final String DEFAULT_STATUS = "SAMPLE_TEXT";
    private static final String UPDATED_STATUS = "UPDATED_TEXT";

    @Inject
    private Stock_airwaybillRepository stock_airwaybillRepository;

    private MockMvc restStock_airwaybillMockMvc;

    private Stock_airwaybill stock_airwaybill;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Stock_airwaybillResource stock_airwaybillResource = new Stock_airwaybillResource();
        ReflectionTestUtils.setField(stock_airwaybillResource, "stock_airwaybillRepository", stock_airwaybillRepository);
        this.restStock_airwaybillMockMvc = MockMvcBuilders.standaloneSetup(stock_airwaybillResource).build();
    }

    @Before
    public void initTest() {
        stock_airwaybill = new Stock_airwaybill();
        stock_airwaybill.setAwb(DEFAULT_AWB);
        stock_airwaybill.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createStock_airwaybill() throws Exception {
        int databaseSizeBeforeCreate = stock_airwaybillRepository.findAll().size();

        // Create the Stock_airwaybill
        restStock_airwaybillMockMvc.perform(post("/api/stock_airwaybills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stock_airwaybill)))
                .andExpect(status().isCreated());

        // Validate the Stock_airwaybill in the database
        List<Stock_airwaybill> stock_airwaybills = stock_airwaybillRepository.findAll();
        assertThat(stock_airwaybills).hasSize(databaseSizeBeforeCreate + 1);
        Stock_airwaybill testStock_airwaybill = stock_airwaybills.get(stock_airwaybills.size() - 1);
        assertThat(testStock_airwaybill.getAwb()).isEqualTo(DEFAULT_AWB);
        assertThat(testStock_airwaybill.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkAwbIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(stock_airwaybillRepository.findAll()).hasSize(0);
        // set the field null
        stock_airwaybill.setAwb(null);

        // Create the Stock_airwaybill, which fails.
        restStock_airwaybillMockMvc.perform(post("/api/stock_airwaybills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stock_airwaybill)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Stock_airwaybill> stock_airwaybills = stock_airwaybillRepository.findAll();
        assertThat(stock_airwaybills).hasSize(0);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(stock_airwaybillRepository.findAll()).hasSize(0);
        // set the field null
        stock_airwaybill.setStatus(null);

        // Create the Stock_airwaybill, which fails.
        restStock_airwaybillMockMvc.perform(post("/api/stock_airwaybills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stock_airwaybill)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Stock_airwaybill> stock_airwaybills = stock_airwaybillRepository.findAll();
        assertThat(stock_airwaybills).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllStock_airwaybills() throws Exception {
        // Initialize the database
        stock_airwaybillRepository.saveAndFlush(stock_airwaybill);

        // Get all the stock_airwaybills
        restStock_airwaybillMockMvc.perform(get("/api/stock_airwaybills"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(stock_airwaybill.getId().intValue())))
                .andExpect(jsonPath("$.[*].awb").value(hasItem(DEFAULT_AWB.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getStock_airwaybill() throws Exception {
        // Initialize the database
        stock_airwaybillRepository.saveAndFlush(stock_airwaybill);

        // Get the stock_airwaybill
        restStock_airwaybillMockMvc.perform(get("/api/stock_airwaybills/{id}", stock_airwaybill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(stock_airwaybill.getId().intValue()))
            .andExpect(jsonPath("$.awb").value(DEFAULT_AWB.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStock_airwaybill() throws Exception {
        // Get the stock_airwaybill
        restStock_airwaybillMockMvc.perform(get("/api/stock_airwaybills/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStock_airwaybill() throws Exception {
        // Initialize the database
        stock_airwaybillRepository.saveAndFlush(stock_airwaybill);

		int databaseSizeBeforeUpdate = stock_airwaybillRepository.findAll().size();

        // Update the stock_airwaybill
        stock_airwaybill.setAwb(UPDATED_AWB);
        stock_airwaybill.setStatus(UPDATED_STATUS);
        restStock_airwaybillMockMvc.perform(put("/api/stock_airwaybills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stock_airwaybill)))
                .andExpect(status().isOk());

        // Validate the Stock_airwaybill in the database
        List<Stock_airwaybill> stock_airwaybills = stock_airwaybillRepository.findAll();
        assertThat(stock_airwaybills).hasSize(databaseSizeBeforeUpdate);
        Stock_airwaybill testStock_airwaybill = stock_airwaybills.get(stock_airwaybills.size() - 1);
        assertThat(testStock_airwaybill.getAwb()).isEqualTo(UPDATED_AWB);
        assertThat(testStock_airwaybill.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteStock_airwaybill() throws Exception {
        // Initialize the database
        stock_airwaybillRepository.saveAndFlush(stock_airwaybill);

		int databaseSizeBeforeDelete = stock_airwaybillRepository.findAll().size();

        // Get the stock_airwaybill
        restStock_airwaybillMockMvc.perform(delete("/api/stock_airwaybills/{id}", stock_airwaybill.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Stock_airwaybill> stock_airwaybills = stock_airwaybillRepository.findAll();
        assertThat(stock_airwaybills).hasSize(databaseSizeBeforeDelete - 1);
    }
}
