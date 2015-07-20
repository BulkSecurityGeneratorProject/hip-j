package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Master_mama;
import com.mycompany.myapp.repository.Master_mamaRepository;
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
 * REST controller for managing Master_mama.
 */
@RestController
@RequestMapping("/api")
public class Master_mamaResource {

    private final Logger log = LoggerFactory.getLogger(Master_mamaResource.class);

    @Inject
    private Master_mamaRepository master_mamaRepository;

    /**
     * POST  /master_mamas -> Create a new master_mama.
     */
    @RequestMapping(value = "/master_mamas",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Master_mama master_mama) throws URISyntaxException {
        log.debug("REST request to save Master_mama : {}", master_mama);
        if (master_mama.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new master_mama cannot already have an ID").build();
        }
        master_mamaRepository.save(master_mama);
        return ResponseEntity.created(new URI("/api/master_mamas/" + master_mama.getId())).build();
    }

    /**
     * PUT  /master_mamas -> Updates an existing master_mama.
     */
    @RequestMapping(value = "/master_mamas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Master_mama master_mama) throws URISyntaxException {
        log.debug("REST request to update Master_mama : {}", master_mama);
        if (master_mama.getId() == null) {
            return create(master_mama);
        }
        master_mamaRepository.save(master_mama);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /master_mamas -> get all the master_mamas.
     */
    @RequestMapping(value = "/master_mamas",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Master_mama>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Master_mama> page = master_mamaRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/master_mamas", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /master_mamas/:id -> get the "id" master_mama.
     */
    @RequestMapping(value = "/master_mamas/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Master_mama> get(@PathVariable Long id) {
        log.debug("REST request to get Master_mama : {}", id);
        return Optional.ofNullable(master_mamaRepository.findOne(id))
            .map(master_mama -> new ResponseEntity<>(
                master_mama,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /master_mamas/:id -> delete the "id" master_mama.
     */
    @RequestMapping(value = "/master_mamas/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Master_mama : {}", id);
        master_mamaRepository.delete(id);
    }
}
