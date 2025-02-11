package com.hfm350.tarea3dweshfm350.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.query.NativeQuery.ReturnableResultNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hfm350.tarea3dweshfm350.modelo.Controlador;
import com.hfm350.tarea3dweshfm350.modelo.Credencial;
import com.hfm350.tarea3dweshfm350.modelo.Ejemplar;
import com.hfm350.tarea3dweshfm350.modelo.Mensaje;
import com.hfm350.tarea3dweshfm350.modelo.Persona;
import com.hfm350.tarea3dweshfm350.modelo.Planta;
import com.hfm350.tarea3dweshfm350.modelo.Sesion;
import com.hfm350.tarea3dweshfm350.modelo.Sesion.Perfil;
import com.hfm350.tarea3dweshfm350.repositorios.PlantaRepository;
import com.hfm350.tarea3dweshfm350.servicios.ServiciosCredenciales;
import com.hfm350.tarea3dweshfm350.servicios.ServiciosEjemplar;
import com.hfm350.tarea3dweshfm350.servicios.ServiciosMensaje;
import com.hfm350.tarea3dweshfm350.servicios.ServiciosPersona;
import com.hfm350.tarea3dweshfm350.servicios.ServiciosPlanta;

@Controller
public class HomeController {

	@Autowired
	private ServiciosEjemplar serviciosEjemplar;

	@Autowired
	private ServiciosPlanta serviciosPlanta;

	@Autowired
	private ServiciosPersona serviciosPersona;

	@Autowired
	private ServiciosMensaje serviciosMensaje;
	@Autowired
	private PlantaRepository plantaRepo;

	@Autowired
	private ServiciosCredenciales serviciosCredenciales;

	@Autowired
	private Controlador controlador;

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

	@GetMapping("/gestionEjemplares")
	public String gestionEjemplar(Model model) {
		model.addAttribute("perfil", sesion.getPerfil());
		return "/gestionEjemplares";
	}

	@GetMapping("/insertarEjemplar")
	public String insertarEjemplar(Model model) {
		List<Ejemplar> ejemplares = serviciosEjemplar.findAll();
		model.addAttribute("ejemplares", ejemplares);
		List<Planta> plantas = serviciosPlanta.findAll();
		if (plantas.isEmpty()) {
			model.addAttribute("errorMessagelistavacia", "No hay plantas");
		}

		List<Planta> plantaSinEjemplar = new ArrayList<>();
		for (Planta planta : plantas) {
			if (!serviciosEjemplar.existsByPlanta(planta)) {
				plantaSinEjemplar.add(planta);
			}
		}

		if (plantaSinEjemplar.isEmpty()) {
			model.addAttribute("errorMessagelistavacia", "No hay plantas sin ejemplar");
		} else {
			model.addAttribute("plantasSinEjemplar", plantaSinEjemplar);
		}
		return "/insertarEjemplar";
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

			Optional<Credencial> idUsuario = serviciosCredenciales.buscarPersonaPorId(nombreUsuario);
			if (idUsuario != null) {
				Credencial credencial = idUsuario.get();
				controlador.setUsuarioAutenticado(credencial.getId());
			}

			if ("admin".equalsIgnoreCase(nombreUsuario)) {
				sesion.setPerfil(Perfil.ADMIN);
				return "redirect:/menuAdmin";
			} else {
				sesion.setPerfil(Perfil.PERSONAL);
				return "redirect:/menuPersonal";
			}
		} else {
			model.addAttribute("error", "Usuario o contraseña incorrectos");
			return "inicioSesion";
		}
	}

	@PostMapping("/insertarEjemplar")
	public String insertarEjemplar(@RequestParam String codigoPlanta, @RequestParam String nombreEjemplar,
			@RequestParam String mensaje, Model model) {

		Planta planta = serviciosPlanta.buscarPorCodigo(codigoPlanta);

		if (planta == null) {
			model.addAttribute("errorMessage", "El código de la planta no es válido.");
			return "insertarEjemplar";
		}

		Ejemplar ejemplar = serviciosEjemplar.insertar(nombreEjemplar, codigoPlanta);

		if (ejemplar == null) {
			model.addAttribute("errorMessage", "Error al insertar el ejemplar.");
			return "insertarEjemplar";
		}

		Long usuarioIdLong = controlador.getUsuarioAutenticado();
		if (usuarioIdLong == null) {
			model.addAttribute("errorMessagePersona", "Usuario no autenticado.");
			return "insertarEjemplar";
		}

		Optional<Long> personaID = serviciosCredenciales.obtenerIdPersonaPorIdCredencial(usuarioIdLong);
		if (!personaID.isPresent()) {
			model.addAttribute("errorMessagePersona", "No se encuentra la persona asociada.");
			return "insertarEjemplar";
		}
		

		Optional<Persona> personaOptional = serviciosPersona.buscarPorId(personaID.get());
		if (!personaOptional.isPresent()) {
			model.addAttribute("errorUsuario", "No se encuentra la persona con el ID obtenido");
			return "insertarEjemplar";
		}

		Persona persona = personaOptional.get();
		LocalDateTime tiempo = LocalDateTime.now();
		Mensaje nuevoMensaje = new Mensaje(tiempo, mensaje, persona, ejemplar);

		serviciosMensaje.insertar(nuevoMensaje);

		List<Ejemplar> ejemplares = serviciosEjemplar.findAll();
		model.addAttribute("ejemplares", ejemplares);
		model.addAttribute("successMessage", "Ejemplar y mensaje registrados exitosamente");

		return "redirect:/insertarEjemplar";
	}

	@GetMapping("/cerrarSesion")
	public String cerrarSesion() {
		sesion.setPerfil(Perfil.INVITADO);
		sesion.setNombreUsuario("");
		return "redirect:/";
	}
}
