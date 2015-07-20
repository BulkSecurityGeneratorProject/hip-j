package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Courier;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Courier entity.
 */
public interface CourierRepository extends JpaRepository<Courier,Long> {

}
