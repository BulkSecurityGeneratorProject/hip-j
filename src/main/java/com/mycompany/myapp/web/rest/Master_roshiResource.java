package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Master_roshi;
import com.mycompany.myapp.repository.Master_roshiRepository;
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
 * REST controller for managing Master_roshi.
 */
@RestController
@RequestMapping("/api")
public class Master_roshiResource {

    private final Logger log = LoggerFactory.getLogger(Master_roshiResource.class);

    @Inject
    private Master_roshiRepository master_roshiRepository;

    /**
     * POST  /master_roshis -> Create a new master_roshi.
     */
    @RequestMapping(value = "/master_roshis",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Master_roshi master_roshi) throws URISyntaxException {
        log.debug("REST request to save Master_roshi : {}", master_roshi);
        if (master_roshi.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new master_roshi cannot already have an ID").build();
        }
        master_roshiRepository.save(master_roshi);
        return ResponseEntity.created(new URI("/api/master_roshis/" + master_roshi.getId())).build();
    }

    /**
     * PUT  /master_roshis -> Updates an existing master_roshi.
     */
    @RequestMapping(value = "/master_roshis",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Master_roshi master_roshi) throws URISyntaxException {
        log.debug("REST request to update Master_roshi : {}", master_roshi);
        if (master_roshi.getId() == null) {
            return create(master_roshi);
        }
        master_roshiRepository.save(master_roshi);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /master_roshis -> get all the master_roshis.
     */
    @RequestMapping(value = "/master_roshis",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Master_roshi>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Master_roshi> page = master_roshiRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/master_roshis", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /master_roshis/:id -> get the "id" master_roshi.
     */
    @RequestMapping(value = "/master_roshis/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Master_roshi> get(@PathVariable Long id) {
        log.debug("REST request to get Master_roshi : {}", id);
        return Optional.ofNullable(master_roshiRepository.findOne(id))
            .map(master_roshi -> new ResponseEntity<>(
                master_roshi,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /master_roshis/:id -> delete the "id" master_roshi.
     */
    @RequestMapping(value = "/master_roshis/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Master_roshi : {}", id);
        master_roshiRepository.delete(id);
    }
}
