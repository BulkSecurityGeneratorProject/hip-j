package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Courier_country_serviceability;
import com.mycompany.myapp.repository.Courier_country_serviceabilityRepository;
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
 * REST controller for managing Courier_country_serviceability.
 */
@RestController
@RequestMapping("/api")
public class Courier_country_serviceabilityResource {

    private final Logger log = LoggerFactory.getLogger(Courier_country_serviceabilityResource.class);

    @Inject
    private Courier_country_serviceabilityRepository courier_country_serviceabilityRepository;

    /**
     * POST  /courier_country_serviceabilitys -> Create a new courier_country_serviceability.
     */
    @RequestMapping(value = "/courier_country_serviceabilitys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Courier_country_serviceability courier_country_serviceability) throws URISyntaxException {
        log.debug("REST request to save Courier_country_serviceability : {}", courier_country_serviceability);
        if (courier_country_serviceability.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new courier_country_serviceability cannot already have an ID").build();
        }
        courier_country_serviceabilityRepository.save(courier_country_serviceability);
        return ResponseEntity.created(new URI("/api/courier_country_serviceabilitys/" + courier_country_serviceability.getId())).build();
    }

    /**
     * PUT  /courier_country_serviceabilitys -> Updates an existing courier_country_serviceability.
     */
    @RequestMapping(value = "/courier_country_serviceabilitys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Courier_country_serviceability courier_country_serviceability) throws URISyntaxException {
        log.debug("REST request to update Courier_country_serviceability : {}", courier_country_serviceability);
        if (courier_country_serviceability.getId() == null) {
            return create(courier_country_serviceability);
        }
        courier_country_serviceabilityRepository.save(courier_country_serviceability);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /courier_country_serviceabilitys -> get all the courier_country_serviceabilitys.
     */
    @RequestMapping(value = "/courier_country_serviceabilitys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Courier_country_serviceability>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Courier_country_serviceability> page = courier_country_serviceabilityRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/courier_country_serviceabilitys", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /courier_country_serviceabilitys/:id -> get the "id" courier_country_serviceability.
     */
    @RequestMapping(value = "/courier_country_serviceabilitys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Courier_country_serviceability> get(@PathVariable Long id) {
        log.debug("REST request to get Courier_country_serviceability : {}", id);
        return Optional.ofNullable(courier_country_serviceabilityRepository.findOne(id))
            .map(courier_country_serviceability -> new ResponseEntity<>(
                courier_country_serviceability,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /courier_country_serviceabilitys/:id -> delete the "id" courier_country_serviceability.
     */
    @RequestMapping(value = "/courier_country_serviceabilitys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Courier_country_serviceability : {}", id);
        courier_country_serviceabilityRepository.delete(id);
    }
}
