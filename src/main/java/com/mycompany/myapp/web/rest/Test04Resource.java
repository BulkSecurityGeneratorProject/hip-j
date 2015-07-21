package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Test04;
import com.mycompany.myapp.repository.Test04Repository;
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
 * REST controller for managing Test04.
 */
@RestController
@RequestMapping("/api")
public class Test04Resource {

    private final Logger log = LoggerFactory.getLogger(Test04Resource.class);

    @Inject
    private Test04Repository test04Repository;

    /**
     * POST  /test04s -> Create a new test04.
     */
    @RequestMapping(value = "/test04s",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Test04 test04) throws URISyntaxException {
        log.debug("REST request to save Test04 : {}", test04);
        if (test04.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new test04 cannot already have an ID").build();
        }
        test04Repository.save(test04);
        return ResponseEntity.created(new URI("/api/test04s/" + test04.getId())).build();
    }

    /**
     * PUT  /test04s -> Updates an existing test04.
     */
    @RequestMapping(value = "/test04s",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Test04 test04) throws URISyntaxException {
        log.debug("REST request to update Test04 : {}", test04);
        if (test04.getId() == null) {
            return create(test04);
        }
        test04Repository.save(test04);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /test04s -> get all the test04s.
     */
    @RequestMapping(value = "/test04s",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Test04> getAll() {
        log.debug("REST request to get all Test04s");
        return test04Repository.findAll();
    }

    /**
     * GET  /test04s/:id -> get the "id" test04.
     */
    @RequestMapping(value = "/test04s/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Test04> get(@PathVariable Long id) {
        log.debug("REST request to get Test04 : {}", id);
        return Optional.ofNullable(test04Repository.findOne(id))
            .map(test04 -> new ResponseEntity<>(
                test04,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /test04s/:id -> delete the "id" test04.
     */
    @RequestMapping(value = "/test04s/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Test04 : {}", id);
        test04Repository.delete(id);
    }
}
