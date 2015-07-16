package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Three;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Three entity.
 */
public interface ThreeRepository extends JpaRepository<Three,Long> {

}
