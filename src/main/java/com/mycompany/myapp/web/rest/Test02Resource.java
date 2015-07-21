package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Test02;
import com.mycompany.myapp.repository.Test02Repository;
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
 * REST controller for managing Test02.
 */
@RestController
@RequestMapping("/api")
public class Test02Resource {

    private final Logger log = LoggerFactory.getLogger(Test02Resource.class);

    @Inject
    private Test02Repository test02Repository;

    /**
     * POST  /test02s -> Create a new test02.
     */
    @RequestMapping(value = "/test02s",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Test02 test02) throws URISyntaxException {
        log.debug("REST request to save Test02 : {}", test02);
        if (test02.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new test02 cannot already have an ID").build();
        }
        test02Repository.save(test02);
        return ResponseEntity.created(new URI("/api/test02s/" + test02.getId())).build();
    }

    /**
     * PUT  /test02s -> Updates an existing test02.
     */
    @RequestMapping(value = "/test02s",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Test02 test02) throws URISyntaxException {
        log.debug("REST request to update Test02 : {}", test02);
        if (test02.getId() == null) {
            return create(test02);
        }
        test02Repository.save(test02);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /test02s -> get all the test02s.
     */
    @RequestMapping(value = "/test02s",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Test02> getAll() {
        log.debug("REST request to get all Test02s");
        return test02Repository.findAll();
    }

    /**
     * GET  /test02s/:id -> get the "id" test02.
     */
    @RequestMapping(value = "/test02s/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Test02> get(@PathVariable Long id) {
        log.debug("REST request to get Test02 : {}", id);
        return Optional.ofNullable(test02Repository.findOne(id))
            .map(test02 -> new ResponseEntity<>(
                test02,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /test02s/:id -> delete the "id" test02.
     */
    @RequestMapping(value = "/test02s/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Test02 : {}", id);
        test02Repository.delete(id);
    }
}
