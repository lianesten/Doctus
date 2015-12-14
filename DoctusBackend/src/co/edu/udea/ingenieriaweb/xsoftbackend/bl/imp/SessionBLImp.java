package co.edu.udea.ingenieriaweb.xsoftbackend.bl.imp;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import co.edu.udea.ingenieriaweb.xsoftbackend.bl.SessionBl;
import co.edu.udea.ingenieriaweb.xsoftbackend.dao.UsuarioDAO;
import co.edu.udea.ingenieriaweb.xsoftbackend.dto.Usuario;
import co.edu.udea.ingenieriaweb.xsoftbackend.exception.DataBaseException;
import co.edu.udea.ingenieriaweb.xsoftbackend.exception.LogicException;
import co.edu.udea.ingenieriaweb.xsoftbackend.seguridad.Autenticator;

/**
 * Clase que permite controlar las sesiones de los usuarios en el sistema
 * @author Julianesten
 *
 */
public class SessionBLImp implements SessionBl {

	/**
	 * Necesitamos un objeto de la clase Autenticator para generar el token
	 */
	private Autenticator autenticator = new Autenticator();
	
	UsuarioDAO usuarioDAO;
	
    Usuario usuario;
	
	public UsuarioDAO getUsuarioDAO(){
		return usuarioDAO;
	}
	
	public void setUsuarioDAO(UsuarioDAO usuarioDAO){
		this.usuarioDAO = usuarioDAO;
	}
	
	/**
	 * Metodo encargado de logear los usuarios en el sistema
	 * Retorna un token de 512 caracteres que contiene la informacion basica de 
	 * un usuario, IdUsuari, username y privilegios
	 * @param username
	 * @param password
	 * @return Token   
	 */
	@Override
	public String autenticar(String username, String password) throws LogicException, DataBaseException {
		
		 Usuario usuario = new Usuario();
	        
	        try {
	            usuario = usuarioDAO.obtenerUsuarioUsername(username);
	            Logger log = Logger.getLogger(this.getClass());
	            log.info("Nombre de usuario: " + usuario.getNombres());
	         JOptionPane.showMessageDialog(null, "Nombre de usuario: " + usuario.getNombres());
	        } catch (Exception e) {
	        	Logger log = Logger.getLogger(this.getClass());
	        	e.printStackTrace();
				log.error("El usuario no existe en la base de datos"+ e.toString());
				throw new LogicException(e, "El usuario o la contraseña ingresados no son validos");
	        }
	        /**
	         * Verificamos la contraseña coincide
	         */
	        if(!usuario.getPassword().equals(password)){
	        	Logger log = Logger.getLogger(this.getClass());
	        	log.error("Contraseña  incorrecta");
	        	throw new LogicException("El usuario o la contraseña ingresados no son validos");
	        }
	        
	        /**
	         * Le pasamos los parametros que queremos setear en el TOKEN
	         */
	        autenticator.addParam( Autenticator.USER_ID, usuario.getNumeroId().toString() );
	        autenticator.addParam(Autenticator.USERNAME, username);
	        autenticator.addParam(Autenticator.ROL, Integer.toString(usuario.getPrivilegio()));
			 
				 
	        /**
	         * String para almacenar el token
	         */
	        String token = null;	
	        
	        try {
	            /**
	             * Se debe enviar la Key con la que se va a cifrar el token
	             */
	            token = autenticator.generarToken("xsoft");
	            Logger log = Logger.getLogger(this.getClass());
	            log.info("Token: " + token);
	            
	        } catch (UnsupportedEncodingException ex) {
	        	Logger log = Logger.getLogger(this.getClass());
	        	ex.printStackTrace();
				log.error("Error obteniendo el usuario por medio de su Username, "+ ex.toString());
				throw new DataBaseException(ex, "Error obteniendo usuario por Usernaname");
	        }catch (IllegalArgumentException ex){
	        	Logger log = Logger.getLogger(this.getClass());
	        	ex.printStackTrace();
				log.error("Error obteniendo el usuario por medio de su Username"+ ex.toString());
				throw new DataBaseException(ex, "Error obteniendo usuario por Usernaname");
	        } catch (NoSuchAlgorithmException ex){
	        	Logger log = Logger.getLogger(this.getClass());
	        	ex.printStackTrace();
				log.error("Error obteniendo el usuario por medio de su Username"+ ex.toString());
				throw new DataBaseException(ex, "Error obteniendo usuario por Usernaname");
	        } catch (InvalidKeyException ex){
	        	Logger log = Logger.getLogger(this.getClass());
	        	ex.printStackTrace();
				log.error("Error obteniendo el usuario por medio de su Username"+ ex.toString());
				throw new DataBaseException(ex, "Error obteniendo usuario por Usernaname");
	        }
	        
	        usuario.setToken(token);
	        try {
				usuarioDAO.actualizarUsuario(usuario);
			} catch (DataBaseException e) {
				Logger log = Logger.getLogger(this.getClass());
	        	e.printStackTrace();
				log.error("Error obteniendo el usuario por medio de su Username"+ e.toString());
				throw new DataBaseException(e, "El usuario o la contraseña no son validos");
			}
		return token;
	}
	
	 
    /**
     * Cierra una sesión de usuario de usuario a partir de un token.
     * @param  token Token que contiene los datos de la sesión del usuario       
     * @throws DataBaseException 
     */
	@Override
	public void cerrarSesion(String token) throws DataBaseException, LogicException {
		 try {
			 Usuario usuario  = usuarioDAO.obtenerUsuarioToken(token);
			 if(usuario==null || "".equals(usuario.getNumeroId())){
				 throw new LogicException("EL token es invalido");
			 }
	            usuario.setToken(null);
	            usuarioDAO.actualizarUsuario(usuario);                
	        } catch (DataBaseException ex) {
	        	Logger log = Logger.getLogger(this.getClass());
				log.error("Error cerrando la sesion: "+ ex.toString());
				throw new DataBaseException(ex, "La sesion no pudo ser cerrada, intente luego");
	            
	           
	        }catch(LogicException e){
	        	throw new LogicException(e.getMessage());
	        }
	}

	@Override
	public int autenticarSinToken(String username, String password)
			throws LogicException, DataBaseException {
        try {
            usuario = usuarioDAO.obtenerUsuarioUsername(username);
            Logger log = Logger.getLogger(this.getClass());
            log.info("Nombre de usuario: " + usuario.getNombres());
        // JOptionPane.showMessageDialog(null, "Nombre de usuario: " + usuario.getNombres());
        } catch (Exception e) {
        	Logger log = Logger.getLogger(this.getClass());
        	e.printStackTrace();
			log.error("El usuario no existe en la base de datos"+ e.toString());
			throw new LogicException(e, "El usuario o la contraseña ingresados no son validos");
        }
        /**
         * Verificamos la contraseña coincide
         */
        if(!usuario.getPassword().equals(password)){
        	Logger log = Logger.getLogger(this.getClass());
        	log.error("Contraseña  incorrecta");
        	throw new LogicException("El usuario o la contraseña ingresados no son validos");
        }
		return usuario.getPrivilegio();
	}

}