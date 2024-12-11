package com.hfm350.tarea3dweshfm350.fachada;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.hfm350.tarea3dweshfm350.modelo.Credencial;
import com.hfm350.tarea3dweshfm350.modelo.Persona;
import com.hfm350.tarea3dweshfm350.modelo.Planta;
import com.hfm350.tarea3dweshfm350.servicios.ServiciosCredenciales;
import com.hfm350.tarea3dweshfm350.servicios.ServiciosPersona;
import com.hfm350.tarea3dweshfm350.servicios.ServiciosPlanta;

@Component
public class FachadaAdmin {

    @Autowired
    private ServiciosPlanta servPlanta;

    @Autowired
    private ServiciosPersona servPersona;

    @Autowired
    private ServiciosCredenciales servCredenciales;

    @Autowired
    @Lazy
    private FachadaInvitado vistaFachadaInvitado;

    @Autowired
    @Lazy
    private FachadaPersonal vistaFachadaPersonal;

    Scanner sc = new Scanner(System.in);

    public void menuAdmin() {
        int opcion = 0;

        do {
            System.out.println("\n\t\tMenú Administrador\n");
            System.out.println("\t\t1-  Gestión plantas");
            System.out.println("\t\t2-  Gestión ejemplares");
            System.out.println("\t\t3-  Gestión mensajes");
            System.out.println("\t\t4-  Gestión persona");
            System.out.println("\t\t9-  Cierre de Sesión");

            try {
                opcion = sc.nextInt();
                sc.nextLine();
                if (opcion != 1 && opcion != 2 && opcion != 3 && opcion != 4 && opcion != 9) {
                    System.out.println("Opción invalida. Prueba otra vez. \n");
                    continue;
                }
                switch (opcion) {
                    case 1:
                        gestionPlanta();
                        break;
                    case 2:
                        gestionEjemplar();
                        break;
                    case 3:
                        gestionMensaje();
                        break;
                    case 4:
                        gestionPersona();
                        break;
                    case 9:
                        vistaFachadaInvitado.mostrarMenuInvitado();
                        break;
                }
            } catch (InputMismatchException e) {
                System.err.println("ERROR, porfavor ingrese un numero ENTERO");
                sc.next();
            }
        } while (opcion != 9);
    }

    private void gestionPlanta() {
        System.out.println("\n\t\tMenu-Gestión-Ejemplar\n");
        int opcion = 0;

        do {
            System.out.println("\t\t1-  Insertar PLANTA");
            System.out.println("\t\t2-  Modificar PLANTA-NombreComun");
            System.out.println("\t\t3-  Modificar PLANTA-NombreCientifico");
            System.out.println("\t\t9-  Volver al menú ADMIN");

            try {
                opcion = sc.nextInt();
                sc.nextLine();
                if (opcion != 1 && opcion != 2 && opcion != 3 && opcion != 9) {
                    System.out.println("Opción invalida. Prueba otra vez. \n");
                    continue;
                }
                switch (opcion) {
                    case 1:
                        insertarPlanta();
                        break;
                    case 2:
                        modificarPlantaNombreComun();
                        break;
                    case 3:
                        modificarPlantaNombreCientifico();
                        break;
                    case 9:
                        menuAdmin();
                        break;
                }
            } catch (InputMismatchException e) {
                System.err.println("ERROR, porfavor ingrese un numero ENTERO");
                sc.next();
            }
        } while (opcion != 9);
    }

    private void modificarPlantaNombreComun() {
        Planta p = null;
        boolean codigoCorrecto = false;
        boolean encontrado = false;
        String codigo;

        do {
            System.out.println("Dime el CODIGO para poder cambiar el nombre de esa PLANTA");
            codigo = sc.nextLine();
            codigoCorrecto = servPlanta.validarCodigo(codigo);
            if (!codigoCorrecto) {
                System.out.println("CODIGO no valido");
            }
        } while (!codigoCorrecto);
        
        encontrado = servPlanta.codigoExistente(codigo);
        if (!encontrado) {
            System.out.println("CODIGO no existente");
        }
        
        System.out.print("Nombre Comun nuevo: ");
        String nombreNuevo = sc.nextLine().trim();
        try {
            boolean nuevo = servPlanta.actualizarNombreComun(codigo, nombreNuevo);
            if (nuevo) {
                System.out.println("Planta de codigo: " + codigo + ", ahora el nombre comun es:" + nombreNuevo);
            } else {
                System.out.println("ERROR en la actualización");
            }
        } catch (Exception ex) {
            System.out.println("ERROR en la actualización" + ex.getMessage());
        }
    }

    public void modificarPlantaNombreCientifico() {
        Planta p = null;
        boolean codigoCorrecto = false;
        boolean encontrado = false;
        String codigo;

        do {
            System.out.println("Dime el CODIGO para poder cambiar el nombre de esa PLANTA");
            codigo = sc.nextLine();
            codigoCorrecto = servPlanta.validarCodigo(codigo);
            if (!codigoCorrecto) {
                System.out.println("CODIGO no valido");
            }
        } while (!codigoCorrecto);
        
        encontrado = servPlanta.codigoExistente(codigo);
        if (!encontrado) {
            System.out.println("CODIGO no existente");
        }
        
        System.out.print("Nombre Cientifico nuevo: ");
        String nombreNuevo = sc.nextLine().trim();
        try {
            boolean nuevo = servPlanta.actualizarNombreCientifico(codigo, nombreNuevo);
            if (nuevo) {
                System.out.println("Planta de codigo: " + codigo + ", ahora el nombre cientifico es:" + nombreNuevo);
            } else {
                System.out.println("ERROR en la actualización");
            }
        } catch (Exception ex) {
            System.out.println("ERROR en la actualización" + ex.getMessage());
        }
    }

    public void insertarPlanta() {
        Planta p = new Planta();
        boolean valido = false;
        while (!valido) {
            System.out.print("Dime el CODIGO de la planta: ");
            try {
                String codigoPlanta = sc.nextLine().trim().toUpperCase();
                valido = servPlanta.validarCodigo(codigoPlanta);
                if (!valido) {
                    System.out.println("3-20 Caracteres");
                    continue;
                }
                p.setCodigo(codigoPlanta);
            } catch (Exception e) {
                System.out.println("ERROR al insertar los datos " + e.getMessage());
                valido = false;
            }
        }

        System.out.print("Nombre común: ");
        String nombreComun = sc.nextLine().trim();
        p.setNombreComun(nombreComun);

        System.out.print("Nombre científico: ");
        String nombreCientifico = sc.nextLine().trim();
        p.setNombreCientifico(nombreCientifico);

        valido = servPlanta.validarPlanta(p);
        if (!valido) {
            System.out.println("Los datos que has introducido no son correctos. Revisa los valores ingresados.");
            return;
        }

        try {
            servPlanta.insertar(p);
            System.out.println("Planta INSERTADA.");
        } catch (Exception e) {
            System.out.println("ERROR al insertar la planta" + e.getMessage());
        }
    }

    private void gestionEjemplar() {
        System.out.println("\n\t\tMenu-Gestión-Ejemplar\n");
    }

    private void gestionMensaje() {
        System.out.println("\n\t\tMenu-Gestión-Mensaje\n");
    }

    public void gestionPersona() {
        System.out.println("\n\t\tMenú-Gestión-Persona\n");
        Persona p = new Persona();
        Credencial c = new Credencial();
        
        String nombre = solicitarNombre();
        p.setNombre(nombre);

        String email;
        boolean emailValido = false;
        while (!emailValido) {
            email = solicitarEmail();
            if (servPersona.existeEmail(email)) {
                System.out.println("EMAIL ya registrado. Intente con otro.");
            } else {
                p.setEmail(email);
                emailValido = true;
            }
        }

        boolean usuarioValido = false;
        String usuario;
        String contrasena;
        do {
            System.out.print("Usuario: ");
            usuario = sc.nextLine().trim();
            if (servCredenciales.existeUsuario(usuario)) {
                System.out.println("El usuario '" + usuario + "' ya está registrado. Elige otro nombre.");
            } else {
                usuarioValido = true;
                c.setUsuario(usuario);
            }
        } while (!usuarioValido);

        contrasena = solicitarContraseña();
        c.setPassword(contrasena);

        try {
            servPersona.insertar(p);
            Long idPersona = p.getId();

            if (idPersona == null || idPersona == 0) {
                System.out.println("ERROR en el registro de la persona. No hay ID asignado.");
                return;
            }

            servCredenciales.insertar(usuario, contrasena, idPersona);
            System.out.println("Registro exitoso. Usuario y credenciales creados correctamente.");


            System.out.println("\nListado de todas las personas registradas:");
            
            List<Persona> todasLasPersonas = servPersona.obtenerTodasLasPersonas();
            if (todasLasPersonas.isEmpty()) {
                System.out.println("No hay personas registradas.");
            } else {
                for (Persona persona : todasLasPersonas) {
                    System.out.println(persona); 
                }
            }
            
        } catch (Exception e) {
            System.err.println("Ocurrió un error durante el registro de las credenciales: " + e.getMessage());
        }
    }

    


    private String solicitarEmail() {
        while (true) {
            System.out.print("Dime el EMAIL de la persona a quien quieres REGISTRAR: ");
            String email = sc.nextLine().trim();
            if (emailValido(email)) {
                return email;
            } else {
                System.out.println("El EMAIL tiene que tener la primera letra mayúscula");
            }
        }
    }

    private String solicitarNombre() {
        while (true) {
            System.out.print("Dime el NOMBRE de la persona a quien quieres REGISTRAR: ");
            String nombre = sc.nextLine().trim();
            if (nombreValido(nombre)) {
                return nombre;
            } else {
                System.out.println("El NOMBRE tiene que tener la primera letra mayúscula");
            }
        }
    }

    private String solicitarUsuario() {
        while (true) {
            System.out.print("Usuario: ");
            String usuario = sc.nextLine().trim();
            if (servCredenciales.existeUsuario(usuario)) {
                System.out.println("El usuario '" + usuario + "' ya está registrado. Elige otro nombre.");
            } else {
                return usuario;
            }
        }
    }

    private String solicitarContraseña() {
        while (true) {
            System.out.println("Dime la CONTRASEÑA que le quieres poner a tu USUARIO (4 digitos EJ: 0000): ");
            String contrasena = sc.nextLine().trim();
            if (contraseñaValida(contrasena)) {
                return contrasena;
            } else {
                System.out.println("La CONTRASEÑA tiene que tener 4 digitos");
            }
        }
    }

    private boolean emailValido(String email) {
        return email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.com$");
    }

    private boolean contraseñaValida(String contraseña) {
        return contraseña.matches("\\d{4}");
    }

    private boolean nombreValido(String nombre) {
        return nombre.matches("[A-Z][a-z]*");
    }
}
