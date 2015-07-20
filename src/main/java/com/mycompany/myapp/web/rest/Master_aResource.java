package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Master_a;
import com.mycompany.myapp.repository.Master_aRepository;
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
 * REST controller for managing Master_a.
 */
@RestController
@RequestMapping("/api")
public class Master_aResource {

    private final Logger log = LoggerFactory.getLogger(Master_aResource.class);

    @Inject
    private Master_aRepository master_aRepository;

    /**
     * POST  /master_as -> Create a new master_a.
     */
    @RequestMapping(value = "/master_as",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Master_a master_a) throws URISyntaxException {
        log.debug("REST request to save Master_a : {}", master_a);
        if (master_a.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new master_a cannot already have an ID").build();
        }
        master_aRepository.save(master_a);
        return ResponseEntity.created(new URI("/api/master_as/" + master_a.getId())).build();
    }

    /**
     * PUT  /master_as -> Updates an existing master_a.
     */
    @RequestMapping(value = "/master_as",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Master_a master_a) throws URISyntaxException {
        log.debug("REST request to update Master_a : {}", master_a);
        if (master_a.getId() == null) {
            return create(master_a);
        }
        master_aRepository.save(master_a);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /master_as -> get all the master_as.
     */
    @RequestMapping(value = "/master_as",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Master_a>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Master_a> page = master_aRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/master_as", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /master_as/:id -> get the "id" master_a.
     */
    @RequestMapping(value = "/master_as/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Master_a> get(@PathVariable Long id) {
        log.debug("REST request to get Master_a : {}", id);
        return Optional.ofNullable(master_aRepository.findOne(id))
            .map(master_a -> new ResponseEntity<>(
                master_a,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /master_as/:id -> delete the "id" master_a.
     */
    @RequestMapping(value = "/master_as/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Master_a : {}", id);
        master_aRepository.delete(id);
    }
}
