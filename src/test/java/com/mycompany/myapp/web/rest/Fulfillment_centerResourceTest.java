package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Fulfillment_center;
import com.mycompany.myapp.repository.Fulfillment_centerRepository;

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
 * Test class for the Fulfillment_centerResource REST controller.
 *
 * @see Fulfillment_centerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Fulfillment_centerResourceTest {

    private static final String DEFAULT_FCID = "SAMPLE_TEXT";
    private static final String UPDATED_FCID = "UPDATED_TEXT";
    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";
    private static final String DEFAULT_FC_TYPE = "SAMPLE_TEXT";
    private static final String UPDATED_FC_TYPE = "UPDATED_TEXT";
    private static final String DEFAULT_FRANCHISE = "SAMPLE_TEXT";
    private static final String UPDATED_FRANCHISE = "UPDATED_TEXT";
    private static final String DEFAULT_ADDRESS1 = "SAMPLE_TEXT";
    private static final String UPDATED_ADDRESS1 = "UPDATED_TEXT";
    private static final String DEFAULT_ADDRESS2 = "SAMPLE_TEXT";
    private static final String UPDATED_ADDRESS2 = "UPDATED_TEXT";
    private static final String DEFAULT_ADDRESS3 = "SAMPLE_TEXT";
    private static final String UPDATED_ADDRESS3 = "UPDATED_TEXT";
    private static final String DEFAULT_PINCODE = "SAMPLE_TEXT";
    private static final String UPDATED_PINCODE = "UPDATED_TEXT";

    private static final Integer DEFAULT_O2S_TAT = 0;
    private static final Integer UPDATED_O2S_TAT = 1;

    private static final Boolean DEFAULT_IS_ENABLED = false;
    private static final Boolean UPDATED_IS_ENABLED = true;

    @Inject
    private Fulfillment_centerRepository fulfillment_centerRepository;

    private MockMvc restFulfillment_centerMockMvc;

    private Fulfillment_center fulfillment_center;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Fulfillment_centerResource fulfillment_centerResource = new Fulfillment_centerResource();
        ReflectionTestUtils.setField(fulfillment_centerResource, "fulfillment_centerRepository", fulfillment_centerRepository);
        this.restFulfillment_centerMockMvc = MockMvcBuilders.standaloneSetup(fulfillment_centerResource).build();
    }

    @Before
    public void initTest() {
        fulfillment_center = new Fulfillment_center();
        fulfillment_center.setFcid(DEFAULT_FCID);
        fulfillment_center.setName(DEFAULT_NAME);
        fulfillment_center.setDescription(DEFAULT_DESCRIPTION);
        fulfillment_center.setFc_type(DEFAULT_FC_TYPE);
        fulfillment_center.setFranchise(DEFAULT_FRANCHISE);
        fulfillment_center.setAddress1(DEFAULT_ADDRESS1);
        fulfillment_center.setAddress2(DEFAULT_ADDRESS2);
        fulfillment_center.setAddress3(DEFAULT_ADDRESS3);
        fulfillment_center.setPincode(DEFAULT_PINCODE);
        fulfillment_center.seto2s_tat(DEFAULT_O2S_TAT);
        fulfillment_center.setIs_enabled(DEFAULT_IS_ENABLED);
    }

    @Test
    @Transactional
    public void createFulfillment_center() throws Exception {
        int databaseSizeBeforeCreate = fulfillment_centerRepository.findAll().size();

        // Create the Fulfillment_center
        restFulfillment_centerMockMvc.perform(post("/api/fulfillment_centers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fulfillment_center)))
                .andExpect(status().isCreated());

        // Validate the Fulfillment_center in the database
        List<Fulfillment_center> fulfillment_centers = fulfillment_centerRepository.findAll();
        assertThat(fulfillment_centers).hasSize(databaseSizeBeforeCreate + 1);
        Fulfillment_center testFulfillment_center = fulfillment_centers.get(fulfillment_centers.size() - 1);
        assertThat(testFulfillment_center.getFcid()).isEqualTo(DEFAULT_FCID);
        assertThat(testFulfillment_center.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFulfillment_center.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFulfillment_center.getFc_type()).isEqualTo(DEFAULT_FC_TYPE);
        assertThat(testFulfillment_center.getFranchise()).isEqualTo(DEFAULT_FRANCHISE);
        assertThat(testFulfillment_center.getAddress1()).isEqualTo(DEFAULT_ADDRESS1);
        assertThat(testFulfillment_center.getAddress2()).isEqualTo(DEFAULT_ADDRESS2);
        assertThat(testFulfillment_center.getAddress3()).isEqualTo(DEFAULT_ADDRESS3);
        assertThat(testFulfillment_center.getPincode()).isEqualTo(DEFAULT_PINCODE);
        assertThat(testFulfillment_center.geto2s_tat()).isEqualTo(DEFAULT_O2S_TAT);
        assertThat(testFulfillment_center.getIs_enabled()).isEqualTo(DEFAULT_IS_ENABLED);
    }

    @Test
    @Transactional
    public void checkFcidIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(fulfillment_centerRepository.findAll()).hasSize(0);
        // set the field null
        fulfillment_center.setFcid(null);

        // Create the Fulfillment_center, which fails.
        restFulfillment_centerMockMvc.perform(post("/api/fulfillment_centers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fulfillment_center)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Fulfillment_center> fulfillment_centers = fulfillment_centerRepository.findAll();
        assertThat(fulfillment_centers).hasSize(0);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(fulfillment_centerRepository.findAll()).hasSize(0);
        // set the field null
        fulfillment_center.setName(null);

        // Create the Fulfillment_center, which fails.
        restFulfillment_centerMockMvc.perform(post("/api/fulfillment_centers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fulfillment_center)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Fulfillment_center> fulfillment_centers = fulfillment_centerRepository.findAll();
        assertThat(fulfillment_centers).hasSize(0);
    }

    @Test
    @Transactional
    public void checkFc_typeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(fulfillment_centerRepository.findAll()).hasSize(0);
        // set the field null
        fulfillment_center.setFc_type(null);

        // Create the Fulfillment_center, which fails.
        restFulfillment_centerMockMvc.perform(post("/api/fulfillment_centers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fulfillment_center)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Fulfillment_center> fulfillment_centers = fulfillment_centerRepository.findAll();
        assertThat(fulfillment_centers).hasSize(0);
    }

    @Test
    @Transactional
    public void checkFranchiseIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(fulfillment_centerRepository.findAll()).hasSize(0);
        // set the field null
        fulfillment_center.setFranchise(null);

        // Create the Fulfillment_center, which fails.
        restFulfillment_centerMockMvc.perform(post("/api/fulfillment_centers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fulfillment_center)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Fulfillment_center> fulfillment_centers = fulfillment_centerRepository.findAll();
        assertThat(fulfillment_centers).hasSize(0);
    }

    @Test
    @Transactional
    public void checkPincodeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(fulfillment_centerRepository.findAll()).hasSize(0);
        // set the field null
        fulfillment_center.setPincode(null);

        // Create the Fulfillment_center, which fails.
        restFulfillment_centerMockMvc.perform(post("/api/fulfillment_centers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fulfillment_center)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Fulfillment_center> fulfillment_centers = fulfillment_centerRepository.findAll();
        assertThat(fulfillment_centers).hasSize(0);
    }

    @Test
    @Transactional
    public void checko2s_tatIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(fulfillment_centerRepository.findAll()).hasSize(0);
        // set the field null
        fulfillment_center.seto2s_tat(null);

        // Create the Fulfillment_center, which fails.
        restFulfillment_centerMockMvc.perform(post("/api/fulfillment_centers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fulfillment_center)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Fulfillment_center> fulfillment_centers = fulfillment_centerRepository.findAll();
        assertThat(fulfillment_centers).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllFulfillment_centers() throws Exception {
        // Initialize the database
        fulfillment_centerRepository.saveAndFlush(fulfillment_center);

        // Get all the fulfillment_centers
        restFulfillment_centerMockMvc.perform(get("/api/fulfillment_centers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(fulfillment_center.getId().intValue())))
                .andExpect(jsonPath("$.[*].fcid").value(hasItem(DEFAULT_FCID.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].fc_type").value(hasItem(DEFAULT_FC_TYPE.toString())))
                .andExpect(jsonPath("$.[*].franchise").value(hasItem(DEFAULT_FRANCHISE.toString())))
                .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS1.toString())))
                .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS2.toString())))
                .andExpect(jsonPath("$.[*].address3").value(hasItem(DEFAULT_ADDRESS3.toString())))
                .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE.toString())))
                .andExpect(jsonPath("$.[*].o2s_tat").value(hasItem(DEFAULT_O2S_TAT)))
                .andExpect(jsonPath("$.[*].is_enabled").value(hasItem(DEFAULT_IS_ENABLED.booleanValue())));
    }

    @Test
    @Transactional
    public void getFulfillment_center() throws Exception {
        // Initialize the database
        fulfillment_centerRepository.saveAndFlush(fulfillment_center);

        // Get the fulfillment_center
        restFulfillment_centerMockMvc.perform(get("/api/fulfillment_centers/{id}", fulfillment_center.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(fulfillment_center.getId().intValue()))
            .andExpect(jsonPath("$.fcid").value(DEFAULT_FCID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.fc_type").value(DEFAULT_FC_TYPE.toString()))
            .andExpect(jsonPath("$.franchise").value(DEFAULT_FRANCHISE.toString()))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS1.toString()))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS2.toString()))
            .andExpect(jsonPath("$.address3").value(DEFAULT_ADDRESS3.toString()))
            .andExpect(jsonPath("$.pincode").value(DEFAULT_PINCODE.toString()))
            .andExpect(jsonPath("$.o2s_tat").value(DEFAULT_O2S_TAT))
            .andExpect(jsonPath("$.is_enabled").value(DEFAULT_IS_ENABLED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFulfillment_center() throws Exception {
        // Get the fulfillment_center
        restFulfillment_centerMockMvc.perform(get("/api/fulfillment_centers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFulfillment_center() throws Exception {
        // Initialize the database
        fulfillment_centerRepository.saveAndFlush(fulfillment_center);

		int databaseSizeBeforeUpdate = fulfillment_centerRepository.findAll().size();

        // Update the fulfillment_center
        fulfillment_center.setFcid(UPDATED_FCID);
        fulfillment_center.setName(UPDATED_NAME);
        fulfillment_center.setDescription(UPDATED_DESCRIPTION);
        fulfillment_center.setFc_type(UPDATED_FC_TYPE);
        fulfillment_center.setFranchise(UPDATED_FRANCHISE);
        fulfillment_center.setAddress1(UPDATED_ADDRESS1);
        fulfillment_center.setAddress2(UPDATED_ADDRESS2);
        fulfillment_center.setAddress3(UPDATED_ADDRESS3);
        fulfillment_center.setPincode(UPDATED_PINCODE);
        fulfillment_center.seto2s_tat(UPDATED_O2S_TAT);
        fulfillment_center.setIs_enabled(UPDATED_IS_ENABLED);
        restFulfillment_centerMockMvc.perform(put("/api/fulfillment_centers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fulfillment_center)))
                .andExpect(status().isOk());

        // Validate the Fulfillment_center in the database
        List<Fulfillment_center> fulfillment_centers = fulfillment_centerRepository.findAll();
        assertThat(fulfillment_centers).hasSize(databaseSizeBeforeUpdate);
        Fulfillment_center testFulfillment_center = fulfillment_centers.get(fulfillment_centers.size() - 1);
        assertThat(testFulfillment_center.getFcid()).isEqualTo(UPDATED_FCID);
        assertThat(testFulfillment_center.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFulfillment_center.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFulfillment_center.getFc_type()).isEqualTo(UPDATED_FC_TYPE);
        assertThat(testFulfillment_center.getFranchise()).isEqualTo(UPDATED_FRANCHISE);
        assertThat(testFulfillment_center.getAddress1()).isEqualTo(UPDATED_ADDRESS1);
        assertThat(testFulfillment_center.getAddress2()).isEqualTo(UPDATED_ADDRESS2);
        assertThat(testFulfillment_center.getAddress3()).isEqualTo(UPDATED_ADDRESS3);
        assertThat(testFulfillment_center.getPincode()).isEqualTo(UPDATED_PINCODE);
        assertThat(testFulfillment_center.geto2s_tat()).isEqualTo(UPDATED_O2S_TAT);
        assertThat(testFulfillment_center.getIs_enabled()).isEqualTo(UPDATED_IS_ENABLED);
    }

    @Test
    @Transactional
    public void deleteFulfillment_center() throws Exception {
        // Initialize the database
        fulfillment_centerRepository.saveAndFlush(fulfillment_center);

		int databaseSizeBeforeDelete = fulfillment_centerRepository.findAll().size();

        // Get the fulfillment_center
        restFulfillment_centerMockMvc.perform(delete("/api/fulfillment_centers/{id}", fulfillment_center.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Fulfillment_center> fulfillment_centers = fulfillment_centerRepository.findAll();
        assertThat(fulfillment_centers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
