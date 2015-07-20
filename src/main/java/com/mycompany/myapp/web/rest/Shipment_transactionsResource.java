package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Shipment_transactions;
import com.mycompany.myapp.repository.Shipment_transactionsRepository;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Shipment_transactions.
 */
@RestController
@RequestMapping("/api")
public class Shipment_transactionsResource {

    private final Logger log = LoggerFactory.getLogger(Shipment_transactionsResource.class);

    @Inject
    private Shipment_transactionsRepository shipment_transactionsRepository;

    /**
     * POST  /shipment_transactionss -> Create a new shipment_transactions.
     */
    @RequestMapping(value = "/shipment_transactionss",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Shipment_transactions shipment_transactions) throws URISyntaxException {
        log.debug("REST request to save Shipment_transactions : {}", shipment_transactions);
        if (shipment_transactions.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new shipment_transactions cannot already have an ID").build();
        }
        shipment_transactionsRepository.save(shipment_transactions);
        return ResponseEntity.created(new URI("/api/shipment_transactionss/" + shipment_transactions.getId())).build();
    }

    /**
     * PUT  /shipment_transactionss -> Updates an existing shipment_transactions.
     */
    @RequestMapping(value = "/shipment_transactionss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Shipment_transactions shipment_transactions) throws URISyntaxException {
        log.debug("REST request to update Shipment_transactions : {}", shipment_transactions);
        if (shipment_transactions.getId() == null) {
            return create(shipment_transactions);
        }
        shipment_transactionsRepository.save(shipment_transactions);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /shipment_transactionss -> get all the shipment_transactionss.
     */
    @RequestMapping(value = "/shipment_transactionss",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Shipment_transactions>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Shipment_transactions> page = shipment_transactionsRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/shipment_transactionss", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /shipment_transactionss/:id -> get the "id" shipment_transactions.
     */
    @RequestMapping(value = "/shipment_transactionss/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Shipment_transactions> get(@PathVariable Long id) {
        log.debug("REST request to get Shipment_transactions : {}", id);
        return Optional.ofNullable(shipment_transactionsRepository.findOne(id))
            .map(shipment_transactions -> new ResponseEntity<>(
                shipment_transactions,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /shipment_transactionss/:id -> delete the "id" shipment_transactions.
     */
    @RequestMapping(value = "/shipment_transactionss/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Shipment_transactions : {}", id);
        shipment_transactionsRepository.delete(id);
    }
}
