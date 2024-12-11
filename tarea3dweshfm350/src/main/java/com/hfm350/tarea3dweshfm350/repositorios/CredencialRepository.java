package com.hfm350.tarea3dweshfm350.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hfm350.tarea3dweshfm350.modelo.Credencial;

@Repository
public interface CredencialRepository extends JpaRepository<Credencial, Long> {

	Optional<Credencial> findByUsuario(String nombreUsuario);
	

	@Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END FROM Credencial c WHERE c.usuario = :usuario")
	    boolean existeUsuario(String usuario);
	
}
