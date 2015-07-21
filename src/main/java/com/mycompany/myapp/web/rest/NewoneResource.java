package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Newone;
import com.mycompany.myapp.repository.NewoneRepository;
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
 * REST controller for managing Newone.
 */
@RestController
@RequestMapping("/api")
public class NewoneResource {

    private final Logger log = LoggerFactory.getLogger(NewoneResource.class);

    @Inject
    private NewoneRepository newoneRepository;

    /**
     * POST  /newones -> Create a new newone.
     */
    @RequestMapping(value = "/newones",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Newone newone) throws URISyntaxException {
        log.debug("REST request to save Newone : {}", newone);
        if (newone.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new newone cannot already have an ID").build();
        }
        newoneRepository.save(newone);
        return ResponseEntity.created(new URI("/api/newones/" + newone.getId())).build();
    }

    /**
     * PUT  /newones -> Updates an existing newone.
     */
    @RequestMapping(value = "/newones",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Newone newone) throws URISyntaxException {
        log.debug("REST request to update Newone : {}", newone);
        if (newone.getId() == null) {
            return create(newone);
        }
        newoneRepository.save(newone);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /newones -> get all the newones.
     */
    @RequestMapping(value = "/newones",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Newone>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Newone> page = newoneRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/newones", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /newones/:id -> get the "id" newone.
     */
    @RequestMapping(value = "/newones/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Newone> get(@PathVariable Long id) {
        log.debug("REST request to get Newone : {}", id);
        return Optional.ofNullable(newoneRepository.findOne(id))
            .map(newone -> new ResponseEntity<>(
                newone,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /newones/:id -> delete the "id" newone.
     */
    @RequestMapping(value = "/newones/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Newone : {}", id);
        newoneRepository.delete(id);
    }
}
