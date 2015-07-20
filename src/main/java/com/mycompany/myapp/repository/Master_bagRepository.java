package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Master_bag;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Master_bag entity.
 */
public interface Master_bagRepository extends JpaRepository<Master_bag,Long> {

}
