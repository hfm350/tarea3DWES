package com.hfm350.tarea3dweshfm350.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hfm350.tarea3dweshfm350.modelo.*;

@Repository
public interface PlantaRepository extends JpaRepository<Planta, Long> {

	@Query("SELECT p FROM Planta p WHERE p.id = :id")
	Planta findByID(@Param("id") Long id);

}
