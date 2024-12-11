package com.hfm350.tarea3dweshfm350.fachada;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

import com.hfm350.tarea3dweshfm350.fachada.*;
import com.hfm350.tarea3dweshfm350.modelo.Planta;
import com.hfm350.tarea3dweshfm350.servicios.ServiciosCredenciales;
import com.hfm350.tarea3dweshfm350.servicios.ServiciosPlanta;

@Component
public class FachadaInvitado {

	@Autowired
	private ServiciosCredenciales credencialesServicio;

	@Autowired
	private ServiciosPlanta plantasServicio;

	@Autowired
	@Lazy
	private FachadaAdmin adminVista;

	@Autowired
	@Lazy
	private FachadaPersonal personalVista;

	private Scanner entrada = new Scanner(System.in);

	public void mostrarMenuInvitado() {
		int opcionSeleccionada = 0;
		do {
			System.out.println("\n\n\t\t\tBIENVENIDO AL SISTEMA DEL VIVERO");
			System.out.println("\tSeleccione una opción:");
			System.out.println("\t1. Mostrar todas las plantas");
			System.out.println("\t2. Iniciar sesión");
			System.out.println("\t9. Salir del sistema");
			try {
				opcionSeleccionada = entrada.nextInt();
				switch (opcionSeleccionada) {
				case 1 -> mostrarTodasLasPlantas();
				case 2 -> iniciarSesion();
				case 9 -> {
					System.out.println("ADIOS");
					System.exit(0);
				}
				default -> System.out.println("OPCION NO VALIDA");
				}
			} catch (InputMismatchException e) {
				System.out.println("ENTRADA NO VALIDA");
				entrada.nextLine();
				opcionSeleccionada = 0;
			}
		} while (opcionSeleccionada != 9);
	}

	public void iniciarSesion() {
	    entrada.nextLine();
	    System.out.print("Ingrese su nombre de usuario: ");
	    String nombreUsuario = entrada.nextLine().trim().toUpperCase();
	    System.out.print("Ingrese su contraseña: ");
	    String clave = entrada.nextLine().trim().toLowerCase();

	    if (credencialesServicio.autenticar(nombreUsuario, clave)) {
	        System.out.print("\n");
	        if (nombreUsuario.equalsIgnoreCase("ADMIN") && clave.equals("admin")) {
	            System.out.println("\t\tHola ADMIN, bienvenido dispones de todo el control del VIVERO");
	            adminVista.menuAdmin();
	        } else {
	            personalVista.menuPersonal();
	        }
	    } else {
	        System.out.println("Usuario o contraseña incorrectos.");
	    }
	}


	public void mostrarTodasLasPlantas() {
	    ArrayList<Planta> listaDePlantas = (ArrayList<Planta>) plantasServicio.findAll(); 
	    if (listaDePlantas == null || listaDePlantas.isEmpty()) {
	        System.out.println("NO HAY PLANTAS");
	        return;
	    }
	    int contador=0;
	    System.out.println("\t\t\tLISTA DE PLANTAS DISPONIBLES");
	    for (Planta planta : listaDePlantas) {
	    	contador++;
	        System.out.println(contador+"º");
	        System.out.println(planta);
	        System.out.println("-----------------");
	        
	        
	    }
	
	}
}
