package co.edu.udea.ingenieriaweb.xsoftbackend.bl;

import java.util.Date;
import java.util.List;

import co.edu.udea.ingenieriaweb.xsoftbackend.dto.Actividad;
import co.edu.udea.ingenieriaweb.xsoftbackend.dto.Hora;
import co.edu.udea.ingenieriaweb.xsoftbackend.exception.DataBaseException;
import co.edu.udea.ingenieriaweb.xsoftbackend.exception.LogicException;

public interface ActividadBl {
	
	public void guardarActividad(String descripcion, String identificacionEmpleado) throws DataBaseException, LogicException;
	
	public List<Actividad> obtenerActividades() throws DataBaseException, LogicException;
	
	public List<Hora> obtenerHoras(String idActividad) throws DataBaseException, LogicException;
	
	public String asignarHora(String idActividad, String fecha, String hora) throws DataBaseException,LogicException;
}
