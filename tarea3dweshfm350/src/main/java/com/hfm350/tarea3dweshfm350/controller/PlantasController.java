package com.hfm350.tarea3dweshfm350.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.hfm350.tarea3dweshfm350.modelo.Sesion;
import com.hfm350.tarea3dweshfm350.modelo.Sesion.Perfil;
import com.hfm350.tarea3dweshfm350.repositorios.PlantaRepository;

@Controller
public class PlantasController {

	@Autowired
	private PlantaRepository plantaRepo;

	@Autowired 
	private HomeController homeController;
	
	private Sesion sesion;

	@GetMapping("/registrarPlantas")
	public String registrarPlantas(Model model) {
	    model.addAttribute("perfil", sesion.getPerfil());
	    return "registrarPlantas";
	}


	@PostMapping("/registrarPlantas")
	public String registrarPlanta() {

		return "registrarPlanta";
	}
}
