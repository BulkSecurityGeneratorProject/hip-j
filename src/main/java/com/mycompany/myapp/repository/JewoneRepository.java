package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Jewone;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Jewone entity.
 */
public interface JewoneRepository extends JpaRepository<Jewone,Long> {

}
