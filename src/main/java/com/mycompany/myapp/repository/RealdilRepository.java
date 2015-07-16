package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Realdil;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Realdil entity.
 */
public interface RealdilRepository extends JpaRepository<Realdil,Long> {

}
