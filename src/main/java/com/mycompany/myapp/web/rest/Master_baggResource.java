package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Master_bagg;
import com.mycompany.myapp.repository.Master_baggRepository;
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
 * REST controller for managing Master_bagg.
 */
@RestController
@RequestMapping("/api")
public class Master_baggResource {

    private final Logger log = LoggerFactory.getLogger(Master_baggResource.class);

    @Inject
    private Master_baggRepository master_baggRepository;

    /**
     * POST  /master_baggs -> Create a new master_bagg.
     */
    @RequestMapping(value = "/master_baggs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Master_bagg master_bagg) throws URISyntaxException {
        log.debug("REST request to save Master_bagg : {}", master_bagg);
        if (master_bagg.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new master_bagg cannot already have an ID").build();
        }
        master_baggRepository.save(master_bagg);
        return ResponseEntity.created(new URI("/api/master_baggs/" + master_bagg.getId())).build();
    }

    /**
     * PUT  /master_baggs -> Updates an existing master_bagg.
     */
    @RequestMapping(value = "/master_baggs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Master_bagg master_bagg) throws URISyntaxException {
        log.debug("REST request to update Master_bagg : {}", master_bagg);
        if (master_bagg.getId() == null) {
            return create(master_bagg);
        }
        master_baggRepository.save(master_bagg);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /master_baggs -> get all the master_baggs.
     */
    @RequestMapping(value = "/master_baggs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Master_bagg>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Master_bagg> page = master_baggRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/master_baggs", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /master_baggs/:id -> get the "id" master_bagg.
     */
    @RequestMapping(value = "/master_baggs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Master_bagg> get(@PathVariable Long id) {
        log.debug("REST request to get Master_bagg : {}", id);
        return Optional.ofNullable(master_baggRepository.findOne(id))
            .map(master_bagg -> new ResponseEntity<>(
                master_bagg,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /master_baggs/:id -> delete the "id" master_bagg.
     */
    @RequestMapping(value = "/master_baggs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Master_bagg : {}", id);
        master_baggRepository.delete(id);
    }
}
