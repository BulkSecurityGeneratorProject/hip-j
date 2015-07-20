package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Courier;
import com.mycompany.myapp.repository.CourierRepository;
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
 * REST controller for managing Courier.
 */
@RestController
@RequestMapping("/api")
public class CourierResource {

    private final Logger log = LoggerFactory.getLogger(CourierResource.class);

    @Inject
    private CourierRepository courierRepository;

    /**
     * POST  /couriers -> Create a new courier.
     */
    @RequestMapping(value = "/couriers",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Courier courier) throws URISyntaxException {
        log.debug("REST request to save Courier : {}", courier);
        if (courier.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new courier cannot already have an ID").build();
        }
        courierRepository.save(courier);
        return ResponseEntity.created(new URI("/api/couriers/" + courier.getId())).build();
    }

    /**
     * PUT  /couriers -> Updates an existing courier.
     */
    @RequestMapping(value = "/couriers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Courier courier) throws URISyntaxException {
        log.debug("REST request to update Courier : {}", courier);
        if (courier.getId() == null) {
            return create(courier);
        }
        courierRepository.save(courier);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /couriers -> get all the couriers.
     */
    @RequestMapping(value = "/couriers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Courier>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Courier> page = courierRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/couriers", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /couriers/:id -> get the "id" courier.
     */
    @RequestMapping(value = "/couriers/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Courier> get(@PathVariable Long id) {
        log.debug("REST request to get Courier : {}", id);
        return Optional.ofNullable(courierRepository.findOne(id))
            .map(courier -> new ResponseEntity<>(
                courier,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /couriers/:id -> delete the "id" courier.
     */
    @RequestMapping(value = "/couriers/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Courier : {}", id);
        courierRepository.delete(id);
    }
}
