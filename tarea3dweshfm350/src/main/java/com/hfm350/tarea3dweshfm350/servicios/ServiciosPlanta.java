package com.hfm350.tarea3dweshfm350.servicios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfm350.tarea3dweshfm350.modelo.Planta;
import com.hfm350.tarea3dweshfm350.repositorios.EjemplarRepository;
import com.hfm350.tarea3dweshfm350.repositorios.PlantaRepository;

@Service
public class ServiciosPlanta {

	@Autowired
	PlantaRepository plantarepo;

	@Autowired
	private PlantaRepository plantaRepo;

	public Planta findByID(long id) {
		Optional<Planta> plantas = plantaRepo.findById(id);
		return plantas.orElse(null);
	}

}
