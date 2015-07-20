package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Fulfillment_center;
import com.mycompany.myapp.repository.Fulfillment_centerRepository;
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
 * REST controller for managing Fulfillment_center.
 */
@RestController
@RequestMapping("/api")
public class Fulfillment_centerResource {

    private final Logger log = LoggerFactory.getLogger(Fulfillment_centerResource.class);

    @Inject
    private Fulfillment_centerRepository fulfillment_centerRepository;

    /**
     * POST  /fulfillment_centers -> Create a new fulfillment_center.
     */
    @RequestMapping(value = "/fulfillment_centers",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Fulfillment_center fulfillment_center) throws URISyntaxException {
        log.debug("REST request to save Fulfillment_center : {}", fulfillment_center);
        if (fulfillment_center.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new fulfillment_center cannot already have an ID").build();
        }
        fulfillment_centerRepository.save(fulfillment_center);
        return ResponseEntity.created(new URI("/api/fulfillment_centers/" + fulfillment_center.getId())).build();
    }

    /**
     * PUT  /fulfillment_centers -> Updates an existing fulfillment_center.
     */
    @RequestMapping(value = "/fulfillment_centers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Fulfillment_center fulfillment_center) throws URISyntaxException {
        log.debug("REST request to update Fulfillment_center : {}", fulfillment_center);
        if (fulfillment_center.getId() == null) {
            return create(fulfillment_center);
        }
        fulfillment_centerRepository.save(fulfillment_center);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /fulfillment_centers -> get all the fulfillment_centers.
     */
    @RequestMapping(value = "/fulfillment_centers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Fulfillment_center>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Fulfillment_center> page = fulfillment_centerRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fulfillment_centers", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /fulfillment_centers/:id -> get the "id" fulfillment_center.
     */
    @RequestMapping(value = "/fulfillment_centers/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Fulfillment_center> get(@PathVariable Long id) {
        log.debug("REST request to get Fulfillment_center : {}", id);
        return Optional.ofNullable(fulfillment_centerRepository.findOne(id))
            .map(fulfillment_center -> new ResponseEntity<>(
                fulfillment_center,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /fulfillment_centers/:id -> delete the "id" fulfillment_center.
     */
    @RequestMapping(value = "/fulfillment_centers/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Fulfillment_center : {}", id);
        fulfillment_centerRepository.delete(id);
    }
}
