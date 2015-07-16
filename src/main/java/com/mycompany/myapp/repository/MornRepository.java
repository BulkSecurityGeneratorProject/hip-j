package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Morn;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Morn entity.
 */
public interface MornRepository extends JpaRepository<Morn,Long> {

}
