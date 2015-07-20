package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Payment_type;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Payment_type entity.
 */
public interface Payment_typeRepository extends JpaRepository<Payment_type,Long> {

}
