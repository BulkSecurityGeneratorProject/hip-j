package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Master_baggg;
import com.mycompany.myapp.repository.Master_bagggRepository;
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
 * REST controller for managing Master_baggg.
 */
@RestController
@RequestMapping("/api")
public class Master_bagggResource {

    private final Logger log = LoggerFactory.getLogger(Master_bagggResource.class);

    @Inject
    private Master_bagggRepository master_bagggRepository;

    /**
     * POST  /master_bagggs -> Create a new master_baggg.
     */
    @RequestMapping(value = "/master_bagggs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Master_baggg master_baggg) throws URISyntaxException {
        log.debug("REST request to save Master_baggg : {}", master_baggg);
        if (master_baggg.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new master_baggg cannot already have an ID").build();
        }
        master_bagggRepository.save(master_baggg);
        return ResponseEntity.created(new URI("/api/master_bagggs/" + master_baggg.getId())).build();
    }

    /**
     * PUT  /master_bagggs -> Updates an existing master_baggg.
     */
    @RequestMapping(value = "/master_bagggs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Master_baggg master_baggg) throws URISyntaxException {
        log.debug("REST request to update Master_baggg : {}", master_baggg);
        if (master_baggg.getId() == null) {
            return create(master_baggg);
        }
        master_bagggRepository.save(master_baggg);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /master_bagggs -> get all the master_bagggs.
     */
    @RequestMapping(value = "/master_bagggs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Master_baggg>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Master_baggg> page = master_bagggRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/master_bagggs", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /master_bagggs/:id -> get the "id" master_baggg.
     */
    @RequestMapping(value = "/master_bagggs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Master_baggg> get(@PathVariable Long id) {
        log.debug("REST request to get Master_baggg : {}", id);
        return Optional.ofNullable(master_bagggRepository.findOne(id))
            .map(master_baggg -> new ResponseEntity<>(
                master_baggg,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /master_bagggs/:id -> delete the "id" master_baggg.
     */
    @RequestMapping(value = "/master_bagggs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Master_baggg : {}", id);
        master_bagggRepository.delete(id);
    }
}
