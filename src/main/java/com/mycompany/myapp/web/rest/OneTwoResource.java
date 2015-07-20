package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.OneTwo;
import com.mycompany.myapp.repository.OneTwoRepository;
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
 * REST controller for managing OneTwo.
 */
@RestController
@RequestMapping("/api")
public class OneTwoResource {

    private final Logger log = LoggerFactory.getLogger(OneTwoResource.class);

    @Inject
    private OneTwoRepository oneTwoRepository;

    /**
     * POST  /oneTwos -> Create a new oneTwo.
     */
    @RequestMapping(value = "/oneTwos",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody OneTwo oneTwo) throws URISyntaxException {
        log.debug("REST request to save OneTwo : {}", oneTwo);
        if (oneTwo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new oneTwo cannot already have an ID").build();
        }
        oneTwoRepository.save(oneTwo);
        return ResponseEntity.created(new URI("/api/oneTwos/" + oneTwo.getId())).build();
    }

    /**
     * PUT  /oneTwos -> Updates an existing oneTwo.
     */
    @RequestMapping(value = "/oneTwos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody OneTwo oneTwo) throws URISyntaxException {
        log.debug("REST request to update OneTwo : {}", oneTwo);
        if (oneTwo.getId() == null) {
            return create(oneTwo);
        }
        oneTwoRepository.save(oneTwo);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /oneTwos -> get all the oneTwos.
     */
    @RequestMapping(value = "/oneTwos",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<OneTwo> getAll() {
        log.debug("REST request to get all OneTwos");
        return oneTwoRepository.findAll();
    }

    /**
     * GET  /oneTwos/:id -> get the "id" oneTwo.
     */
    @RequestMapping(value = "/oneTwos/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OneTwo> get(@PathVariable Long id) {
        log.debug("REST request to get OneTwo : {}", id);
        return Optional.ofNullable(oneTwoRepository.findOne(id))
            .map(oneTwo -> new ResponseEntity<>(
                oneTwo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /oneTwos/:id -> delete the "id" oneTwo.
     */
    @RequestMapping(value = "/oneTwos/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete OneTwo : {}", id);
        oneTwoRepository.delete(id);
    }
}
