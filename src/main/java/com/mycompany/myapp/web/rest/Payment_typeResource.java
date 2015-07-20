package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Payment_type;
import com.mycompany.myapp.repository.Payment_typeRepository;
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
 * REST controller for managing Payment_type.
 */
@RestController
@RequestMapping("/api")
public class Payment_typeResource {

    private final Logger log = LoggerFactory.getLogger(Payment_typeResource.class);

    @Inject
    private Payment_typeRepository payment_typeRepository;

    /**
     * POST  /payment_types -> Create a new payment_type.
     */
    @RequestMapping(value = "/payment_types",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Payment_type payment_type) throws URISyntaxException {
        log.debug("REST request to save Payment_type : {}", payment_type);
        if (payment_type.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new payment_type cannot already have an ID").build();
        }
        payment_typeRepository.save(payment_type);
        return ResponseEntity.created(new URI("/api/payment_types/" + payment_type.getId())).build();
    }

    /**
     * PUT  /payment_types -> Updates an existing payment_type.
     */
    @RequestMapping(value = "/payment_types",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Payment_type payment_type) throws URISyntaxException {
        log.debug("REST request to update Payment_type : {}", payment_type);
        if (payment_type.getId() == null) {
            return create(payment_type);
        }
        payment_typeRepository.save(payment_type);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /payment_types -> get all the payment_types.
     */
    @RequestMapping(value = "/payment_types",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Payment_type>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Payment_type> page = payment_typeRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/payment_types", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /payment_types/:id -> get the "id" payment_type.
     */
    @RequestMapping(value = "/payment_types/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Payment_type> get(@PathVariable Long id) {
        log.debug("REST request to get Payment_type : {}", id);
        return Optional.ofNullable(payment_typeRepository.findOne(id))
            .map(payment_type -> new ResponseEntity<>(
                payment_type,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /payment_types/:id -> delete the "id" payment_type.
     */
    @RequestMapping(value = "/payment_types/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Payment_type : {}", id);
        payment_typeRepository.delete(id);
    }
}
