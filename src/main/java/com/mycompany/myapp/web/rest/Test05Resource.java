package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Test05;
import com.mycompany.myapp.repository.Test05Repository;
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
 * REST controller for managing Test05.
 */
@RestController
@RequestMapping("/api")
public class Test05Resource {

    private final Logger log = LoggerFactory.getLogger(Test05Resource.class);

    @Inject
    private Test05Repository test05Repository;

    /**
     * POST  /test05s -> Create a new test05.
     */
    @RequestMapping(value = "/test05s",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Test05 test05) throws URISyntaxException {
        log.debug("REST request to save Test05 : {}", test05);
        if (test05.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new test05 cannot already have an ID").build();
        }
        test05Repository.save(test05);
        return ResponseEntity.created(new URI("/api/test05s/" + test05.getId())).build();
    }

    /**
     * PUT  /test05s -> Updates an existing test05.
     */
    @RequestMapping(value = "/test05s",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Test05 test05) throws URISyntaxException {
        log.debug("REST request to update Test05 : {}", test05);
        if (test05.getId() == null) {
            return create(test05);
        }
        test05Repository.save(test05);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /test05s -> get all the test05s.
     */
    @RequestMapping(value = "/test05s",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Test05> getAll() {
        log.debug("REST request to get all Test05s");
        return test05Repository.findAll();
    }

    /**
     * GET  /test05s/:id -> get the "id" test05.
     */
    @RequestMapping(value = "/test05s/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Test05> get(@PathVariable Long id) {
        log.debug("REST request to get Test05 : {}", id);
        return Optional.ofNullable(test05Repository.findOne(id))
            .map(test05 -> new ResponseEntity<>(
                test05,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /test05s/:id -> delete the "id" test05.
     */
    @RequestMapping(value = "/test05s/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Test05 : {}", id);
        test05Repository.delete(id);
    }
}
