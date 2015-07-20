package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Master_bag;
import com.mycompany.myapp.repository.Master_bagRepository;
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
 * REST controller for managing Master_bag.
 */
@RestController
@RequestMapping("/api")
public class Master_bagResource {

    private final Logger log = LoggerFactory.getLogger(Master_bagResource.class);

    @Inject
    private Master_bagRepository master_bagRepository;

    /**
     * POST  /master_bags -> Create a new master_bag.
     */
    @RequestMapping(value = "/master_bags",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Master_bag master_bag) throws URISyntaxException {
        log.debug("REST request to save Master_bag : {}", master_bag);
        if (master_bag.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new master_bag cannot already have an ID").build();
        }
        master_bagRepository.save(master_bag);
        return ResponseEntity.created(new URI("/api/master_bags/" + master_bag.getId())).build();
    }

    /**
     * PUT  /master_bags -> Updates an existing master_bag.
     */
    @RequestMapping(value = "/master_bags",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Master_bag master_bag) throws URISyntaxException {
        log.debug("REST request to update Master_bag : {}", master_bag);
        if (master_bag.getId() == null) {
            return create(master_bag);
        }
        master_bagRepository.save(master_bag);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /master_bags -> get all the master_bags.
     */
    @RequestMapping(value = "/master_bags",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Master_bag>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Master_bag> page = master_bagRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/master_bags", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /master_bags/:id -> get the "id" master_bag.
     */
    @RequestMapping(value = "/master_bags/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Master_bag> get(@PathVariable Long id) {
        log.debug("REST request to get Master_bag : {}", id);
        return Optional.ofNullable(master_bagRepository.findOne(id))
            .map(master_bag -> new ResponseEntity<>(
                master_bag,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /master_bags/:id -> delete the "id" master_bag.
     */
    @RequestMapping(value = "/master_bags/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Master_bag : {}", id);
        master_bagRepository.delete(id);
    }
}
