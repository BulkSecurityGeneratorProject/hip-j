package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Mewone;
import com.mycompany.myapp.repository.MewoneRepository;
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
 * REST controller for managing Mewone.
 */
@RestController
@RequestMapping("/api")
public class MewoneResource {

    private final Logger log = LoggerFactory.getLogger(MewoneResource.class);

    @Inject
    private MewoneRepository mewoneRepository;

    /**
     * POST  /mewones -> Create a new mewone.
     */
    @RequestMapping(value = "/mewones",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Mewone mewone) throws URISyntaxException {
        log.debug("REST request to save Mewone : {}", mewone);
        if (mewone.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new mewone cannot already have an ID").build();
        }
        mewoneRepository.save(mewone);
        return ResponseEntity.created(new URI("/api/mewones/" + mewone.getId())).build();
    }

    /**
     * PUT  /mewones -> Updates an existing mewone.
     */
    @RequestMapping(value = "/mewones",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Mewone mewone) throws URISyntaxException {
        log.debug("REST request to update Mewone : {}", mewone);
        if (mewone.getId() == null) {
            return create(mewone);
        }
        mewoneRepository.save(mewone);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /mewones -> get all the mewones.
     */
    @RequestMapping(value = "/mewones",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Mewone>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Mewone> page = mewoneRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mewones", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mewones/:id -> get the "id" mewone.
     */
    @RequestMapping(value = "/mewones/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Mewone> get(@PathVariable Long id) {
        log.debug("REST request to get Mewone : {}", id);
        return Optional.ofNullable(mewoneRepository.findOne(id))
            .map(mewone -> new ResponseEntity<>(
                mewone,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /mewones/:id -> delete the "id" mewone.
     */
    @RequestMapping(value = "/mewones/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Mewone : {}", id);
        mewoneRepository.delete(id);
    }
}
