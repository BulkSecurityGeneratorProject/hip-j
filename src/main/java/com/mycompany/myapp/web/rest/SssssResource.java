package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Sssss;
import com.mycompany.myapp.repository.SssssRepository;
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
 * REST controller for managing Sssss.
 */
@RestController
@RequestMapping("/api")
public class SssssResource {

    private final Logger log = LoggerFactory.getLogger(SssssResource.class);

    @Inject
    private SssssRepository sssssRepository;

    /**
     * POST  /ssssss -> Create a new sssss.
     */
    @RequestMapping(value = "/ssssss",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Sssss sssss) throws URISyntaxException {
        log.debug("REST request to save Sssss : {}", sssss);
        if (sssss.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new sssss cannot already have an ID").build();
        }
        sssssRepository.save(sssss);
        return ResponseEntity.created(new URI("/api/ssssss/" + sssss.getId())).build();
    }

    /**
     * PUT  /ssssss -> Updates an existing sssss.
     */
    @RequestMapping(value = "/ssssss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Sssss sssss) throws URISyntaxException {
        log.debug("REST request to update Sssss : {}", sssss);
        if (sssss.getId() == null) {
            return create(sssss);
        }
        sssssRepository.save(sssss);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /ssssss -> get all the ssssss.
     */
    @RequestMapping(value = "/ssssss",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Sssss> getAll() {
        log.debug("REST request to get all Ssssss");
        return sssssRepository.findAll();
    }

    /**
     * GET  /ssssss/:id -> get the "id" sssss.
     */
    @RequestMapping(value = "/ssssss/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Sssss> get(@PathVariable Long id) {
        log.debug("REST request to get Sssss : {}", id);
        return Optional.ofNullable(sssssRepository.findOne(id))
            .map(sssss -> new ResponseEntity<>(
                sssss,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /ssssss/:id -> delete the "id" sssss.
     */
    @RequestMapping(value = "/ssssss/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Sssss : {}", id);
        sssssRepository.delete(id);
    }
}
