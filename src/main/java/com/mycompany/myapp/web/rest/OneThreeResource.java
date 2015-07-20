package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.OneThree;
import com.mycompany.myapp.repository.OneThreeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing OneThree.
 */
@RestController
@RequestMapping("/api")
public class OneThreeResource {

    private final Logger log = LoggerFactory.getLogger(OneThreeResource.class);

    @Inject
    private OneThreeRepository oneThreeRepository;

    /**
     * POST  /oneThrees -> Create a new oneThree.
     */
    @RequestMapping(value = "/oneThrees",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody OneThree oneThree) throws URISyntaxException {
        log.debug("REST request to save OneThree : {}", oneThree);
        if (oneThree.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new oneThree cannot already have an ID").build();
        }
        oneThreeRepository.save(oneThree);
        return ResponseEntity.created(new URI("/api/oneThrees/" + oneThree.getId())).build();
    }

    /**
     * PUT  /oneThrees -> Updates an existing oneThree.
     */
    @RequestMapping(value = "/oneThrees",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody OneThree oneThree) throws URISyntaxException {
        log.debug("REST request to update OneThree : {}", oneThree);
        if (oneThree.getId() == null) {
            return create(oneThree);
        }
        oneThreeRepository.save(oneThree);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /oneThrees -> get all the oneThrees.
     */
    @RequestMapping(value = "/oneThrees",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<OneThree> getAll() {
        log.debug("REST request to get all OneThrees");
        return oneThreeRepository.findAll();
    }

    /**
     * GET  /oneThrees/:id -> get the "id" oneThree.
     */
    @RequestMapping(value = "/oneThrees/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OneThree> get(@PathVariable Long id) {
        log.debug("REST request to get OneThree : {}", id);
        return Optional.ofNullable(oneThreeRepository.findOne(id))
            .map(oneThree -> new ResponseEntity<>(
                oneThree,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /oneThrees/:id -> delete the "id" oneThree.
     */
    @RequestMapping(value = "/oneThrees/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete OneThree : {}", id);
        oneThreeRepository.delete(id);
    }
}
