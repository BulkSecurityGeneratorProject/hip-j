package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.City;
import com.mycompany.myapp.repository.CityRepository;
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
 * REST controller for managing City.
 */
@RestController
@RequestMapping("/api")
public class CityResource {

    private final Logger log = LoggerFactory.getLogger(CityResource.class);

    @Inject
    private CityRepository cityRepository;

    /**
     * POST  /citys -> Create a new city.
     */
    @RequestMapping(value = "/citys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody City city) throws URISyntaxException {
        log.debug("REST request to save City : {}", city);
        if (city.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new city cannot already have an ID").build();
        }
        cityRepository.save(city);
        return ResponseEntity.created(new URI("/api/citys/" + city.getId())).build();
    }

    /**
     * PUT  /citys -> Updates an existing city.
     */
    @RequestMapping(value = "/citys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody City city) throws URISyntaxException {
        log.debug("REST request to update City : {}", city);
        if (city.getId() == null) {
            return create(city);
        }
        cityRepository.save(city);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /citys -> get all the citys.
     */
    @RequestMapping(value = "/citys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<City>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<City> page = cityRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/citys", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /citys/:id -> get the "id" city.
     */
    @RequestMapping(value = "/citys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<City> get(@PathVariable Long id) {
        log.debug("REST request to get City : {}", id);
        return Optional.ofNullable(cityRepository.findOne(id))
            .map(city -> new ResponseEntity<>(
                city,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /citys/:id -> delete the "id" city.
     */
    @RequestMapping(value = "/citys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete City : {}", id);
        cityRepository.delete(id);
    }
}
