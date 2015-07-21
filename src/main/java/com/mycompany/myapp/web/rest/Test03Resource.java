package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Test03;
import com.mycompany.myapp.repository.Test03Repository;
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
 * REST controller for managing Test03.
 */
@RestController
@RequestMapping("/api")
public class Test03Resource {

    private final Logger log = LoggerFactory.getLogger(Test03Resource.class);

    @Inject
    private Test03Repository test03Repository;

    /**
     * POST  /test03s -> Create a new test03.
     */
    @RequestMapping(value = "/test03s",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Test03 test03) throws URISyntaxException {
        log.debug("REST request to save Test03 : {}", test03);
        if (test03.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new test03 cannot already have an ID").build();
        }
        test03Repository.save(test03);
        return ResponseEntity.created(new URI("/api/test03s/" + test03.getId())).build();
    }

    /**
     * PUT  /test03s -> Updates an existing test03.
     */
    @RequestMapping(value = "/test03s",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Test03 test03) throws URISyntaxException {
        log.debug("REST request to update Test03 : {}", test03);
        if (test03.getId() == null) {
            return create(test03);
        }
        test03Repository.save(test03);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /test03s -> get all the test03s.
     */
    @RequestMapping(value = "/test03s",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Test03> getAll() {
        log.debug("REST request to get all Test03s");
        return test03Repository.findAll();
    }

    /**
     * GET  /test03s/:id -> get the "id" test03.
     */
    @RequestMapping(value = "/test03s/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Test03> get(@PathVariable Long id) {
        log.debug("REST request to get Test03 : {}", id);
        return Optional.ofNullable(test03Repository.findOne(id))
            .map(test03 -> new ResponseEntity<>(
                test03,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /test03s/:id -> delete the "id" test03.
     */
    @RequestMapping(value = "/test03s/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Test03 : {}", id);
        test03Repository.delete(id);
    }
}
