package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Mewone;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Mewone entity.
 */
public interface MewoneRepository extends JpaRepository<Mewone,Long> {

}
