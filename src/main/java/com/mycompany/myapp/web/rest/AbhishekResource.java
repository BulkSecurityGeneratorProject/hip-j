package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Abhishek;
import com.mycompany.myapp.repository.AbhishekRepository;
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
 * REST controller for managing Abhishek.
 */
@RestController
@RequestMapping("/api")
public class AbhishekResource {

    private final Logger log = LoggerFactory.getLogger(AbhishekResource.class);

    @Inject
    private AbhishekRepository abhishekRepository;

    /**
     * POST  /abhisheks -> Create a new abhishek.
     */
    @RequestMapping(value = "/abhisheks",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Abhishek abhishek) throws URISyntaxException {
        log.debug("REST request to save Abhishek : {}", abhishek);
        if (abhishek.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new abhishek cannot already have an ID").build();
        }
        abhishekRepository.save(abhishek);
        return ResponseEntity.created(new URI("/api/abhisheks/" + abhishek.getId())).build();
    }

    /**
     * PUT  /abhisheks -> Updates an existing abhishek.
     */
    @RequestMapping(value = "/abhisheks",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Abhishek abhishek) throws URISyntaxException {
        log.debug("REST request to update Abhishek : {}", abhishek);
        if (abhishek.getId() == null) {
            return create(abhishek);
        }
        abhishekRepository.save(abhishek);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /abhisheks -> get all the abhisheks.
     */
    @RequestMapping(value = "/abhisheks",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Abhishek> getAll() {
        log.debug("REST request to get all Abhisheks");
        return abhishekRepository.findAll();
    }

    /**
     * GET  /abhisheks/:id -> get the "id" abhishek.
     */
    @RequestMapping(value = "/abhisheks/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Abhishek> get(@PathVariable Long id) {
        log.debug("REST request to get Abhishek : {}", id);
        return Optional.ofNullable(abhishekRepository.findOne(id))
            .map(abhishek -> new ResponseEntity<>(
                abhishek,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /abhisheks/:id -> delete the "id" abhishek.
     */
    @RequestMapping(value = "/abhisheks/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Abhishek : {}", id);
        abhishekRepository.delete(id);
    }
}
