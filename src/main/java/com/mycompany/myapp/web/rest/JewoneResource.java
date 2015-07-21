package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Jewone;
import com.mycompany.myapp.repository.JewoneRepository;
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
 * REST controller for managing Jewone.
 */
@RestController
@RequestMapping("/api")
public class JewoneResource {

    private final Logger log = LoggerFactory.getLogger(JewoneResource.class);

    @Inject
    private JewoneRepository jewoneRepository;

    /**
     * POST  /jewones -> Create a new jewone.
     */
    @RequestMapping(value = "/jewones",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Jewone jewone) throws URISyntaxException {
        log.debug("REST request to save Jewone : {}", jewone);
        if (jewone.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new jewone cannot already have an ID").build();
        }
        jewoneRepository.save(jewone);
        return ResponseEntity.created(new URI("/api/jewones/" + jewone.getId())).build();
    }

    /**
     * PUT  /jewones -> Updates an existing jewone.
     */
    @RequestMapping(value = "/jewones",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Jewone jewone) throws URISyntaxException {
        log.debug("REST request to update Jewone : {}", jewone);
        if (jewone.getId() == null) {
            return create(jewone);
        }
        jewoneRepository.save(jewone);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /jewones -> get all the jewones.
     */
    @RequestMapping(value = "/jewones",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Jewone>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Jewone> page = jewoneRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jewones", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /jewones/:id -> get the "id" jewone.
     */
    @RequestMapping(value = "/jewones/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Jewone> get(@PathVariable Long id) {
        log.debug("REST request to get Jewone : {}", id);
        return Optional.ofNullable(jewoneRepository.findOne(id))
            .map(jewone -> new ResponseEntity<>(
                jewone,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /jewones/:id -> delete the "id" jewone.
     */
    @RequestMapping(value = "/jewones/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Jewone : {}", id);
        jewoneRepository.delete(id);
    }
}
