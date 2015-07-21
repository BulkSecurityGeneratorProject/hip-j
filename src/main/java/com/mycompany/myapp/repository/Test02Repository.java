package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Test02;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Test02 entity.
 */
public interface Test02Repository extends JpaRepository<Test02,Long> {

}
