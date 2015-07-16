package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.One;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the One entity.
 */
public interface OneRepository extends JpaRepository<One,Long> {

}
