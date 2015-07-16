package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Noon;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Noon entity.
 */
public interface NoonRepository extends JpaRepository<Noon,Long> {

}
