package com.hfm350.tarea3dweshfm350.controller;

import java.awt.print.PrinterAbortException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hfm350.tarea3dweshfm350.modelo.Planta;
import com.hfm350.tarea3dweshfm350.repositorios.CredencialRepository;
import com.hfm350.tarea3dweshfm350.repositorios.PlantaRepository;
import com.hfm350.tarea3dweshfm350.servicios.ServiciosCredenciales;


@Controller
public class HomeController {
	
	@Autowired
	private PlantaRepository plantaRepo;
	
	@Autowired
	private ServiciosCredenciales serviciosCredenciales;
	
	
	@GetMapping("/")
	public String index(Model model) {
		List <Planta> listaPlantas = plantaRepo.findAll();
		model.addAttribute("plantas", listaPlantas);
		return "index";
	}

	@GetMapping("/inicioSesion")
	public String inicioSesion() {
		return "inicioSesion";
	}
	
	@GetMapping("/menuAdmin")
	public String menuAdmin() {
	    return "menuAdmin"; 
	}

	
	@PostMapping("/inicioSesion")
	public String iniciarSesion(@RequestParam String nombreUsuario, @RequestParam String password, Model model) {
	    System.out.println("nombreUsuario recibido: " + nombreUsuario);
	    System.out.println("password recibido: " + password);
	    
	    if ("ADMIN".equals(nombreUsuario) && "admin".equals(password)) {
	        model.addAttribute("credenciales", serviciosCredenciales.autenticar(nombreUsuario, password));
	        return "redirect:/menuAdmin";
	    }
	    model.addAttribute("error", "Usuario o contraseña incorrectos");
	    return "inicioSesion"; 
	}
	
	

	



	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	

}
