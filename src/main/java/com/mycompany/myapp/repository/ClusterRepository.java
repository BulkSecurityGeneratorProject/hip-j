package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Cluster;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Cluster entity.
 */
public interface ClusterRepository extends JpaRepository<Cluster,Long> {

}
