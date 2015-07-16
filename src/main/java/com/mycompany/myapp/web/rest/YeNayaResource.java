package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.YeNaya;
import com.mycompany.myapp.repository.YeNayaRepository;
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
 * REST controller for managing YeNaya.
 */
@RestController
@RequestMapping("/api")
public class YeNayaResource {

    private final Logger log = LoggerFactory.getLogger(YeNayaResource.class);

    @Inject
    private YeNayaRepository yeNayaRepository;

    /**
     * POST  /yeNayas -> Create a new yeNaya.
     */
    @RequestMapping(value = "/yeNayas",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody YeNaya yeNaya) throws URISyntaxException {
        log.debug("REST request to save YeNaya : {}", yeNaya);
        if (yeNaya.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new yeNaya cannot already have an ID").build();
        }
        yeNayaRepository.save(yeNaya);
        return ResponseEntity.created(new URI("/api/yeNayas/" + yeNaya.getId())).build();
    }

    /**
     * PUT  /yeNayas -> Updates an existing yeNaya.
     */
    @RequestMapping(value = "/yeNayas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody YeNaya yeNaya) throws URISyntaxException {
        log.debug("REST request to update YeNaya : {}", yeNaya);
        if (yeNaya.getId() == null) {
            return create(yeNaya);
        }
        yeNayaRepository.save(yeNaya);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /yeNayas -> get all the yeNayas.
     */
    @RequestMapping(value = "/yeNayas",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<YeNaya> getAll() {
        log.debug("REST request to get all YeNayas");
        return yeNayaRepository.findAll();
    }

    /**
     * GET  /yeNayas/:id -> get the "id" yeNaya.
     */
    @RequestMapping(value = "/yeNayas/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YeNaya> get(@PathVariable Long id) {
        log.debug("REST request to get YeNaya : {}", id);
        return Optional.ofNullable(yeNayaRepository.findOne(id))
            .map(yeNaya -> new ResponseEntity<>(
                yeNaya,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /yeNayas/:id -> delete the "id" yeNaya.
     */
    @RequestMapping(value = "/yeNayas/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete YeNaya : {}", id);
        yeNayaRepository.delete(id);
    }
}
