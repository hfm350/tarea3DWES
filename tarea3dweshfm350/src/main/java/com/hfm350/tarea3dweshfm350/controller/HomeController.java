package com.hfm350.tarea3dweshfm350.controller;

import java.util.List;

import org.hibernate.query.NativeQuery.ReturnableResultNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hfm350.tarea3dweshfm350.modelo.Planta;
import com.hfm350.tarea3dweshfm350.modelo.Sesion;
import com.hfm350.tarea3dweshfm350.modelo.Sesion.Perfil;
import com.hfm350.tarea3dweshfm350.repositorios.PlantaRepository;
import com.hfm350.tarea3dweshfm350.servicios.ServiciosCredenciales;

@Controller
public class HomeController {

	@Autowired
	private PlantaRepository plantaRepo;

	@Autowired
	private ServiciosCredenciales serviciosCredenciales;

	private Sesion sesion = new Sesion(Perfil.INVITADO, "");

	
	@GetMapping("/")
	public String index(Model model) {
		List<Planta> listaPlantas = plantaRepo.findAll();
		model.addAttribute("plantas", listaPlantas);
		model.addAttribute("perfil", sesion.getPerfil());
		return "index";
	}

	@GetMapping("/inicioSesion")
	public String inicioSesion() {
		return "inicioSesion";
	}

	@GetMapping("/menuAdmin")
	public String menuAdmin(Model model) {
		if (sesion.getPerfil() != Perfil.ADMIN) {
			return "redirect:/inicioSesion";
		}
		model.addAttribute("perfil", sesion.getPerfil());
		model.addAttribute("nombreUsuario", sesion.getNombreUsuario());

		return "menuAdmin";
	}

	@GetMapping("/verPlantas")
	public String verPlantas(Model model) {
		List<Planta> listaPlantas = plantaRepo.findAll();
		model.addAttribute("plantas", listaPlantas);
		model.addAttribute("perfil", sesion.getPerfil());
		return "verPlantas";
		
	}

	@GetMapping("/menuPersonal")
	public String menuPersonal(Model model) {
		if (sesion.getPerfil() != Perfil.PERSONAL) {
			return "redirect:/inicioSesion";
		}
		model.addAttribute("perfil", sesion.getPerfil());
		model.addAttribute("nombreUsuario", sesion.getNombreUsuario());
		return "menuPersonal";
	}
	
	
	
	
	
	@GetMapping("/gestionPlantas")
	public String gestionPlantas(Model model) {
		model.addAttribute("perfil", sesion.getPerfil());
		
		return "gestionPlantas";
	}

	@PostMapping("/inicioSesion")
	public String iniciarSesion(@RequestParam String nombreUsuario, @RequestParam String password, Model model) {

		if (serviciosCredenciales.autenticar(nombreUsuario, password)) {

			sesion.setNombreUsuario(nombreUsuario);
			if ("admin".equalsIgnoreCase(nombreUsuario)) {
				sesion.setPerfil(Perfil.ADMIN);
				return "redirect:/menuAdmin";
			}

			else {
				sesion.setPerfil(Perfil.PERSONAL);
				return "redirect:/menuPersonal";
			}
			
			
		} else {

			System.out.println("Usuario o contraseña incorrectos.");
		}

		model.addAttribute("error", "Usuario o contraseña incorrectos");
		return "inicioSesion";
	}
	

	
	
	@GetMapping("/cerrarSesion")
	public String cerrarSesion() {
		sesion.setPerfil(Perfil.INVITADO);
		sesion.setNombreUsuario("");
		return "redirect:/";
	}
}
