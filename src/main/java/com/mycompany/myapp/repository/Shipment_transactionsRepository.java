package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Shipment_transactions;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Shipment_transactions entity.
 */
public interface Shipment_transactionsRepository extends JpaRepository<Shipment_transactions,Long> {

}
