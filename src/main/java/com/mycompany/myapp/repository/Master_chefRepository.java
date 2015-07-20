package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Master_chef;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Master_chef entity.
 */
public interface Master_chefRepository extends JpaRepository<Master_chef,Long> {

}
