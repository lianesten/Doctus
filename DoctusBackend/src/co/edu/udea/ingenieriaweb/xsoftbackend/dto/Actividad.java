package co.edu.udea.ingenieriaweb.xsoftbackend.dto;

import java.io.Serializable;

/**
 * 
 * @author julianesten
 *POJO de la entidad Actividad
 */
public class Actividad implements Serializable {
	
	private int id;
	private String descripcion;
	private String numeroId;

	public Actividad(){}
	

	
	public Actividad(String descripcion, String numeroId){
		this.descripcion=descripcion;
		this.numeroId = numeroId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getnumeroId() {
		return numeroId;
	}

	public void setnumeroId(String numeroId) {
		this.numeroId = numeroId;
	}

	
	

	
}
