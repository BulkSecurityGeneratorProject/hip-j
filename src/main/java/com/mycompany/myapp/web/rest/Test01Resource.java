package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Test01;
import com.mycompany.myapp.repository.Test01Repository;
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
 * REST controller for managing Test01.
 */
@RestController
@RequestMapping("/api")
public class Test01Resource {

    private final Logger log = LoggerFactory.getLogger(Test01Resource.class);

    @Inject
    private Test01Repository test01Repository;

    /**
     * POST  /test01s -> Create a new test01.
     */
    @RequestMapping(value = "/test01s",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Test01 test01) throws URISyntaxException {
        log.debug("REST request to save Test01 : {}", test01);
        if (test01.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new test01 cannot already have an ID").build();
        }
        test01Repository.save(test01);
        return ResponseEntity.created(new URI("/api/test01s/" + test01.getId())).build();
    }

    /**
     * PUT  /test01s -> Updates an existing test01.
     */
    @RequestMapping(value = "/test01s",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Test01 test01) throws URISyntaxException {
        log.debug("REST request to update Test01 : {}", test01);
        if (test01.getId() == null) {
            return create(test01);
        }
        test01Repository.save(test01);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /test01s -> get all the test01s.
     */
    @RequestMapping(value = "/test01s",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Test01> getAll() {
        log.debug("REST request to get all Test01s");
        return test01Repository.findAll();
    }

    /**
     * GET  /test01s/:id -> get the "id" test01.
     */
    @RequestMapping(value = "/test01s/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Test01> get(@PathVariable Long id) {
        log.debug("REST request to get Test01 : {}", id);
        return Optional.ofNullable(test01Repository.findOne(id))
            .map(test01 -> new ResponseEntity<>(
                test01,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /test01s/:id -> delete the "id" test01.
     */
    @RequestMapping(value = "/test01s/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Test01 : {}", id);
        test01Repository.delete(id);
    }
}
