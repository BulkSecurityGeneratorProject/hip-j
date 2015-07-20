package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Cluster;
import com.mycompany.myapp.repository.ClusterRepository;
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
 * REST controller for managing Cluster.
 */
@RestController
@RequestMapping("/api")
public class ClusterResource {

    private final Logger log = LoggerFactory.getLogger(ClusterResource.class);

    @Inject
    private ClusterRepository clusterRepository;

    /**
     * POST  /clusters -> Create a new cluster.
     */
    @RequestMapping(value = "/clusters",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Cluster cluster) throws URISyntaxException {
        log.debug("REST request to save Cluster : {}", cluster);
        if (cluster.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new cluster cannot already have an ID").build();
        }
        clusterRepository.save(cluster);
        return ResponseEntity.created(new URI("/api/clusters/" + cluster.getId())).build();
    }

    /**
     * PUT  /clusters -> Updates an existing cluster.
     */
    @RequestMapping(value = "/clusters",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Cluster cluster) throws URISyntaxException {
        log.debug("REST request to update Cluster : {}", cluster);
        if (cluster.getId() == null) {
            return create(cluster);
        }
        clusterRepository.save(cluster);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /clusters -> get all the clusters.
     */
    @RequestMapping(value = "/clusters",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Cluster>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Cluster> page = clusterRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/clusters", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /clusters/:id -> get the "id" cluster.
     */
    @RequestMapping(value = "/clusters/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cluster> get(@PathVariable Long id) {
        log.debug("REST request to get Cluster : {}", id);
        return Optional.ofNullable(clusterRepository.findOne(id))
            .map(cluster -> new ResponseEntity<>(
                cluster,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /clusters/:id -> delete the "id" cluster.
     */
    @RequestMapping(value = "/clusters/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Cluster : {}", id);
        clusterRepository.delete(id);
    }
}
