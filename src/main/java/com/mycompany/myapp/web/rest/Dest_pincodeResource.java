package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Dest_pincode;
import com.mycompany.myapp.repository.Dest_pincodeRepository;
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
 * REST controller for managing Dest_pincode.
 */
@RestController
@RequestMapping("/api")
public class Dest_pincodeResource {

    private final Logger log = LoggerFactory.getLogger(Dest_pincodeResource.class);

    @Inject
    private Dest_pincodeRepository dest_pincodeRepository;

    /**
     * POST  /dest_pincodes -> Create a new dest_pincode.
     */
    @RequestMapping(value = "/dest_pincodes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Dest_pincode dest_pincode) throws URISyntaxException {
        log.debug("REST request to save Dest_pincode : {}", dest_pincode);
        if (dest_pincode.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new dest_pincode cannot already have an ID").build();
        }
        dest_pincodeRepository.save(dest_pincode);
        return ResponseEntity.created(new URI("/api/dest_pincodes/" + dest_pincode.getId())).build();
    }

    /**
     * PUT  /dest_pincodes -> Updates an existing dest_pincode.
     */
    @RequestMapping(value = "/dest_pincodes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Dest_pincode dest_pincode) throws URISyntaxException {
        log.debug("REST request to update Dest_pincode : {}", dest_pincode);
        if (dest_pincode.getId() == null) {
            return create(dest_pincode);
        }
        dest_pincodeRepository.save(dest_pincode);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /dest_pincodes -> get all the dest_pincodes.
     */
    @RequestMapping(value = "/dest_pincodes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Dest_pincode>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Dest_pincode> page = dest_pincodeRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dest_pincodes", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dest_pincodes/:id -> get the "id" dest_pincode.
     */
    @RequestMapping(value = "/dest_pincodes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Dest_pincode> get(@PathVariable Long id) {
        log.debug("REST request to get Dest_pincode : {}", id);
        return Optional.ofNullable(dest_pincodeRepository.findOne(id))
            .map(dest_pincode -> new ResponseEntity<>(
                dest_pincode,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /dest_pincodes/:id -> delete the "id" dest_pincode.
     */
    @RequestMapping(value = "/dest_pincodes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Dest_pincode : {}", id);
        dest_pincodeRepository.delete(id);
    }
}
