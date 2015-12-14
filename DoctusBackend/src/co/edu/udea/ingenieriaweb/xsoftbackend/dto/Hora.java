package co.edu.udea.ingenieriaweb.xsoftbackend.dto;

import java.util.Date;

/**
 * 
 * @author julianesten
 *POJO de la entidad hora
 */
public class Hora {
	private int id;
	//private Actividad Actividad;
	private String idactividad;
	private String fecha;
	private int hora;
	
	
	public Hora(){}
	
	/*public Hora(Actividad idActividad, Date fecha, int hora){
		this.Actividad = idActividad;
		this.fecha = fecha;
		this.hora = hora;
		
	}*/

	public Hora(String idactividad, String fecha, int hora){
		this.idactividad = idactividad;
		this.fecha = fecha;
		this.hora = hora;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/*public Actividad getActividad() {
		return Actividad;
	}

	public void setActividad(Actividad actividad) {
		Actividad = actividad;
	}*/

	public String getIdactividad() {
		return idactividad;
	}

	public void setIdactividad(String idactividad) {
		this.idactividad = idactividad;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getHora() {
		return hora;
	}

	public void setHora(int hora) {
		this.hora = hora;
	}



}
