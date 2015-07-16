package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Noon;
import com.mycompany.myapp.repository.NoonRepository;
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
 * REST controller for managing Noon.
 */
@RestController
@RequestMapping("/api")
public class NoonResource {

    private final Logger log = LoggerFactory.getLogger(NoonResource.class);

    @Inject
    private NoonRepository noonRepository;

    /**
     * POST  /noons -> Create a new noon.
     */
    @RequestMapping(value = "/noons",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Noon noon) throws URISyntaxException {
        log.debug("REST request to save Noon : {}", noon);
        if (noon.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new noon cannot already have an ID").build();
        }
        noonRepository.save(noon);
        return ResponseEntity.created(new URI("/api/noons/" + noon.getId())).build();
    }

    /**
     * PUT  /noons -> Updates an existing noon.
     */
    @RequestMapping(value = "/noons",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Noon noon) throws URISyntaxException {
        log.debug("REST request to update Noon : {}", noon);
        if (noon.getId() == null) {
            return create(noon);
        }
        noonRepository.save(noon);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /noons -> get all the noons.
     */
    @RequestMapping(value = "/noons",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Noon> getAll() {
        log.debug("REST request to get all Noons");
        return noonRepository.findAll();
    }

    /**
     * GET  /noons/:id -> get the "id" noon.
     */
    @RequestMapping(value = "/noons/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Noon> get(@PathVariable Long id) {
        log.debug("REST request to get Noon : {}", id);
        return Optional.ofNullable(noonRepository.findOne(id))
            .map(noon -> new ResponseEntity<>(
                noon,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /noons/:id -> delete the "id" noon.
     */
    @RequestMapping(value = "/noons/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Noon : {}", id);
        noonRepository.delete(id);
    }
}
