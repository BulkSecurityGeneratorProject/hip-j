package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Master_chacha;
import com.mycompany.myapp.repository.Master_chachaRepository;
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
 * REST controller for managing Master_chacha.
 */
@RestController
@RequestMapping("/api")
public class Master_chachaResource {

    private final Logger log = LoggerFactory.getLogger(Master_chachaResource.class);

    @Inject
    private Master_chachaRepository master_chachaRepository;

    /**
     * POST  /master_chachas -> Create a new master_chacha.
     */
    @RequestMapping(value = "/master_chachas",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Master_chacha master_chacha) throws URISyntaxException {
        log.debug("REST request to save Master_chacha : {}", master_chacha);
        if (master_chacha.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new master_chacha cannot already have an ID").build();
        }
        master_chachaRepository.save(master_chacha);
        return ResponseEntity.created(new URI("/api/master_chachas/" + master_chacha.getId())).build();
    }

    /**
     * PUT  /master_chachas -> Updates an existing master_chacha.
     */
    @RequestMapping(value = "/master_chachas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Master_chacha master_chacha) throws URISyntaxException {
        log.debug("REST request to update Master_chacha : {}", master_chacha);
        if (master_chacha.getId() == null) {
            return create(master_chacha);
        }
        master_chachaRepository.save(master_chacha);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /master_chachas -> get all the master_chachas.
     */
    @RequestMapping(value = "/master_chachas",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Master_chacha>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Master_chacha> page = master_chachaRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/master_chachas", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /master_chachas/:id -> get the "id" master_chacha.
     */
    @RequestMapping(value = "/master_chachas/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Master_chacha> get(@PathVariable Long id) {
        log.debug("REST request to get Master_chacha : {}", id);
        return Optional.ofNullable(master_chachaRepository.findOne(id))
            .map(master_chacha -> new ResponseEntity<>(
                master_chacha,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /master_chachas/:id -> delete the "id" master_chacha.
     */
    @RequestMapping(value = "/master_chachas/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Master_chacha : {}", id);
        master_chachaRepository.delete(id);
    }
}
