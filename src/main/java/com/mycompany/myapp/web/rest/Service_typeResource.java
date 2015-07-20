package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Service_type;
import com.mycompany.myapp.repository.Service_typeRepository;
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
 * REST controller for managing Service_type.
 */
@RestController
@RequestMapping("/api")
public class Service_typeResource {

    private final Logger log = LoggerFactory.getLogger(Service_typeResource.class);

    @Inject
    private Service_typeRepository service_typeRepository;

    /**
     * POST  /service_types -> Create a new service_type.
     */
    @RequestMapping(value = "/service_types",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Service_type service_type) throws URISyntaxException {
        log.debug("REST request to save Service_type : {}", service_type);
        if (service_type.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new service_type cannot already have an ID").build();
        }
        service_typeRepository.save(service_type);
        return ResponseEntity.created(new URI("/api/service_types/" + service_type.getId())).build();
    }

    /**
     * PUT  /service_types -> Updates an existing service_type.
     */
    @RequestMapping(value = "/service_types",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Service_type service_type) throws URISyntaxException {
        log.debug("REST request to update Service_type : {}", service_type);
        if (service_type.getId() == null) {
            return create(service_type);
        }
        service_typeRepository.save(service_type);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /service_types -> get all the service_types.
     */
    @RequestMapping(value = "/service_types",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Service_type>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Service_type> page = service_typeRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/service_types", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /service_types/:id -> get the "id" service_type.
     */
    @RequestMapping(value = "/service_types/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Service_type> get(@PathVariable Long id) {
        log.debug("REST request to get Service_type : {}", id);
        return Optional.ofNullable(service_typeRepository.findOne(id))
            .map(service_type -> new ResponseEntity<>(
                service_type,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /service_types/:id -> delete the "id" service_type.
     */
    @RequestMapping(value = "/service_types/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Service_type : {}", id);
        service_typeRepository.delete(id);
    }
}
