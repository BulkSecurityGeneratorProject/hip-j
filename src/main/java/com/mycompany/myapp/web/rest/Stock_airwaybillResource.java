package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Stock_airwaybill;
import com.mycompany.myapp.repository.Stock_airwaybillRepository;
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
 * REST controller for managing Stock_airwaybill.
 */
@RestController
@RequestMapping("/api")
public class Stock_airwaybillResource {

    private final Logger log = LoggerFactory.getLogger(Stock_airwaybillResource.class);

    @Inject
    private Stock_airwaybillRepository stock_airwaybillRepository;

    /**
     * POST  /stock_airwaybills -> Create a new stock_airwaybill.
     */
    @RequestMapping(value = "/stock_airwaybills",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Stock_airwaybill stock_airwaybill) throws URISyntaxException {
        log.debug("REST request to save Stock_airwaybill : {}", stock_airwaybill);
        if (stock_airwaybill.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new stock_airwaybill cannot already have an ID").build();
        }
        stock_airwaybillRepository.save(stock_airwaybill);
        return ResponseEntity.created(new URI("/api/stock_airwaybills/" + stock_airwaybill.getId())).build();
    }

    /**
     * PUT  /stock_airwaybills -> Updates an existing stock_airwaybill.
     */
    @RequestMapping(value = "/stock_airwaybills",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Stock_airwaybill stock_airwaybill) throws URISyntaxException {
        log.debug("REST request to update Stock_airwaybill : {}", stock_airwaybill);
        if (stock_airwaybill.getId() == null) {
            return create(stock_airwaybill);
        }
        stock_airwaybillRepository.save(stock_airwaybill);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /stock_airwaybills -> get all the stock_airwaybills.
     */
    @RequestMapping(value = "/stock_airwaybills",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Stock_airwaybill>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Stock_airwaybill> page = stock_airwaybillRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/stock_airwaybills", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /stock_airwaybills/:id -> get the "id" stock_airwaybill.
     */
    @RequestMapping(value = "/stock_airwaybills/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Stock_airwaybill> get(@PathVariable Long id) {
        log.debug("REST request to get Stock_airwaybill : {}", id);
        return Optional.ofNullable(stock_airwaybillRepository.findOne(id))
            .map(stock_airwaybill -> new ResponseEntity<>(
                stock_airwaybill,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /stock_airwaybills/:id -> delete the "id" stock_airwaybill.
     */
    @RequestMapping(value = "/stock_airwaybills/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Stock_airwaybill : {}", id);
        stock_airwaybillRepository.delete(id);
    }
}
