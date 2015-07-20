package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Master_chef;
import com.mycompany.myapp.repository.Master_chefRepository;
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
 * REST controller for managing Master_chef.
 */
@RestController
@RequestMapping("/api")
public class Master_chefResource {

    private final Logger log = LoggerFactory.getLogger(Master_chefResource.class);

    @Inject
    private Master_chefRepository master_chefRepository;

    /**
     * POST  /master_chefs -> Create a new master_chef.
     */
    @RequestMapping(value = "/master_chefs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Master_chef master_chef) throws URISyntaxException {
        log.debug("REST request to save Master_chef : {}", master_chef);
        if (master_chef.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new master_chef cannot already have an ID").build();
        }
        master_chefRepository.save(master_chef);
        return ResponseEntity.created(new URI("/api/master_chefs/" + master_chef.getId())).build();
    }

    /**
     * PUT  /master_chefs -> Updates an existing master_chef.
     */
    @RequestMapping(value = "/master_chefs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Master_chef master_chef) throws URISyntaxException {
        log.debug("REST request to update Master_chef : {}", master_chef);
        if (master_chef.getId() == null) {
            return create(master_chef);
        }
        master_chefRepository.save(master_chef);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /master_chefs -> get all the master_chefs.
     */
    @RequestMapping(value = "/master_chefs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Master_chef>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Master_chef> page = master_chefRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/master_chefs", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /master_chefs/:id -> get the "id" master_chef.
     */
    @RequestMapping(value = "/master_chefs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Master_chef> get(@PathVariable Long id) {
        log.debug("REST request to get Master_chef : {}", id);
        return Optional.ofNullable(master_chefRepository.findOne(id))
            .map(master_chef -> new ResponseEntity<>(
                master_chef,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /master_chefs/:id -> delete the "id" master_chef.
     */
    @RequestMapping(value = "/master_chefs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Master_chef : {}", id);
        master_chefRepository.delete(id);
    }
}
