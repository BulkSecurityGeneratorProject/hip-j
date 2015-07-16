package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Hoohaa;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Hoohaa entity.
 */
public interface HoohaaRepository extends JpaRepository<Hoohaa,Long> {

}
