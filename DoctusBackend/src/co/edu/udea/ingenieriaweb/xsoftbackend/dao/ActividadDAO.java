package co.edu.udea.ingenieriaweb.xsoftbackend.dao;

import java.util.List;

import co.edu.udea.ingenieriaweb.xsoftbackend.dto.Actividad;
import co.edu.udea.ingenieriaweb.xsoftbackend.dto.Hora;
import co.edu.udea.ingenieriaweb.xsoftbackend.exception.DataBaseException;

public interface ActividadDAO {
	
	public void guardarActividad(Actividad actividad)  throws DataBaseException;

	public List<Actividad> obtenerActividades() throws DataBaseException;
	
	public List<Hora> obtenerHoras() throws DataBaseException;
	
	public void asignarHora(Hora actividad)  throws DataBaseException;
	
	public Actividad obtenerActividad(String idActividad) throws DataBaseException;
	
}
