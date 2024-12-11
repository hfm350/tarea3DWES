package com.hfm350.tarea3dweshfm350.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfm350.tarea3dweshfm350.modelo.Ejemplar;
import com.hfm350.tarea3dweshfm350.modelo.Planta;
import com.hfm350.tarea3dweshfm350.repositorios.EjemplarRepository;


@Service
public class ServiciosEjemplar {

	@Autowired
    private EjemplarRepository ejemplarRepo;
	
	public void insertar(Ejemplar ej) {
        ejemplarRepo.saveAndFlush(ej);
    }
}
