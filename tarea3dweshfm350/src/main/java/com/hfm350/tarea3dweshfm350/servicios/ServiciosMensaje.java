package com.hfm350.tarea3dweshfm350.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfm350.tarea3dweshfm350.modelo.Mensaje;
import com.hfm350.tarea3dweshfm350.repositorios.MensajeRepository;
import com.hfm350.tarea3dweshfm350.repositorios.PlantaRepository;

@Service
public class ServiciosMensaje {

	
	 @Autowired
	    private MensajeRepository mensajeRepo;
}