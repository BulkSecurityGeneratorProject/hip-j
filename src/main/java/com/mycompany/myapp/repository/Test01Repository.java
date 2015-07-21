package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Test01;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Test01 entity.
 */
public interface Test01Repository extends JpaRepository<Test01,Long> {

}
