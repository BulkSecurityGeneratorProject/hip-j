package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Fulfillment_center;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Fulfillment_center entity.
 */
public interface Fulfillment_centerRepository extends JpaRepository<Fulfillment_center,Long> {

}
