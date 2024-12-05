package com.hfm350.tarea3dweshfm350;

import java.lang.System.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import com.hfm350.tarea3dweshfm350.modelo.*;
import com.hfm350.tarea3dweshfm350.servicios.*;

public class Principal implements CommandLineRunner{

	
	
	@Autowired
	ServiciosPlanta servplant;
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("INI");
		
		Planta p = new Planta();
		servplant.validarPlanta(p);
		
		servplant.insertarPlanta(p);
		
		System.out.println("-------------");
		System.out.println("FIN");
		
		
	}

}
