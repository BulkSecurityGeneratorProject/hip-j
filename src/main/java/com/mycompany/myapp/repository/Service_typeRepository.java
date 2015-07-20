package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Service_type;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Service_type entity.
 */
public interface Service_typeRepository extends JpaRepository<Service_type,Long> {

}
