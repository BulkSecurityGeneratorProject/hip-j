package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Three;
import com.mycompany.myapp.repository.ThreeRepository;
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
 * REST controller for managing Three.
 */
@RestController
@RequestMapping("/api")
public class ThreeResource {

    private final Logger log = LoggerFactory.getLogger(ThreeResource.class);

    @Inject
    private ThreeRepository threeRepository;

    /**
     * POST  /threes -> Create a new three.
     */
    @RequestMapping(value = "/threes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Three three) throws URISyntaxException {
        log.debug("REST request to save Three : {}", three);
        if (three.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new three cannot already have an ID").build();
        }
        threeRepository.save(three);
        return ResponseEntity.created(new URI("/api/threes/" + three.getId())).build();
    }

    /**
     * PUT  /threes -> Updates an existing three.
     */
    @RequestMapping(value = "/threes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Three three) throws URISyntaxException {
        log.debug("REST request to update Three : {}", three);
        if (three.getId() == null) {
            return create(three);
        }
        threeRepository.save(three);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /threes -> get all the threes.
     */
    @RequestMapping(value = "/threes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Three> getAll() {
        log.debug("REST request to get all Threes");
        return threeRepository.findAll();
    }

    /**
     * GET  /threes/:id -> get the "id" three.
     */
    @RequestMapping(value = "/threes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Three> get(@PathVariable Long id) {
        log.debug("REST request to get Three : {}", id);
        return Optional.ofNullable(threeRepository.findOne(id))
            .map(three -> new ResponseEntity<>(
                three,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /threes/:id -> delete the "id" three.
     */
    @RequestMapping(value = "/threes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Three : {}", id);
        threeRepository.delete(id);
    }
}
