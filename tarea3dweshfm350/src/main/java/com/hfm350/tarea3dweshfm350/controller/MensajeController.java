package com.hfm350.tarea3dweshfm350.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.hfm350.tarea3dweshfm350.modelo.Mensaje;

@Controller
public class MensajeController {

    @GetMapping("/gestionMensajes")
    public String gestionMensajes(Model model) {
    	List<Mensaje> listaMensajes = new ArrayList<Mensaje>(); 
    	model.addAttribute("mensajes", listaMensajes);
        return "gestionMensajes";  
    }
}