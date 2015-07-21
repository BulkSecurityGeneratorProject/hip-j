package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Test03;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Test03 entity.
 */
public interface Test03Repository extends JpaRepository<Test03,Long> {

}
