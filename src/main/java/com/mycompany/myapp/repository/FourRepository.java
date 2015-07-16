package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Four;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Four entity.
 */
public interface FourRepository extends JpaRepository<Four,Long> {

}
