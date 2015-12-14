package co.edu.udea.ingenieriaweb.xsoftbackend.dao.imp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import co.edu.udea.ingenieriaweb.xsoftbackend.dao.ActividadDAO;
import co.edu.udea.ingenieriaweb.xsoftbackend.dto.Actividad;
import co.edu.udea.ingenieriaweb.xsoftbackend.dto.Hora;
import co.edu.udea.ingenieriaweb.xsoftbackend.dto.Usuario;
import co.edu.udea.ingenieriaweb.xsoftbackend.exception.DataBaseException;

public class ActividadDAOImp extends HibernateDaoSupport implements
		ActividadDAO {

	private Session session = null;
	private Logger log;

	@Override
	public void guardarActividad(Actividad actividad) throws DataBaseException {

		log = Logger.getLogger(this.getClass());
		try {
			session = getSession();
			Transaction tx = session.beginTransaction();
			session.save(actividad);
			session.flush();
			tx.commit();
		} catch (HibernateException e) {
			log.error(e);
			throw new DataBaseException(e,
					"Error almacenando un Actividad en la BD");
		} catch (Exception e) {
			log.error(e);
			throw new DataBaseException(e,
					"Error almacenando un Actividad en la BD");
		}

	}

	@Override
	public List<Actividad> obtenerActividades() throws DataBaseException {
		List<Actividad> listaActividades;
		session = null;
		Logger log = null;
		log = Logger.getLogger(this.getClass());
		try {

			session = getSession();

			Criteria criteria = session.createCriteria(Actividad.class);
			//.add(Restrictions.eq("identificacionEmpleado", "1152686066"))
			listaActividades = criteria.list();

		} catch (HibernateException e) {
			log.error(e);
			throw new DataBaseException(e,
					"Error obteniendo lista Actividades en la DB");
		} catch (Exception e) {
			log.error(e);
			throw new DataBaseException(e,
					"Error obteniendo lista Actividades en la DB");
		}

		return listaActividades;
	}

	@Override
	public void asignarHora(Hora hora) throws DataBaseException {
		log = Logger.getLogger(this.getClass());
		try {
			session = getSession();
			Transaction tx = session.beginTransaction();
			session.save(hora);
			session.flush();
			tx.commit();
		} catch (HibernateException e) {
			log.error(e);
			throw new DataBaseException(e,
					"Error almacenando Hora en la BD");
		} catch (Exception e) {
			log.error(e);
			throw new DataBaseException(e,
					"Error almacenando Hora en  la BD");
		}
		
	}

	@Override
	public Actividad obtenerActividad(String idActividad)
			throws DataBaseException {
		log = Logger.getLogger(this.getClass());
		Session session = null;
		try{
			Actividad actividad= null;
			session = getSession();
			
			
			Criteria criteria = session.createCriteria(Actividad.class);
			
			
			actividad =(Actividad)session.get(Actividad.class, idActividad);
			
			
			return actividad;

		}catch(HibernateException e){
			log.error(e);
			throw new DataBaseException(e, "Error obtendiendo un Usuario en la BD");
		}catch(Exception e){
			log.error(e);
			throw new DataBaseException(e, "Error obteniendo un Usuario en la BD");
		}
		
	}

	@Override
	public List<Hora> obtenerHoras() throws DataBaseException {
		List<Hora> listaHoras;
		session = null;
		Logger log = null;
		log = Logger.getLogger(this.getClass());
		try {

			session = getSession();

			Criteria criteria = session.createCriteria(Hora.class);
			//.add(Restrictions.eq("identificacionEmpleado", "1152686066"))
			listaHoras = criteria.list();

		} catch (HibernateException e) {
			log.error(e);
			throw new DataBaseException(e,
					"Error obteniendo lista Actividades en la DB");
		} catch (Exception e) {
			log.error(e);
			throw new DataBaseException(e,
					"Error obteniendo lista Actividades en la DB");
		}

		return listaHoras;
	}

}
