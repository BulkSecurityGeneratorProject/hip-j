package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Realdil;
import com.mycompany.myapp.repository.RealdilRepository;
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
 * REST controller for managing Realdil.
 */
@RestController
@RequestMapping("/api")
public class RealdilResource {

    private final Logger log = LoggerFactory.getLogger(RealdilResource.class);

    @Inject
    private RealdilRepository realdilRepository;

    /**
     * POST  /realdils -> Create a new realdil.
     */
    @RequestMapping(value = "/realdils",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Realdil realdil) throws URISyntaxException {
        log.debug("REST request to save Realdil : {}", realdil);
        if (realdil.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new realdil cannot already have an ID").build();
        }
        realdilRepository.save(realdil);
        return ResponseEntity.created(new URI("/api/realdils/" + realdil.getId())).build();
    }

    /**
     * PUT  /realdils -> Updates an existing realdil.
     */
    @RequestMapping(value = "/realdils",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Realdil realdil) throws URISyntaxException {
        log.debug("REST request to update Realdil : {}", realdil);
        if (realdil.getId() == null) {
            return create(realdil);
        }
        realdilRepository.save(realdil);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /realdils -> get all the realdils.
     */
    @RequestMapping(value = "/realdils",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Realdil> getAll() {
        log.debug("REST request to get all Realdils");
        return realdilRepository.findAll();
    }

    /**
     * GET  /realdils/:id -> get the "id" realdil.
     */
    @RequestMapping(value = "/realdils/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Realdil> get(@PathVariable Long id) {
        log.debug("REST request to get Realdil : {}", id);
        return Optional.ofNullable(realdilRepository.findOne(id))
            .map(realdil -> new ResponseEntity<>(
                realdil,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /realdils/:id -> delete the "id" realdil.
     */
    @RequestMapping(value = "/realdils/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Realdil : {}", id);
        realdilRepository.delete(id);
    }
}
