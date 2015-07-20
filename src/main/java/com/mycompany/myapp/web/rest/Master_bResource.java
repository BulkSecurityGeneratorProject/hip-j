package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Master_b;
import com.mycompany.myapp.repository.Master_bRepository;
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
 * REST controller for managing Master_b.
 */
@RestController
@RequestMapping("/api")
public class Master_bResource {

    private final Logger log = LoggerFactory.getLogger(Master_bResource.class);

    @Inject
    private Master_bRepository master_bRepository;

    /**
     * POST  /master_bs -> Create a new master_b.
     */
    @RequestMapping(value = "/master_bs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Master_b master_b) throws URISyntaxException {
        log.debug("REST request to save Master_b : {}", master_b);
        if (master_b.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new master_b cannot already have an ID").build();
        }
        master_bRepository.save(master_b);
        return ResponseEntity.created(new URI("/api/master_bs/" + master_b.getId())).build();
    }

    /**
     * PUT  /master_bs -> Updates an existing master_b.
     */
    @RequestMapping(value = "/master_bs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Master_b master_b) throws URISyntaxException {
        log.debug("REST request to update Master_b : {}", master_b);
        if (master_b.getId() == null) {
            return create(master_b);
        }
        master_bRepository.save(master_b);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /master_bs -> get all the master_bs.
     */
    @RequestMapping(value = "/master_bs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Master_b>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Master_b> page = master_bRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/master_bs", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /master_bs/:id -> get the "id" master_b.
     */
    @RequestMapping(value = "/master_bs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Master_b> get(@PathVariable Long id) {
        log.debug("REST request to get Master_b : {}", id);
        return Optional.ofNullable(master_bRepository.findOne(id))
            .map(master_b -> new ResponseEntity<>(
                master_b,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /master_bs/:id -> delete the "id" master_b.
     */
    @RequestMapping(value = "/master_bs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Master_b : {}", id);
        master_bRepository.delete(id);
    }
}
