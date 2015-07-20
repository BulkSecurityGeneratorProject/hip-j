package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Master_c;
import com.mycompany.myapp.repository.Master_cRepository;
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
 * REST controller for managing Master_c.
 */
@RestController
@RequestMapping("/api")
public class Master_cResource {

    private final Logger log = LoggerFactory.getLogger(Master_cResource.class);

    @Inject
    private Master_cRepository master_cRepository;

    /**
     * POST  /master_cs -> Create a new master_c.
     */
    @RequestMapping(value = "/master_cs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Master_c master_c) throws URISyntaxException {
        log.debug("REST request to save Master_c : {}", master_c);
        if (master_c.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new master_c cannot already have an ID").build();
        }
        master_cRepository.save(master_c);
        return ResponseEntity.created(new URI("/api/master_cs/" + master_c.getId())).build();
    }

    /**
     * PUT  /master_cs -> Updates an existing master_c.
     */
    @RequestMapping(value = "/master_cs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Master_c master_c) throws URISyntaxException {
        log.debug("REST request to update Master_c : {}", master_c);
        if (master_c.getId() == null) {
            return create(master_c);
        }
        master_cRepository.save(master_c);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /master_cs -> get all the master_cs.
     */
    @RequestMapping(value = "/master_cs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Master_c>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Master_c> page = master_cRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/master_cs", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /master_cs/:id -> get the "id" master_c.
     */
    @RequestMapping(value = "/master_cs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Master_c> get(@PathVariable Long id) {
        log.debug("REST request to get Master_c : {}", id);
        return Optional.ofNullable(master_cRepository.findOne(id))
            .map(master_c -> new ResponseEntity<>(
                master_c,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /master_cs/:id -> delete the "id" master_c.
     */
    @RequestMapping(value = "/master_cs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Master_c : {}", id);
        master_cRepository.delete(id);
    }
}
