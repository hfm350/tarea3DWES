package com.hfm350.tarea3dweshfm350.modelo;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "mensajes")
public class Mensaje implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	@Temporal(TemporalType.TIME)
	private Date fechahora;

	@Column
	private String mensaje;

	@ManyToOne
	@JoinColumn(name = "persona_id")
	private Persona persona;

	@ManyToOne
	@JoinColumn(name = "ejemplar_id")
	private Ejemplar ejemplar;

	public Mensaje() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechahora() {
		return fechahora;
	}

	public void setFechahora(Date fechahora) {
		this.fechahora = fechahora;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Ejemplar getEjemplar() {
		return ejemplar;
	}

	public void setEjemplar(Ejemplar ejemplar) {
		this.ejemplar = ejemplar;
	}

	@Override
	public String toString() {
		return "Mensaje [id=" + id + ", fechahora=" + fechahora + ", mensaje=" + mensaje + ", persona=" + persona
				+ ", ejemplar=" + ejemplar + "]";
	}
	
	

}