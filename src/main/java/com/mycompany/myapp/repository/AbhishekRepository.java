package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Abhishek;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Abhishek entity.
 */
public interface AbhishekRepository extends JpaRepository<Abhishek,Long> {

}
