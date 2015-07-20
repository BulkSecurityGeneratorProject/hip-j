package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.OneThree;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OneThree entity.
 */
public interface OneThreeRepository extends JpaRepository<OneThree,Long> {

}
