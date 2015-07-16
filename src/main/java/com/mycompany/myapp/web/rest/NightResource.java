package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Night;
import com.mycompany.myapp.repository.NightRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing Night.
 */
@RestController
@RequestMapping("/api")
public class NightResource {

    private final Logger log = LoggerFactory.getLogger(NightResource.class);

    @Inject
    private NightRepository nightRepository;

    /**
     * POST  /nights -> Create a new night.
     */
    @RequestMapping(value = "/nights",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Night night) throws URISyntaxException {
        log.debug("REST request to save Night : {}", night);
        if (night.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new night cannot already have an ID").build();
        }
        nightRepository.save(night);
        return ResponseEntity.created(new URI("/api/nights/" + night.getId())).build();
    }

    /**
     * PUT  /nights -> Updates an existing night.
     */
    @RequestMapping(value = "/nights",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Night night) throws URISyntaxException {
        log.debug("REST request to update Night : {}", night);
        if (night.getId() == null) {
            return create(night);
        }
        nightRepository.save(night);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /nights -> get all the nights.
     */
    @RequestMapping(value = "/nights",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Night> getAll() {
        log.debug("REST request to get all Nights");
        return nightRepository.findAll();
    }

    /**
     * GET  /nights/:id -> get the "id" night.
     */
    @RequestMapping(value = "/nights/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Night> get(@PathVariable Long id) {
        log.debug("REST request to get Night : {}", id);
        return Optional.ofNullable(nightRepository.findOne(id))
            .map(night -> new ResponseEntity<>(
                night,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /nights/:id -> delete the "id" night.
     */
    @RequestMapping(value = "/nights/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Night : {}", id);
        nightRepository.delete(id);
    }
}
