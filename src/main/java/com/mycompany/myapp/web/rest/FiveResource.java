package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Five;
import com.mycompany.myapp.repository.FiveRepository;
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
 * REST controller for managing Five.
 */
@RestController
@RequestMapping("/api")
public class FiveResource {

    private final Logger log = LoggerFactory.getLogger(FiveResource.class);

    @Inject
    private FiveRepository fiveRepository;

    /**
     * POST  /fives -> Create a new five.
     */
    @RequestMapping(value = "/fives",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Five five) throws URISyntaxException {
        log.debug("REST request to save Five : {}", five);
        if (five.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new five cannot already have an ID").build();
        }
        fiveRepository.save(five);
        return ResponseEntity.created(new URI("/api/fives/" + five.getId())).build();
    }

    /**
     * PUT  /fives -> Updates an existing five.
     */
    @RequestMapping(value = "/fives",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Five five) throws URISyntaxException {
        log.debug("REST request to update Five : {}", five);
        if (five.getId() == null) {
            return create(five);
        }
        fiveRepository.save(five);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /fives -> get all the fives.
     */
    @RequestMapping(value = "/fives",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Five> getAll() {
        log.debug("REST request to get all Fives");
        return fiveRepository.findAll();
    }

    /**
     * GET  /fives/:id -> get the "id" five.
     */
    @RequestMapping(value = "/fives/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Five> get(@PathVariable Long id) {
        log.debug("REST request to get Five : {}", id);
        return Optional.ofNullable(fiveRepository.findOne(id))
            .map(five -> new ResponseEntity<>(
                five,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /fives/:id -> delete the "id" five.
     */
    @RequestMapping(value = "/fives/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Five : {}", id);
        fiveRepository.delete(id);
    }
}
