package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Four;
import com.mycompany.myapp.repository.FourRepository;
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
 * REST controller for managing Four.
 */
@RestController
@RequestMapping("/api")
public class FourResource {

    private final Logger log = LoggerFactory.getLogger(FourResource.class);

    @Inject
    private FourRepository fourRepository;

    /**
     * POST  /fours -> Create a new four.
     */
    @RequestMapping(value = "/fours",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Four four) throws URISyntaxException {
        log.debug("REST request to save Four : {}", four);
        if (four.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new four cannot already have an ID").build();
        }
        fourRepository.save(four);
        return ResponseEntity.created(new URI("/api/fours/" + four.getId())).build();
    }

    /**
     * PUT  /fours -> Updates an existing four.
     */
    @RequestMapping(value = "/fours",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Four four) throws URISyntaxException {
        log.debug("REST request to update Four : {}", four);
        if (four.getId() == null) {
            return create(four);
        }
        fourRepository.save(four);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /fours -> get all the fours.
     */
    @RequestMapping(value = "/fours",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Four> getAll() {
        log.debug("REST request to get all Fours");
        return fourRepository.findAll();
    }

    /**
     * GET  /fours/:id -> get the "id" four.
     */
    @RequestMapping(value = "/fours/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Four> get(@PathVariable Long id) {
        log.debug("REST request to get Four : {}", id);
        return Optional.ofNullable(fourRepository.findOne(id))
            .map(four -> new ResponseEntity<>(
                four,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /fours/:id -> delete the "id" four.
     */
    @RequestMapping(value = "/fours/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Four : {}", id);
        fourRepository.delete(id);
    }
}
