package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Five;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Five entity.
 */
public interface FiveRepository extends JpaRepository<Five,Long> {

}
