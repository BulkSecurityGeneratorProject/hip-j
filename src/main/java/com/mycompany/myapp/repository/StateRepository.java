package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.State;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the State entity.
 */
public interface StateRepository extends JpaRepository<State,Long> {

}
