package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Courier_preferences;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Courier_preferences entity.
 */
public interface Courier_preferencesRepository extends JpaRepository<Courier_preferences,Long> {

}
