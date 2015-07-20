package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.OneTwo;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OneTwo entity.
 */
public interface OneTwoRepository extends JpaRepository<OneTwo,Long> {

}
