package com.hfm350.tarea3dweshfm350.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hfm350.tarea3dweshfm350.modelo.Ejemplar;



@Repository
public interface EjemplarRepository extends JpaRepository<Ejemplar, Long>{
	
}
