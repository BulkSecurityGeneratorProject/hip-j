package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Two;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Two entity.
 */
public interface TwoRepository extends JpaRepository<Two,Long> {

}
