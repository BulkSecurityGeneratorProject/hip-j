package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Master_bagggg;
import com.mycompany.myapp.repository.Master_baggggRepository;
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
 * REST controller for managing Master_bagggg.
 */
@RestController
@RequestMapping("/api")
public class Master_baggggResource {

    private final Logger log = LoggerFactory.getLogger(Master_baggggResource.class);

    @Inject
    private Master_baggggRepository master_baggggRepository;

    /**
     * POST  /master_baggggs -> Create a new master_bagggg.
     */
    @RequestMapping(value = "/master_baggggs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Master_bagggg master_bagggg) throws URISyntaxException {
        log.debug("REST request to save Master_bagggg : {}", master_bagggg);
        if (master_bagggg.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new master_bagggg cannot already have an ID").build();
        }
        master_baggggRepository.save(master_bagggg);
        return ResponseEntity.created(new URI("/api/master_baggggs/" + master_bagggg.getId())).build();
    }

    /**
     * PUT  /master_baggggs -> Updates an existing master_bagggg.
     */
    @RequestMapping(value = "/master_baggggs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Master_bagggg master_bagggg) throws URISyntaxException {
        log.debug("REST request to update Master_bagggg : {}", master_bagggg);
        if (master_bagggg.getId() == null) {
            return create(master_bagggg);
        }
        master_baggggRepository.save(master_bagggg);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /master_baggggs -> get all the master_baggggs.
     */
    @RequestMapping(value = "/master_baggggs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Master_bagggg>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Master_bagggg> page = master_baggggRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/master_baggggs", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /master_baggggs/:id -> get the "id" master_bagggg.
     */
    @RequestMapping(value = "/master_baggggs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Master_bagggg> get(@PathVariable Long id) {
        log.debug("REST request to get Master_bagggg : {}", id);
        return Optional.ofNullable(master_baggggRepository.findOne(id))
            .map(master_bagggg -> new ResponseEntity<>(
                master_bagggg,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /master_baggggs/:id -> delete the "id" master_bagggg.
     */
    @RequestMapping(value = "/master_baggggs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Master_bagggg : {}", id);
        master_baggggRepository.delete(id);
    }
}
