package com.hfm350.tarea3dweshfm350.fachada;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.hfm350.tarea3dweshfm350.servicios.ServiciosEjemplar;
import com.hfm350.tarea3dweshfm350.servicios.ServiciosMensaje;
import com.hfm350.tarea3dweshfm350.servicios.ServiciosPersona;
import com.hfm350.tarea3dweshfm350.servicios.ServiciosPlanta;

@Component
public class FachadaPersonal {


    @Autowired
    private ServiciosEjemplar serviciosEjemplar;

    @Autowired
    private ServiciosMensaje serviciosMensaje;

    @Autowired
    private ServiciosPersona serviciosPersona;

    @Autowired
    private ServiciosPlanta serviciosPlanta;

    @Autowired
    private FachadaAdmin fachadaAdmin;

    @Autowired
    @Lazy
    private FachadaInvitado vistaInvitado;
    	
    
    public void menuPersonal() {
    	Scanner sc = new Scanner (System.in);
        int seleccion = 0;
        do {
            System.out.println("\n\t\t MENÚ DEL PERSONAL");
            System.out.println("\t\t1. Listar todas las plantas.");
            System.out.println("\t\t2.  Administrar ejemplares.");
            System.out.println("\t\t3. Administrar mensajes.");
            System.out.println("\t\t4. EXIT");
            

            try {
                seleccion = sc.nextInt();
                if (seleccion < 1 || seleccion > 4) {
                    continue;
                }

                switch (seleccion) {
                    case 1 -> {
                        System.out.println("Cargando todas las plantas...");
                        vistaInvitado.mostrarTodasLasPlantas();
                    }
                    case 2 -> {
                        System.out.println("Accediendo a la gestión de ejemplares...");
                        menuPersonalEjemplares();
                    }
                    case 3 -> {
                        System.out.println("Accediendo a la gestión de mensajes...");
                        menuPersonalMensajes();
                    }
                    case 9 -> {
                        FachadaInvitado vistaInvitado;
                    }
                }
            } catch (InputMismatchException ex) {
                System.out.println("Entrada inválida. Por favor, ingresa un número entre 1 y 4.");
                sc.nextLine();
                seleccion = -1;
            }
        } while (seleccion != 4);
    }


	private void menuPersonalEjemplares() {
		
		
	}


	private void menuPersonalMensajes() {
		
		
	}

    }
  
