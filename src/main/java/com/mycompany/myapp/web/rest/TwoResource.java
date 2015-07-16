package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Two;
import com.mycompany.myapp.repository.TwoRepository;
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
 * REST controller for managing Two.
 */
@RestController
@RequestMapping("/api")
public class TwoResource {

    private final Logger log = LoggerFactory.getLogger(TwoResource.class);

    @Inject
    private TwoRepository twoRepository;

    /**
     * POST  /twos -> Create a new two.
     */
    @RequestMapping(value = "/twos",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Two two) throws URISyntaxException {
        log.debug("REST request to save Two : {}", two);
        if (two.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new two cannot already have an ID").build();
        }
        twoRepository.save(two);
        return ResponseEntity.created(new URI("/api/twos/" + two.getId())).build();
    }

    /**
     * PUT  /twos -> Updates an existing two.
     */
    @RequestMapping(value = "/twos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Two two) throws URISyntaxException {
        log.debug("REST request to update Two : {}", two);
        if (two.getId() == null) {
            return create(two);
        }
        twoRepository.save(two);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /twos -> get all the twos.
     */
    @RequestMapping(value = "/twos",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Two> getAll() {
        log.debug("REST request to get all Twos");
        return twoRepository.findAll();
    }

    /**
     * GET  /twos/:id -> get the "id" two.
     */
    @RequestMapping(value = "/twos/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Two> get(@PathVariable Long id) {
        log.debug("REST request to get Two : {}", id);
        return Optional.ofNullable(twoRepository.findOne(id))
            .map(two -> new ResponseEntity<>(
                two,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /twos/:id -> delete the "id" two.
     */
    @RequestMapping(value = "/twos/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Two : {}", id);
        twoRepository.delete(id);
    }
}
