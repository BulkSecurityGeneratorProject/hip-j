package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Master_d;
import com.mycompany.myapp.repository.Master_dRepository;
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
 * REST controller for managing Master_d.
 */
@RestController
@RequestMapping("/api")
public class Master_dResource {

    private final Logger log = LoggerFactory.getLogger(Master_dResource.class);

    @Inject
    private Master_dRepository master_dRepository;

    /**
     * POST  /master_ds -> Create a new master_d.
     */
    @RequestMapping(value = "/master_ds",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Master_d master_d) throws URISyntaxException {
        log.debug("REST request to save Master_d : {}", master_d);
        if (master_d.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new master_d cannot already have an ID").build();
        }
        master_dRepository.save(master_d);
        return ResponseEntity.created(new URI("/api/master_ds/" + master_d.getId())).build();
    }

    /**
     * PUT  /master_ds -> Updates an existing master_d.
     */
    @RequestMapping(value = "/master_ds",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Master_d master_d) throws URISyntaxException {
        log.debug("REST request to update Master_d : {}", master_d);
        if (master_d.getId() == null) {
            return create(master_d);
        }
        master_dRepository.save(master_d);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /master_ds -> get all the master_ds.
     */
    @RequestMapping(value = "/master_ds",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Master_d>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Master_d> page = master_dRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/master_ds", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /master_ds/:id -> get the "id" master_d.
     */
    @RequestMapping(value = "/master_ds/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Master_d> get(@PathVariable Long id) {
        log.debug("REST request to get Master_d : {}", id);
        return Optional.ofNullable(master_dRepository.findOne(id))
            .map(master_d -> new ResponseEntity<>(
                master_d,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /master_ds/:id -> delete the "id" master_d.
     */
    @RequestMapping(value = "/master_ds/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Master_d : {}", id);
        master_dRepository.delete(id);
    }
}
