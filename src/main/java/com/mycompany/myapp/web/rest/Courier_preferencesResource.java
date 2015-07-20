package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Courier_preferences;
import com.mycompany.myapp.repository.Courier_preferencesRepository;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Courier_preferences.
 */
@RestController
@RequestMapping("/api")
public class Courier_preferencesResource {

    private final Logger log = LoggerFactory.getLogger(Courier_preferencesResource.class);

    @Inject
    private Courier_preferencesRepository courier_preferencesRepository;

    /**
     * POST  /courier_preferencess -> Create a new courier_preferences.
     */
    @RequestMapping(value = "/courier_preferencess",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Courier_preferences courier_preferences) throws URISyntaxException {
        log.debug("REST request to save Courier_preferences : {}", courier_preferences);
        if (courier_preferences.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new courier_preferences cannot already have an ID").build();
        }
        courier_preferencesRepository.save(courier_preferences);
        return ResponseEntity.created(new URI("/api/courier_preferencess/" + courier_preferences.getId())).build();
    }

    /**
     * PUT  /courier_preferencess -> Updates an existing courier_preferences.
     */
    @RequestMapping(value = "/courier_preferencess",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Courier_preferences courier_preferences) throws URISyntaxException {
        log.debug("REST request to update Courier_preferences : {}", courier_preferences);
        if (courier_preferences.getId() == null) {
            return create(courier_preferences);
        }
        courier_preferencesRepository.save(courier_preferences);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /courier_preferencess -> get all the courier_preferencess.
     */
    @RequestMapping(value = "/courier_preferencess",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Courier_preferences>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Courier_preferences> page = courier_preferencesRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/courier_preferencess", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /courier_preferencess/:id -> get the "id" courier_preferences.
     */
    @RequestMapping(value = "/courier_preferencess/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Courier_preferences> get(@PathVariable Long id) {
        log.debug("REST request to get Courier_preferences : {}", id);
        return Optional.ofNullable(courier_preferencesRepository.findOne(id))
            .map(courier_preferences -> new ResponseEntity<>(
                courier_preferences,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /courier_preferencess/:id -> delete the "id" courier_preferences.
     */
    @RequestMapping(value = "/courier_preferencess/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Courier_preferences : {}", id);
        courier_preferencesRepository.delete(id);
    }
}
