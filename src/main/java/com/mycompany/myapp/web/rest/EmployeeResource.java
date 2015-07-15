package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Employee;
import com.mycompany.myapp.repository.EmployeeRepository;
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
 * REST controller for managing Employee.
 */
@RestController
@RequestMapping("/api")
public class EmployeeResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeResource.class);

    @Inject
    private EmployeeRepository employeeRepository;

    /**
     * POST  /employees -> Create a new employee.
     */
    @RequestMapping(value = "/employees",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Employee employee) throws URISyntaxException {
        log.debug("REST request to save Employee : {}", employee);
        if (employee.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new employee cannot already have an ID").build();
        }
        employeeRepository.save(employee);
        return ResponseEntity.created(new URI("/api/employees/" + employee.getId())).build();
    }

    /**
     * PUT  /employees -> Updates an existing employee.
     */
    @RequestMapping(value = "/employees",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Employee employee) throws URISyntaxException {
        log.debug("REST request to update Employee : {}", employee);
        if (employee.getId() == null) {
            return create(employee);
        }
        employeeRepository.save(employee);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /employees -> get all the employees.
     */
    @RequestMapping(value = "/employees",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Employee> getAll() {
        log.debug("REST request to get all Employees");
        return employeeRepository.findAll();
    }

    /**
     * GET  /employees/:id -> get the "id" employee.
     */
    @RequestMapping(value = "/employees/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Employee> get(@PathVariable Long id) {
        log.debug("REST request to get Employee : {}", id);
        return Optional.ofNullable(employeeRepository.findOne(id))
            .map(employee -> new ResponseEntity<>(
                employee,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /employees/:id -> delete the "id" employee.
     */
    @RequestMapping(value = "/employees/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Employee : {}", id);
        employeeRepository.delete(id);
    }
}
