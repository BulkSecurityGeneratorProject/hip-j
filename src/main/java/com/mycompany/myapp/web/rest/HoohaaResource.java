package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Hoohaa;
import com.mycompany.myapp.repository.HoohaaRepository;
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
 * REST controller for managing Hoohaa.
 */
@RestController
@RequestMapping("/api")
public class HoohaaResource {

    private final Logger log = LoggerFactory.getLogger(HoohaaResource.class);

    @Inject
    private HoohaaRepository hoohaaRepository;

    /**
     * POST  /hoohaas -> Create a new hoohaa.
     */
    @RequestMapping(value = "/hoohaas",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Hoohaa hoohaa) throws URISyntaxException {
        log.debug("REST request to save Hoohaa : {}", hoohaa);
        if (hoohaa.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new hoohaa cannot already have an ID").build();
        }
        hoohaaRepository.save(hoohaa);
        return ResponseEntity.created(new URI("/api/hoohaas/" + hoohaa.getId())).build();
    }

    /**
     * PUT  /hoohaas -> Updates an existing hoohaa.
     */
    @RequestMapping(value = "/hoohaas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Hoohaa hoohaa) throws URISyntaxException {
        log.debug("REST request to update Hoohaa : {}", hoohaa);
        if (hoohaa.getId() == null) {
            return create(hoohaa);
        }
        hoohaaRepository.save(hoohaa);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /hoohaas -> get all the hoohaas.
     */
    @RequestMapping(value = "/hoohaas",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Hoohaa> getAll() {
        log.debug("REST request to get all Hoohaas");
        return hoohaaRepository.findAll();
    }

    /**
     * GET  /hoohaas/:id -> get the "id" hoohaa.
     */
    @RequestMapping(value = "/hoohaas/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Hoohaa> get(@PathVariable Long id) {
        log.debug("REST request to get Hoohaa : {}", id);
        return Optional.ofNullable(hoohaaRepository.findOne(id))
            .map(hoohaa -> new ResponseEntity<>(
                hoohaa,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hoohaas/:id -> delete the "id" hoohaa.
     */
    @RequestMapping(value = "/hoohaas/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Hoohaa : {}", id);
        hoohaaRepository.delete(id);
    }
}
