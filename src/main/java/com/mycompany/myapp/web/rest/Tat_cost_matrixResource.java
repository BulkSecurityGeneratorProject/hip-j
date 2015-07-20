package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Tat_cost_matrix;
import com.mycompany.myapp.repository.Tat_cost_matrixRepository;
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
 * REST controller for managing Tat_cost_matrix.
 */
@RestController
@RequestMapping("/api")
public class Tat_cost_matrixResource {

    private final Logger log = LoggerFactory.getLogger(Tat_cost_matrixResource.class);

    @Inject
    private Tat_cost_matrixRepository tat_cost_matrixRepository;

    /**
     * POST  /tat_cost_matrixs -> Create a new tat_cost_matrix.
     */
    @RequestMapping(value = "/tat_cost_matrixs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Tat_cost_matrix tat_cost_matrix) throws URISyntaxException {
        log.debug("REST request to save Tat_cost_matrix : {}", tat_cost_matrix);
        if (tat_cost_matrix.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new tat_cost_matrix cannot already have an ID").build();
        }
        tat_cost_matrixRepository.save(tat_cost_matrix);
        return ResponseEntity.created(new URI("/api/tat_cost_matrixs/" + tat_cost_matrix.getId())).build();
    }

    /**
     * PUT  /tat_cost_matrixs -> Updates an existing tat_cost_matrix.
     */
    @RequestMapping(value = "/tat_cost_matrixs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Tat_cost_matrix tat_cost_matrix) throws URISyntaxException {
        log.debug("REST request to update Tat_cost_matrix : {}", tat_cost_matrix);
        if (tat_cost_matrix.getId() == null) {
            return create(tat_cost_matrix);
        }
        tat_cost_matrixRepository.save(tat_cost_matrix);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /tat_cost_matrixs -> get all the tat_cost_matrixs.
     */
    @RequestMapping(value = "/tat_cost_matrixs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Tat_cost_matrix>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Tat_cost_matrix> page = tat_cost_matrixRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tat_cost_matrixs", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tat_cost_matrixs/:id -> get the "id" tat_cost_matrix.
     */
    @RequestMapping(value = "/tat_cost_matrixs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tat_cost_matrix> get(@PathVariable Long id) {
        log.debug("REST request to get Tat_cost_matrix : {}", id);
        return Optional.ofNullable(tat_cost_matrixRepository.findOne(id))
            .map(tat_cost_matrix -> new ResponseEntity<>(
                tat_cost_matrix,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tat_cost_matrixs/:id -> delete the "id" tat_cost_matrix.
     */
    @RequestMapping(value = "/tat_cost_matrixs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Tat_cost_matrix : {}", id);
        tat_cost_matrixRepository.delete(id);
    }
}
