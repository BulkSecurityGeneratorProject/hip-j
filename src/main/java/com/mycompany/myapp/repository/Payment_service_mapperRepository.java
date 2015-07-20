package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Payment_service_mapper;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Payment_service_mapper entity.
 */
public interface Payment_service_mapperRepository extends JpaRepository<Payment_service_mapper,Long> {

}
