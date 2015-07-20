package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Payment_service_mapper;
import com.mycompany.myapp.repository.Payment_service_mapperRepository;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Payment_service_mapper.
 */
@RestController
@RequestMapping("/api")
public class Payment_service_mapperResource {

    private final Logger log = LoggerFactory.getLogger(Payment_service_mapperResource.class);

    @Inject
    private Payment_service_mapperRepository payment_service_mapperRepository;

    /**
     * POST  /payment_service_mappers -> Create a new payment_service_mapper.
     */
    @RequestMapping(value = "/payment_service_mappers",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Payment_service_mapper payment_service_mapper) throws URISyntaxException {
        log.debug("REST request to save Payment_service_mapper : {}", payment_service_mapper);
        if (payment_service_mapper.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new payment_service_mapper cannot already have an ID").build();
        }
        payment_service_mapperRepository.save(payment_service_mapper);
        return ResponseEntity.created(new URI("/api/payment_service_mappers/" + payment_service_mapper.getId())).build();
    }

    /**
     * PUT  /payment_service_mappers -> Updates an existing payment_service_mapper.
     */
    @RequestMapping(value = "/payment_service_mappers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Payment_service_mapper payment_service_mapper) throws URISyntaxException {
        log.debug("REST request to update Payment_service_mapper : {}", payment_service_mapper);
        if (payment_service_mapper.getId() == null) {
            return create(payment_service_mapper);
        }
        payment_service_mapperRepository.save(payment_service_mapper);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /payment_service_mappers -> get all the payment_service_mappers.
     */
    @RequestMapping(value = "/payment_service_mappers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Payment_service_mapper>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Payment_service_mapper> page = payment_service_mapperRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/payment_service_mappers", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /payment_service_mappers/:id -> get the "id" payment_service_mapper.
     */
    @RequestMapping(value = "/payment_service_mappers/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Payment_service_mapper> get(@PathVariable Long id) {
        log.debug("REST request to get Payment_service_mapper : {}", id);
        return Optional.ofNullable(payment_service_mapperRepository.findOne(id))
            .map(payment_service_mapper -> new ResponseEntity<>(
                payment_service_mapper,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /payment_service_mappers/:id -> delete the "id" payment_service_mapper.
     */
    @RequestMapping(value = "/payment_service_mappers/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Payment_service_mapper : {}", id);
        payment_service_mapperRepository.delete(id);
    }
}
