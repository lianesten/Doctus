package co.edu.udea.ingenieriaweb.xsoftbackend.ws;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import co.edu.udea.ingenieriaweb.xsoftbackend.bl.ActividadBl;
import co.edu.udea.ingenieriaweb.xsoftbackend.dto.Actividad;
import co.edu.udea.ingenieriaweb.xsoftbackend.exception.DataBaseException;
import co.edu.udea.ingenieriaweb.xsoftbackend.exception.LogicException;

@Component
@Path("actividades")
public class ActividadResource {
	
	/**
	 * Inyectamos el bean desde Spring FW
	 */
	@Autowired
	private ActividadBl actividadBl;

	/**
	 * Servicio para listar todas las actividades
	 * @return
	 * @throws RemoteException
	 */
	@Path("listarActividades")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarActividades() throws RemoteException{
		List listaActividades =null;
		Logger log = Logger.getLogger(this.getClass());
		Gson gson = new Gson();
		try{
			listaActividades = actividadBl.obtenerActividades();
			if(listaActividades==null){
				return Response.ok(gson.toJson("No hay actividades registrados en el sistema!")).build();
			}
		}catch(LogicException e){
			log.error(e);
			return Response.status(Response.Status.BAD_REQUEST).build();
		}catch(DataBaseException e){
			log.error(e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}catch(Exception e){
			log.error(e);
		}
		
		return Response.ok(gson.toJson(listaActividades)).build();

	}
	
	
	/**
	 * Servicio para listar todas las horasxActividad
	 * @return
	 * @throws RemoteException
	 */
	@Path("listarHoras")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarHoras(
			@QueryParam("idActividad") String idActividad) throws RemoteException{
		List listaHoras =null;
		Logger log = Logger.getLogger(this.getClass());
		Gson gson = new Gson();
		try{
			listaHoras = actividadBl.obtenerHoras(idActividad);
			if(listaHoras==null){
				return Response.ok(gson.toJson("No hay Horas registrados en el sistema!")).build();
			}
		}catch(LogicException e){
			log.error(e);
			return Response.status(Response.Status.BAD_REQUEST).build();
		}catch(DataBaseException e){
			log.error(e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}catch(Exception e){
			log.error(e);
		}
		
		return Response.ok(gson.toJson(listaHoras)).build();

	}
	
	

	@Path("AsignarHora")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response AsignarHora(
			@QueryParam("idActividad") String idActividad,
			@QueryParam("fecha") String fecha,
			@QueryParam("hora") String hora) throws RemoteException{
		Logger log = Logger.getLogger(this.getClass());
		Gson gson = new Gson();
		String mensaje="";
		try{
			//JOptionPane.showMessageDialog(null, "id de la actividad a asignar hora: "+idActividad);
			
			mensaje = actividadBl.asignarHora(idActividad, fecha, hora);
		}catch(LogicException e){
			log.error(e);
			return Response.status(Response.Status.BAD_REQUEST).build();
		}catch(DataBaseException e){
			log.error(e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}catch(Exception e){
			log.error(e);
		}
		
		return Response.ok(gson.toJson(mensaje)).build();
		
	}
	
	@Path("guardarActividad")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response guardarUsuario(
			@QueryParam("descripcion") String descripcion,
			@QueryParam("identificacionEmpleado") String identificacionEmpleado) throws RemoteException{
		Logger log = Logger.getLogger(this.getClass());
		Gson gson = new Gson();
		try{
			//Validar que el empleado con el numero de identificación si exista en el sistema
			/*String idEmpleado = actividad.getIdentificacion();
			System.out.println(idEmpleado);
			empleado = empleadoBl.obtenerEmpleado(idEmpleado);
			if(empleado == null){
				return Response.ok(gson.toJson("El empleado  con id: "+idEmpleado+" no se enceuntra registrado en la base de datos")).build();
			}*/
			//Guardamos la actividad y el empleado quien la realizo
			actividadBl.guardarActividad(descripcion, identificacionEmpleado);
		}catch(LogicException e){
			log.error(e);
			return Response.status(Response.Status.BAD_REQUEST).build();
		}catch(DataBaseException e){
			log.error(e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}catch(Exception e){
			log.error(e);
		}
		
		return Response.ok(gson.toJson("La actividad ha sido alamcenado exitosamente en el sistema")).build();
		
	}
	
	/*
	 *Este servicio sirve si y solo si se envia desde el frontend un Json, para ello se utiliza el type JSONP en AngularJS
	@Path("guardarActividad")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response guardarUsuario(String Json) throws RemoteException{
		Logger log = Logger.getLogger(this.getClass());
		Gson gson = new Gson();
		Actividad actividad = null;
		Empleado empleado = null;
		
		try{
			actividad = gson.fromJson(Json, Actividad.class);
			//Validar que el empleado con el numero de identificación si exista en el sistema
			/*String idEmpleado = actividad.getIdentificacion();
			System.out.println(idEmpleado);
			empleado = empleadoBl.obtenerEmpleado(idEmpleado);
			if(empleado == null){
				return Response.ok(gson.toJson("El empleado  con id: "+idEmpleado+" no se enceuntra registrado en la base de datos")).build();
			}*
			//Guardamos la actividad y el empleado quien la realizo
			actividadBl.guardarActividad(actividad.getDescripcion(), actividad.getIdentificacion());
		}catch(LogicException e){
			log.error(e);
			return Response.status(Response.Status.BAD_REQUEST).build();
		}catch(DataBaseException e){
			log.error(e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}catch(Exception e){
			log.error(e);
		}
		
		return Response.ok(gson.toJson("La actividad ha sido alamcenado exitosamente en el sistema")).build();
		
	}*/

}
