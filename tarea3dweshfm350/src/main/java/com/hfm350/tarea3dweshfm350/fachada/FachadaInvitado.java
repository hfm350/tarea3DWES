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
import com.hfm350.tarea3dweshfm350.modelo.Sesion;
import com.hfm350.tarea3dweshfm350.modelo.Sesion.Perfil;
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
	
	Sesion s = new Sesion (Perfil.INVITADO);
	boolean sesion = true;
	
	public void mostrarMenuInvitado() {
	    int opcionSeleccionada = 0;
	    boolean sesion = false;
	    do {
	        System.out.println("\n\n\t\t\tMENU INVITADO");
	        System.out.println("\tSeleccione una opción:");
	        System.out.println("\t1. Mostrar todas las plantas");
	        System.out.println("\t2. Iniciar sesión");
	        System.out.println("\t9. Cerrar Sesion");

	        try {
	            opcionSeleccionada = entrada.nextInt();
	            switch (opcionSeleccionada) {
	                case 1:
	                    mostrarTodasLasPlantas();
	                    break;
	                case 2:
	                    iniciarSesion();
	                    sesion = true;
	                    break;
	                case 9:
	                    System.out.println("Cierre Sesion HECHO");
	                    sesion = false;
	                    break;
	                default:
	                    System.out.println("OPCION NO VALIDA");
	            }
	        } catch (InputMismatchException e) {
	            System.out.println("ENTRADA NO VALIDA");
	            entrada.nextLine();
	            opcionSeleccionada = 0;
	        }
	    } while (sesion);
	}


	public void iniciarSesion() {
	    entrada.nextLine();
	    System.out.print("Ingrese su nombre de usuario: ");
	    String nombreUsuario = entrada.nextLine().toUpperCase();
	    System.out.print("Ingrese su contraseña: ");
	    String clave = entrada.nextLine().toLowerCase();

	    if (credencialesServicio.autenticar(nombreUsuario, clave)) {
	        System.out.print("\n");
	        if (nombreUsuario.equalsIgnoreCase("ADMIN") && clave.equals("admin")) {
	        	s.setPerfil(Perfil.ADMIN);
	            System.out.println("\t\tHola ADMIN");
	            adminVista.menuAdmin();
	        } else {
	        	s.setPerfil(Perfil.PERSONAL);
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
	    mostrarMenuInvitado();
	
	}
}
