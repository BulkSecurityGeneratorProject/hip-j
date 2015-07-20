package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Stock_airwaybill;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Stock_airwaybill entity.
 */
public interface Stock_airwaybillRepository extends JpaRepository<Stock_airwaybill,Long> {

}
