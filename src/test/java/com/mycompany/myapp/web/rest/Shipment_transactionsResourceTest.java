package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Shipment_transactions;
import com.mycompany.myapp.repository.Shipment_transactionsRepository;

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
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the Shipment_transactionsResource REST controller.
 *
 * @see Shipment_transactionsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Shipment_transactionsResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_ORDER_REF = "SAMPLE_TEXT";
    private static final String UPDATED_ORDER_REF = "UPDATED_TEXT";
    private static final String DEFAULT_INVOICE_ID = "SAMPLE_TEXT";
    private static final String UPDATED_INVOICE_ID = "UPDATED_TEXT";
    private static final String DEFAULT_SHIPMENT_ID = "SAMPLE_TEXT";
    private static final String UPDATED_SHIPMENT_ID = "UPDATED_TEXT";

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(0);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(1);
    private static final String DEFAULT_AWB = "SAMPLE_TEXT";
    private static final String UPDATED_AWB = "UPDATED_TEXT";
    private static final String DEFAULT_STATUS = "SAMPLE_TEXT";
    private static final String UPDATED_STATUS = "UPDATED_TEXT";

    private static final DateTime DEFAULT_TIME = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_TIME = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_TIME_STR = dateTimeFormatter.print(DEFAULT_TIME);
    private static final String DEFAULT_LBH = "SAMPLE_TEXT";
    private static final String UPDATED_LBH = "UPDATED_TEXT";

    private static final DateTime DEFAULT_IN_SCAN_TIME = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_IN_SCAN_TIME = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_IN_SCAN_TIME_STR = dateTimeFormatter.print(DEFAULT_IN_SCAN_TIME);
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";

    private static final BigDecimal DEFAULT_COST = new BigDecimal(0);
    private static final BigDecimal UPDATED_COST = new BigDecimal(1);

    @Inject
    private Shipment_transactionsRepository shipment_transactionsRepository;

    private MockMvc restShipment_transactionsMockMvc;

    private Shipment_transactions shipment_transactions;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Shipment_transactionsResource shipment_transactionsResource = new Shipment_transactionsResource();
        ReflectionTestUtils.setField(shipment_transactionsResource, "shipment_transactionsRepository", shipment_transactionsRepository);
        this.restShipment_transactionsMockMvc = MockMvcBuilders.standaloneSetup(shipment_transactionsResource).build();
    }

    @Before
    public void initTest() {
        shipment_transactions = new Shipment_transactions();
        shipment_transactions.setOrderRef(DEFAULT_ORDER_REF);
        shipment_transactions.setInvoiceId(DEFAULT_INVOICE_ID);
        shipment_transactions.setShipmentId(DEFAULT_SHIPMENT_ID);
        shipment_transactions.setAmount(DEFAULT_AMOUNT);
        shipment_transactions.setAwb(DEFAULT_AWB);
        shipment_transactions.setStatus(DEFAULT_STATUS);
        shipment_transactions.setTime(DEFAULT_TIME);
        shipment_transactions.setLbh(DEFAULT_LBH);
        shipment_transactions.setIn_scan_time(DEFAULT_IN_SCAN_TIME);
        shipment_transactions.setDescription(DEFAULT_DESCRIPTION);
        shipment_transactions.setCost(DEFAULT_COST);
    }

    @Test
    @Transactional
    public void createShipment_transactions() throws Exception {
        int databaseSizeBeforeCreate = shipment_transactionsRepository.findAll().size();

        // Create the Shipment_transactions
        restShipment_transactionsMockMvc.perform(post("/api/shipment_transactionss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shipment_transactions)))
                .andExpect(status().isCreated());

        // Validate the Shipment_transactions in the database
        List<Shipment_transactions> shipment_transactionss = shipment_transactionsRepository.findAll();
        assertThat(shipment_transactionss).hasSize(databaseSizeBeforeCreate + 1);
        Shipment_transactions testShipment_transactions = shipment_transactionss.get(shipment_transactionss.size() - 1);
        assertThat(testShipment_transactions.getOrderRef()).isEqualTo(DEFAULT_ORDER_REF);
        assertThat(testShipment_transactions.getInvoiceId()).isEqualTo(DEFAULT_INVOICE_ID);
        assertThat(testShipment_transactions.getShipmentId()).isEqualTo(DEFAULT_SHIPMENT_ID);
        assertThat(testShipment_transactions.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testShipment_transactions.getAwb()).isEqualTo(DEFAULT_AWB);
        assertThat(testShipment_transactions.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testShipment_transactions.getTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_TIME);
        assertThat(testShipment_transactions.getLbh()).isEqualTo(DEFAULT_LBH);
        assertThat(testShipment_transactions.getIn_scan_time().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_IN_SCAN_TIME);
        assertThat(testShipment_transactions.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testShipment_transactions.getCost()).isEqualTo(DEFAULT_COST);
    }

    @Test
    @Transactional
    public void checkOrderRefIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(shipment_transactionsRepository.findAll()).hasSize(0);
        // set the field null
        shipment_transactions.setOrderRef(null);

        // Create the Shipment_transactions, which fails.
        restShipment_transactionsMockMvc.perform(post("/api/shipment_transactionss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shipment_transactions)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Shipment_transactions> shipment_transactionss = shipment_transactionsRepository.findAll();
        assertThat(shipment_transactionss).hasSize(0);
    }

    @Test
    @Transactional
    public void checkInvoiceIdIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(shipment_transactionsRepository.findAll()).hasSize(0);
        // set the field null
        shipment_transactions.setInvoiceId(null);

        // Create the Shipment_transactions, which fails.
        restShipment_transactionsMockMvc.perform(post("/api/shipment_transactionss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shipment_transactions)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Shipment_transactions> shipment_transactionss = shipment_transactionsRepository.findAll();
        assertThat(shipment_transactionss).hasSize(0);
    }

    @Test
    @Transactional
    public void checkShipmentIdIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(shipment_transactionsRepository.findAll()).hasSize(0);
        // set the field null
        shipment_transactions.setShipmentId(null);

        // Create the Shipment_transactions, which fails.
        restShipment_transactionsMockMvc.perform(post("/api/shipment_transactionss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shipment_transactions)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Shipment_transactions> shipment_transactionss = shipment_transactionsRepository.findAll();
        assertThat(shipment_transactionss).hasSize(0);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(shipment_transactionsRepository.findAll()).hasSize(0);
        // set the field null
        shipment_transactions.setAmount(null);

        // Create the Shipment_transactions, which fails.
        restShipment_transactionsMockMvc.perform(post("/api/shipment_transactionss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shipment_transactions)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Shipment_transactions> shipment_transactionss = shipment_transactionsRepository.findAll();
        assertThat(shipment_transactionss).hasSize(0);
    }

    @Test
    @Transactional
    public void checkTimeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(shipment_transactionsRepository.findAll()).hasSize(0);
        // set the field null
        shipment_transactions.setTime(null);

        // Create the Shipment_transactions, which fails.
        restShipment_transactionsMockMvc.perform(post("/api/shipment_transactionss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shipment_transactions)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Shipment_transactions> shipment_transactionss = shipment_transactionsRepository.findAll();
        assertThat(shipment_transactionss).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllShipment_transactionss() throws Exception {
        // Initialize the database
        shipment_transactionsRepository.saveAndFlush(shipment_transactions);

        // Get all the shipment_transactionss
        restShipment_transactionsMockMvc.perform(get("/api/shipment_transactionss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(shipment_transactions.getId().intValue())))
                .andExpect(jsonPath("$.[*].orderRef").value(hasItem(DEFAULT_ORDER_REF.toString())))
                .andExpect(jsonPath("$.[*].invoiceId").value(hasItem(DEFAULT_INVOICE_ID.toString())))
                .andExpect(jsonPath("$.[*].shipmentId").value(hasItem(DEFAULT_SHIPMENT_ID.toString())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].awb").value(hasItem(DEFAULT_AWB.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME_STR)))
                .andExpect(jsonPath("$.[*].lbh").value(hasItem(DEFAULT_LBH.toString())))
                .andExpect(jsonPath("$.[*].in_scan_time").value(hasItem(DEFAULT_IN_SCAN_TIME_STR)))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.intValue())));
    }

    @Test
    @Transactional
    public void getShipment_transactions() throws Exception {
        // Initialize the database
        shipment_transactionsRepository.saveAndFlush(shipment_transactions);

        // Get the shipment_transactions
        restShipment_transactionsMockMvc.perform(get("/api/shipment_transactionss/{id}", shipment_transactions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(shipment_transactions.getId().intValue()))
            .andExpect(jsonPath("$.orderRef").value(DEFAULT_ORDER_REF.toString()))
            .andExpect(jsonPath("$.invoiceId").value(DEFAULT_INVOICE_ID.toString()))
            .andExpect(jsonPath("$.shipmentId").value(DEFAULT_SHIPMENT_ID.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.awb").value(DEFAULT_AWB.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME_STR))
            .andExpect(jsonPath("$.lbh").value(DEFAULT_LBH.toString()))
            .andExpect(jsonPath("$.in_scan_time").value(DEFAULT_IN_SCAN_TIME_STR))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingShipment_transactions() throws Exception {
        // Get the shipment_transactions
        restShipment_transactionsMockMvc.perform(get("/api/shipment_transactionss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShipment_transactions() throws Exception {
        // Initialize the database
        shipment_transactionsRepository.saveAndFlush(shipment_transactions);

		int databaseSizeBeforeUpdate = shipment_transactionsRepository.findAll().size();

        // Update the shipment_transactions
        shipment_transactions.setOrderRef(UPDATED_ORDER_REF);
        shipment_transactions.setInvoiceId(UPDATED_INVOICE_ID);
        shipment_transactions.setShipmentId(UPDATED_SHIPMENT_ID);
        shipment_transactions.setAmount(UPDATED_AMOUNT);
        shipment_transactions.setAwb(UPDATED_AWB);
        shipment_transactions.setStatus(UPDATED_STATUS);
        shipment_transactions.setTime(UPDATED_TIME);
        shipment_transactions.setLbh(UPDATED_LBH);
        shipment_transactions.setIn_scan_time(UPDATED_IN_SCAN_TIME);
        shipment_transactions.setDescription(UPDATED_DESCRIPTION);
        shipment_transactions.setCost(UPDATED_COST);
        restShipment_transactionsMockMvc.perform(put("/api/shipment_transactionss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shipment_transactions)))
                .andExpect(status().isOk());

        // Validate the Shipment_transactions in the database
        List<Shipment_transactions> shipment_transactionss = shipment_transactionsRepository.findAll();
        assertThat(shipment_transactionss).hasSize(databaseSizeBeforeUpdate);
        Shipment_transactions testShipment_transactions = shipment_transactionss.get(shipment_transactionss.size() - 1);
        assertThat(testShipment_transactions.getOrderRef()).isEqualTo(UPDATED_ORDER_REF);
        assertThat(testShipment_transactions.getInvoiceId()).isEqualTo(UPDATED_INVOICE_ID);
        assertThat(testShipment_transactions.getShipmentId()).isEqualTo(UPDATED_SHIPMENT_ID);
        assertThat(testShipment_transactions.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testShipment_transactions.getAwb()).isEqualTo(UPDATED_AWB);
        assertThat(testShipment_transactions.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testShipment_transactions.getTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_TIME);
        assertThat(testShipment_transactions.getLbh()).isEqualTo(UPDATED_LBH);
        assertThat(testShipment_transactions.getIn_scan_time().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_IN_SCAN_TIME);
        assertThat(testShipment_transactions.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testShipment_transactions.getCost()).isEqualTo(UPDATED_COST);
    }

    @Test
    @Transactional
    public void deleteShipment_transactions() throws Exception {
        // Initialize the database
        shipment_transactionsRepository.saveAndFlush(shipment_transactions);

		int databaseSizeBeforeDelete = shipment_transactionsRepository.findAll().size();

        // Get the shipment_transactions
        restShipment_transactionsMockMvc.perform(delete("/api/shipment_transactionss/{id}", shipment_transactions.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Shipment_transactions> shipment_transactionss = shipment_transactionsRepository.findAll();
        assertThat(shipment_transactionss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
