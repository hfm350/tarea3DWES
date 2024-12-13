package com.hfm350.tarea3dweshfm350.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hfm350.tarea3dweshfm350.modelo.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long>{

	boolean existsByEmail(String email);
	
	
	
	
}
