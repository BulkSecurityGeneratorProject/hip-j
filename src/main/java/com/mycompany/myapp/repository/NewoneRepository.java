package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Newone;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Newone entity.
 */
public interface NewoneRepository extends JpaRepository<Newone,Long> {

}
