package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Morn;
import com.mycompany.myapp.repository.MornRepository;
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
 * REST controller for managing Morn.
 */
@RestController
@RequestMapping("/api")
public class MornResource {

    private final Logger log = LoggerFactory.getLogger(MornResource.class);

    @Inject
    private MornRepository mornRepository;

    /**
     * POST  /morns -> Create a new morn.
     */
    @RequestMapping(value = "/morns",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Morn morn) throws URISyntaxException {
        log.debug("REST request to save Morn : {}", morn);
        if (morn.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new morn cannot already have an ID").build();
        }
        mornRepository.save(morn);
        return ResponseEntity.created(new URI("/api/morns/" + morn.getId())).build();
    }

    /**
     * PUT  /morns -> Updates an existing morn.
     */
    @RequestMapping(value = "/morns",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Morn morn) throws URISyntaxException {
        log.debug("REST request to update Morn : {}", morn);
        if (morn.getId() == null) {
            return create(morn);
        }
        mornRepository.save(morn);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /morns -> get all the morns.
     */
    @RequestMapping(value = "/morns",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Morn> getAll() {
        log.debug("REST request to get all Morns");
        return mornRepository.findAll();
    }

    /**
     * GET  /morns/:id -> get the "id" morn.
     */
    @RequestMapping(value = "/morns/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Morn> get(@PathVariable Long id) {
        log.debug("REST request to get Morn : {}", id);
        return Optional.ofNullable(mornRepository.findOne(id))
            .map(morn -> new ResponseEntity<>(
                morn,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /morns/:id -> delete the "id" morn.
     */
    @RequestMapping(value = "/morns/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Morn : {}", id);
        mornRepository.delete(id);
    }
}
