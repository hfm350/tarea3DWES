package com.hfm350.tarea3dweshfm350.modelo;

public class Sesion {

	private Perfil perfil;

	public Sesion() {
	}

	public enum Perfil {
		INVITADO, PERSONAL, ADMIN
	}

	public Sesion( Perfil perfil) {
		super();
		this.perfil = perfil;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public void cerrarSesion() {
		this.perfil = Perfil.INVITADO;
	}

}
