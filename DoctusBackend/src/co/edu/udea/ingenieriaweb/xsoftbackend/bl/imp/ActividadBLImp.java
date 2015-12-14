package co.edu.udea.ingenieriaweb.xsoftbackend.bl.imp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import co.edu.udea.ingenieriaweb.xsoftbackend.bl.ActividadBl;
import co.edu.udea.ingenieriaweb.xsoftbackend.bl.UsuarioBl;
import co.edu.udea.ingenieriaweb.xsoftbackend.dao.ActividadDAO;
import co.edu.udea.ingenieriaweb.xsoftbackend.dao.UsuarioDAO;
import co.edu.udea.ingenieriaweb.xsoftbackend.dto.Actividad;
import co.edu.udea.ingenieriaweb.xsoftbackend.dto.Hora;
import co.edu.udea.ingenieriaweb.xsoftbackend.dto.Usuario;
import co.edu.udea.ingenieriaweb.xsoftbackend.exception.DataBaseException;
import co.edu.udea.ingenieriaweb.xsoftbackend.exception.LogicException;

public class ActividadBLImp implements ActividadBl {

	ActividadDAO actividadDAO;
	UsuarioDAO usuarioDAO;
	UsuarioBl usuarioBl;


	public ActividadDAO getActividadDAO() {
		return actividadDAO;
	}

	public void setActividadDAO(ActividadDAO actividadDAO) {
		this.actividadDAO = actividadDAO;
	}

	@Override
	public void guardarActividad(String descripcion,
			String identificacionEmpleado) throws DataBaseException,
			LogicException {
		Usuario usuario = null;
		// Espacio para hacer las respectivas validaciones en esta capa
		if ("".equals(identificacionEmpleado) || "".equals(descripcion)) {
			throw new LogicException(
					"La identificacion del empleado que está creando la actividad y/o la descripcion del a actividad no puede ser vacia ni Nula ");
		}
		/*try {
			JOptionPane.showMessageDialog(null, "ObtenerEmpleado");
			usuario = usuarioBl.obtenerUsuario(identificacionEmpleado);
			if(usuario==null){System.out.println("Usuario null");}
			if(usuario.getNumeroId()!=identificacionEmpleado){System.out.println("identificacion de usuarios no coinciden");}
			System.out.println("despues de obtener usuario");
			JOptionPane.showMessageDialog(null, "nombre empleado: "+usuario.getNombres()+", identificacion: "+usuario.getNumeroId());
		} catch (Exception e) {
			Logger log = Logger.getLogger(this.getClass());
			log.error("Error obteniendo usuario creador de la actividad: " + e);
			new DataBaseException(e, "Error obteniendo usuario creador de actividad");

		}*/
		
		try {
			Actividad actividad =  new Actividad(descripcion, identificacionEmpleado);
			actividadDAO.guardarActividad(actividad);
		} catch (Exception e) {
			Logger log = Logger.getLogger(this.getClass());
			log.error("Error en el almacenamiento de Actividad: " + e);
			new DataBaseException(e, "Error almacenando una Actividad");

		}
		
		
	}

	@Override
	public List<Actividad> obtenerActividades() throws DataBaseException,
			LogicException {
		List<Actividad> listaActividades = new ArrayList<Actividad>();
		List<Actividad> aux = new ArrayList<Actividad>();
		try {
			listaActividades = actividadDAO.obtenerActividades();
			/*aux = actividadDAO.obtenerActividades();
			Usuario user = usuarioDAO.obtenerUsuarioUsername(usernameEmpleado);
			System.out.println("idEmpleado: "+user.getNumeroId());
			for (int i = 0; i < aux.size(); i++) {
				System.out.println(aux.get(i));
				if(user.getNumeroId()==aux.get(i).getnumeroId()){
					listaActividades.add(aux.get(i));
				}
			}*/

		} catch (Exception e) {
			e.printStackTrace();
			throw new DataBaseException(e,
					"Error obteniendo la lista de ActividadInteger.parseIntes en la BD");
		}
		return listaActividades;
	}


	@Override
	public String asignarHora(String idActividad, String fecha,
			String hora) throws DataBaseException, LogicException {
		String mensaje="";
		//Fecha String To date
		int year = Integer.parseInt(fecha.substring(0, 4));
		int month = Integer.parseInt(fecha.substring(5, 7));
		int day = Integer.parseInt(fecha.substring(8, 10));
		String fechaconcatenada = day+"/"+month+"/"+year;
		String fechaString = fechaconcatenada;
	    DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
	    Date fechaActividad = null;
	    try {
	    	fechaActividad = df.parse(fechaString);
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    //Hora actual
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		
		if(fechaActividad.after(date)){
			//JOptionPane.showMessageDialog(null,"La fecha de realización de la actividad no puede ser mayor a la actual");
			mensaje = "La fecha de realización de la actividad no puede ser mayor a la actual"; 
			//throw new LogicException(mensaje);
			return mensaje;
		}
		
		//Validamos que el numero de horas por actividad no sume mas de 8 horas
		try{
		List<Hora> sumHora=obtenerHoras(idActividad);
		int suma = 0;
		for (int i = 0; i < sumHora.size(); i++) {
			suma = suma+sumHora.get(i).getHora();
			if(suma>=8){
				//JOptionPane.showMessageDialog(null, "Una Actividad debe tener maximo 8 horas asigandas");
				mensaje = "Una Actividad debe tener maximo 8 horas asigandas";
				//throw new LogicException(mensaje);
				return mensaje;
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataBaseException(e,
					"Error obteniendo horas en la bd");
		}
		
		
		//Obtenemos la actividad a la cual se le han de asociar las horas trabajadas
		
		//Creamos el pojo de la hora a asignar
		Hora nuevaHora = new Hora(idActividad, dateFormat.format(fechaActividad), Integer.parseInt(hora));
		try{
		actividadDAO.asignarHora(nuevaHora);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataBaseException(e,
					"Error almacenando horas en la bd");
		}
		return "Hora asignada exitosamente";
		
	}

	@Override
	public List<Hora> obtenerHoras(String idActividad) throws DataBaseException, LogicException {
		List<Hora> horas = new ArrayList<Hora>();
		List<Hora> listaHoras = new ArrayList<Hora>();
		try {
			int j;
			horas = actividadDAO.obtenerHoras();
			System.out.println(idActividad);
				for ( j = 0; j < horas.size(); j++) {
					String idAc = horas.get(j).getIdactividad();
					System.out.println("idAc: "+idAc);
					if( idActividad.equals(idAc)){
						listaHoras.add(horas.get(j));
					}
				
			}
				j = 0;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataBaseException(e,
					"Error obteniendo la lista de ActividadInteger.parseIntes en la BD");
		}
		return listaHoras;
	}


}
