package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Tat_cost_matrix;
import com.mycompany.myapp.repository.Tat_cost_matrixRepository;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the Tat_cost_matrixResource REST controller.
 *
 * @see Tat_cost_matrixResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Tat_cost_matrixResourceTest {


    private static final BigDecimal DEFAULT_BASIC_COST = new BigDecimal(0);
    private static final BigDecimal UPDATED_BASIC_COST = new BigDecimal(1);

    private static final BigDecimal DEFAULT_FIXED_ADDON_COST = new BigDecimal(0);
    private static final BigDecimal UPDATED_FIXED_ADDON_COST = new BigDecimal(1);

    private static final BigDecimal DEFAULT_VARIABLE_ADDON_PERCENT = new BigDecimal(0);
    private static final BigDecimal UPDATED_VARIABLE_ADDON_PERCENT = new BigDecimal(1);

    private static final Long DEFAULT_TAT = 0L;
    private static final Long UPDATED_TAT = 1L;

    @Inject
    private Tat_cost_matrixRepository tat_cost_matrixRepository;

    private MockMvc restTat_cost_matrixMockMvc;

    private Tat_cost_matrix tat_cost_matrix;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tat_cost_matrixResource tat_cost_matrixResource = new Tat_cost_matrixResource();
        ReflectionTestUtils.setField(tat_cost_matrixResource, "tat_cost_matrixRepository", tat_cost_matrixRepository);
        this.restTat_cost_matrixMockMvc = MockMvcBuilders.standaloneSetup(tat_cost_matrixResource).build();
    }

    @Before
    public void initTest() {
        tat_cost_matrix = new Tat_cost_matrix();
        tat_cost_matrix.setBasic_cost(DEFAULT_BASIC_COST);
        tat_cost_matrix.setFixed_addon_cost(DEFAULT_FIXED_ADDON_COST);
        tat_cost_matrix.setVariable_addon_percent(DEFAULT_VARIABLE_ADDON_PERCENT);
        tat_cost_matrix.setTat(DEFAULT_TAT);
    }

    @Test
    @Transactional
    public void createTat_cost_matrix() throws Exception {
        int databaseSizeBeforeCreate = tat_cost_matrixRepository.findAll().size();

        // Create the Tat_cost_matrix
        restTat_cost_matrixMockMvc.perform(post("/api/tat_cost_matrixs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tat_cost_matrix)))
                .andExpect(status().isCreated());

        // Validate the Tat_cost_matrix in the database
        List<Tat_cost_matrix> tat_cost_matrixs = tat_cost_matrixRepository.findAll();
        assertThat(tat_cost_matrixs).hasSize(databaseSizeBeforeCreate + 1);
        Tat_cost_matrix testTat_cost_matrix = tat_cost_matrixs.get(tat_cost_matrixs.size() - 1);
        assertThat(testTat_cost_matrix.getBasic_cost()).isEqualTo(DEFAULT_BASIC_COST);
        assertThat(testTat_cost_matrix.getFixed_addon_cost()).isEqualTo(DEFAULT_FIXED_ADDON_COST);
        assertThat(testTat_cost_matrix.getVariable_addon_percent()).isEqualTo(DEFAULT_VARIABLE_ADDON_PERCENT);
        assertThat(testTat_cost_matrix.getTat()).isEqualTo(DEFAULT_TAT);
    }

    @Test
    @Transactional
    public void checkBasic_costIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(tat_cost_matrixRepository.findAll()).hasSize(0);
        // set the field null
        tat_cost_matrix.setBasic_cost(null);

        // Create the Tat_cost_matrix, which fails.
        restTat_cost_matrixMockMvc.perform(post("/api/tat_cost_matrixs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tat_cost_matrix)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Tat_cost_matrix> tat_cost_matrixs = tat_cost_matrixRepository.findAll();
        assertThat(tat_cost_matrixs).hasSize(0);
    }

    @Test
    @Transactional
    public void checkFixed_addon_costIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(tat_cost_matrixRepository.findAll()).hasSize(0);
        // set the field null
        tat_cost_matrix.setFixed_addon_cost(null);

        // Create the Tat_cost_matrix, which fails.
        restTat_cost_matrixMockMvc.perform(post("/api/tat_cost_matrixs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tat_cost_matrix)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Tat_cost_matrix> tat_cost_matrixs = tat_cost_matrixRepository.findAll();
        assertThat(tat_cost_matrixs).hasSize(0);
    }

    @Test
    @Transactional
    public void checkVariable_addon_percentIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(tat_cost_matrixRepository.findAll()).hasSize(0);
        // set the field null
        tat_cost_matrix.setVariable_addon_percent(null);

        // Create the Tat_cost_matrix, which fails.
        restTat_cost_matrixMockMvc.perform(post("/api/tat_cost_matrixs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tat_cost_matrix)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Tat_cost_matrix> tat_cost_matrixs = tat_cost_matrixRepository.findAll();
        assertThat(tat_cost_matrixs).hasSize(0);
    }

    @Test
    @Transactional
    public void checkTatIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(tat_cost_matrixRepository.findAll()).hasSize(0);
        // set the field null
        tat_cost_matrix.setTat(null);

        // Create the Tat_cost_matrix, which fails.
        restTat_cost_matrixMockMvc.perform(post("/api/tat_cost_matrixs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tat_cost_matrix)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Tat_cost_matrix> tat_cost_matrixs = tat_cost_matrixRepository.findAll();
        assertThat(tat_cost_matrixs).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllTat_cost_matrixs() throws Exception {
        // Initialize the database
        tat_cost_matrixRepository.saveAndFlush(tat_cost_matrix);

        // Get all the tat_cost_matrixs
        restTat_cost_matrixMockMvc.perform(get("/api/tat_cost_matrixs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(tat_cost_matrix.getId().intValue())))
                .andExpect(jsonPath("$.[*].basic_cost").value(hasItem(DEFAULT_BASIC_COST.intValue())))
                .andExpect(jsonPath("$.[*].fixed_addon_cost").value(hasItem(DEFAULT_FIXED_ADDON_COST.intValue())))
                .andExpect(jsonPath("$.[*].variable_addon_percent").value(hasItem(DEFAULT_VARIABLE_ADDON_PERCENT.intValue())))
                .andExpect(jsonPath("$.[*].tat").value(hasItem(DEFAULT_TAT.intValue())));
    }

    @Test
    @Transactional
    public void getTat_cost_matrix() throws Exception {
        // Initialize the database
        tat_cost_matrixRepository.saveAndFlush(tat_cost_matrix);

        // Get the tat_cost_matrix
        restTat_cost_matrixMockMvc.perform(get("/api/tat_cost_matrixs/{id}", tat_cost_matrix.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tat_cost_matrix.getId().intValue()))
            .andExpect(jsonPath("$.basic_cost").value(DEFAULT_BASIC_COST.intValue()))
            .andExpect(jsonPath("$.fixed_addon_cost").value(DEFAULT_FIXED_ADDON_COST.intValue()))
            .andExpect(jsonPath("$.variable_addon_percent").value(DEFAULT_VARIABLE_ADDON_PERCENT.intValue()))
            .andExpect(jsonPath("$.tat").value(DEFAULT_TAT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTat_cost_matrix() throws Exception {
        // Get the tat_cost_matrix
        restTat_cost_matrixMockMvc.perform(get("/api/tat_cost_matrixs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTat_cost_matrix() throws Exception {
        // Initialize the database
        tat_cost_matrixRepository.saveAndFlush(tat_cost_matrix);

		int databaseSizeBeforeUpdate = tat_cost_matrixRepository.findAll().size();

        // Update the tat_cost_matrix
        tat_cost_matrix.setBasic_cost(UPDATED_BASIC_COST);
        tat_cost_matrix.setFixed_addon_cost(UPDATED_FIXED_ADDON_COST);
        tat_cost_matrix.setVariable_addon_percent(UPDATED_VARIABLE_ADDON_PERCENT);
        tat_cost_matrix.setTat(UPDATED_TAT);
        restTat_cost_matrixMockMvc.perform(put("/api/tat_cost_matrixs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tat_cost_matrix)))
                .andExpect(status().isOk());

        // Validate the Tat_cost_matrix in the database
        List<Tat_cost_matrix> tat_cost_matrixs = tat_cost_matrixRepository.findAll();
        assertThat(tat_cost_matrixs).hasSize(databaseSizeBeforeUpdate);
        Tat_cost_matrix testTat_cost_matrix = tat_cost_matrixs.get(tat_cost_matrixs.size() - 1);
        assertThat(testTat_cost_matrix.getBasic_cost()).isEqualTo(UPDATED_BASIC_COST);
        assertThat(testTat_cost_matrix.getFixed_addon_cost()).isEqualTo(UPDATED_FIXED_ADDON_COST);
        assertThat(testTat_cost_matrix.getVariable_addon_percent()).isEqualTo(UPDATED_VARIABLE_ADDON_PERCENT);
        assertThat(testTat_cost_matrix.getTat()).isEqualTo(UPDATED_TAT);
    }

    @Test
    @Transactional
    public void deleteTat_cost_matrix() throws Exception {
        // Initialize the database
        tat_cost_matrixRepository.saveAndFlush(tat_cost_matrix);

		int databaseSizeBeforeDelete = tat_cost_matrixRepository.findAll().size();

        // Get the tat_cost_matrix
        restTat_cost_matrixMockMvc.perform(delete("/api/tat_cost_matrixs/{id}", tat_cost_matrix.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Tat_cost_matrix> tat_cost_matrixs = tat_cost_matrixRepository.findAll();
        assertThat(tat_cost_matrixs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
