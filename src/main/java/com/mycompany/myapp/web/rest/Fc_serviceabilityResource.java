package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Fc_serviceability;
import com.mycompany.myapp.repository.Fc_serviceabilityRepository;
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
 * REST controller for managing Fc_serviceability.
 */
@RestController
@RequestMapping("/api")
public class Fc_serviceabilityResource {

    private final Logger log = LoggerFactory.getLogger(Fc_serviceabilityResource.class);

    @Inject
    private Fc_serviceabilityRepository fc_serviceabilityRepository;

    /**
     * POST  /fc_serviceabilitys -> Create a new fc_serviceability.
     */
    @RequestMapping(value = "/fc_serviceabilitys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Fc_serviceability fc_serviceability) throws URISyntaxException {
        log.debug("REST request to save Fc_serviceability : {}", fc_serviceability);
        if (fc_serviceability.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new fc_serviceability cannot already have an ID").build();
        }
        fc_serviceabilityRepository.save(fc_serviceability);
        return ResponseEntity.created(new URI("/api/fc_serviceabilitys/" + fc_serviceability.getId())).build();
    }

    /**
     * PUT  /fc_serviceabilitys -> Updates an existing fc_serviceability.
     */
    @RequestMapping(value = "/fc_serviceabilitys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Fc_serviceability fc_serviceability) throws URISyntaxException {
        log.debug("REST request to update Fc_serviceability : {}", fc_serviceability);
        if (fc_serviceability.getId() == null) {
            return create(fc_serviceability);
        }
        fc_serviceabilityRepository.save(fc_serviceability);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /fc_serviceabilitys -> get all the fc_serviceabilitys.
     */
    @RequestMapping(value = "/fc_serviceabilitys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Fc_serviceability>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Fc_serviceability> page = fc_serviceabilityRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fc_serviceabilitys", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /fc_serviceabilitys/:id -> get the "id" fc_serviceability.
     */
    @RequestMapping(value = "/fc_serviceabilitys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Fc_serviceability> get(@PathVariable Long id) {
        log.debug("REST request to get Fc_serviceability : {}", id);
        return Optional.ofNullable(fc_serviceabilityRepository.findOne(id))
            .map(fc_serviceability -> new ResponseEntity<>(
                fc_serviceability,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /fc_serviceabilitys/:id -> delete the "id" fc_serviceability.
     */
    @RequestMapping(value = "/fc_serviceabilitys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Fc_serviceability : {}", id);
        fc_serviceabilityRepository.delete(id);
    }
}
