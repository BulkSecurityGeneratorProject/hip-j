package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Dest_pincode;
import com.mycompany.myapp.repository.Dest_pincodeRepository;

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
 * Test class for the Dest_pincodeResource REST controller.
 *
 * @see Dest_pincodeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Dest_pincodeResourceTest {

    private static final String DEFAULT_PINCODE = "SAMPLE_TEXT";
    private static final String UPDATED_PINCODE = "UPDATED_TEXT";
    private static final String DEFAULT_DISTRICT = "SAMPLE_TEXT";
    private static final String UPDATED_DISTRICT = "UPDATED_TEXT";

    @Inject
    private Dest_pincodeRepository dest_pincodeRepository;

    private MockMvc restDest_pincodeMockMvc;

    private Dest_pincode dest_pincode;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Dest_pincodeResource dest_pincodeResource = new Dest_pincodeResource();
        ReflectionTestUtils.setField(dest_pincodeResource, "dest_pincodeRepository", dest_pincodeRepository);
        this.restDest_pincodeMockMvc = MockMvcBuilders.standaloneSetup(dest_pincodeResource).build();
    }

    @Before
    public void initTest() {
        dest_pincode = new Dest_pincode();
        dest_pincode.setPincode(DEFAULT_PINCODE);
        dest_pincode.setDistrict(DEFAULT_DISTRICT);
    }

    @Test
    @Transactional
    public void createDest_pincode() throws Exception {
        int databaseSizeBeforeCreate = dest_pincodeRepository.findAll().size();

        // Create the Dest_pincode
        restDest_pincodeMockMvc.perform(post("/api/dest_pincodes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dest_pincode)))
                .andExpect(status().isCreated());

        // Validate the Dest_pincode in the database
        List<Dest_pincode> dest_pincodes = dest_pincodeRepository.findAll();
        assertThat(dest_pincodes).hasSize(databaseSizeBeforeCreate + 1);
        Dest_pincode testDest_pincode = dest_pincodes.get(dest_pincodes.size() - 1);
        assertThat(testDest_pincode.getPincode()).isEqualTo(DEFAULT_PINCODE);
        assertThat(testDest_pincode.getDistrict()).isEqualTo(DEFAULT_DISTRICT);
    }

    @Test
    @Transactional
    public void checkPincodeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(dest_pincodeRepository.findAll()).hasSize(0);
        // set the field null
        dest_pincode.setPincode(null);

        // Create the Dest_pincode, which fails.
        restDest_pincodeMockMvc.perform(post("/api/dest_pincodes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dest_pincode)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Dest_pincode> dest_pincodes = dest_pincodeRepository.findAll();
        assertThat(dest_pincodes).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllDest_pincodes() throws Exception {
        // Initialize the database
        dest_pincodeRepository.saveAndFlush(dest_pincode);

        // Get all the dest_pincodes
        restDest_pincodeMockMvc.perform(get("/api/dest_pincodes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dest_pincode.getId().intValue())))
                .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE.toString())))
                .andExpect(jsonPath("$.[*].district").value(hasItem(DEFAULT_DISTRICT.toString())));
    }

    @Test
    @Transactional
    public void getDest_pincode() throws Exception {
        // Initialize the database
        dest_pincodeRepository.saveAndFlush(dest_pincode);

        // Get the dest_pincode
        restDest_pincodeMockMvc.perform(get("/api/dest_pincodes/{id}", dest_pincode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dest_pincode.getId().intValue()))
            .andExpect(jsonPath("$.pincode").value(DEFAULT_PINCODE.toString()))
            .andExpect(jsonPath("$.district").value(DEFAULT_DISTRICT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDest_pincode() throws Exception {
        // Get the dest_pincode
        restDest_pincodeMockMvc.perform(get("/api/dest_pincodes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDest_pincode() throws Exception {
        // Initialize the database
        dest_pincodeRepository.saveAndFlush(dest_pincode);

		int databaseSizeBeforeUpdate = dest_pincodeRepository.findAll().size();

        // Update the dest_pincode
        dest_pincode.setPincode(UPDATED_PINCODE);
        dest_pincode.setDistrict(UPDATED_DISTRICT);
        restDest_pincodeMockMvc.perform(put("/api/dest_pincodes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dest_pincode)))
                .andExpect(status().isOk());

        // Validate the Dest_pincode in the database
        List<Dest_pincode> dest_pincodes = dest_pincodeRepository.findAll();
        assertThat(dest_pincodes).hasSize(databaseSizeBeforeUpdate);
        Dest_pincode testDest_pincode = dest_pincodes.get(dest_pincodes.size() - 1);
        assertThat(testDest_pincode.getPincode()).isEqualTo(UPDATED_PINCODE);
        assertThat(testDest_pincode.getDistrict()).isEqualTo(UPDATED_DISTRICT);
    }

    @Test
    @Transactional
    public void deleteDest_pincode() throws Exception {
        // Initialize the database
        dest_pincodeRepository.saveAndFlush(dest_pincode);

		int databaseSizeBeforeDelete = dest_pincodeRepository.findAll().size();

        // Get the dest_pincode
        restDest_pincodeMockMvc.perform(delete("/api/dest_pincodes/{id}", dest_pincode.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Dest_pincode> dest_pincodes = dest_pincodeRepository.findAll();
        assertThat(dest_pincodes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
