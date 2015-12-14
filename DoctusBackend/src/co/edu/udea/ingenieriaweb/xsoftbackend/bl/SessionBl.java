package co.edu.udea.ingenieriaweb.xsoftbackend.bl;

import co.edu.udea.ingenieriaweb.xsoftbackend.dto.Usuario;
import co.edu.udea.ingenieriaweb.xsoftbackend.exception.DataBaseException;
import co.edu.udea.ingenieriaweb.xsoftbackend.exception.LogicException;

/**
 * 
 * @author Julianesten
 *
 */
public interface SessionBl {
	/**
	 *  Metodo mediante el cual se verifica el logeo de un usuario en el sistema
	 * @param username
	 * @param password
	 * @return token de la session
	 * @throws LogicException
	 * @throws DataBaseException
	 */
	public String autenticar(String username, String password) throws LogicException, DataBaseException;
	
	/**
	 *Metodo mediante el cual se cierra la sesion de un usuario en la DB 
	 * @param usuario
	 */
	public void cerrarSesion(String idUsuario) throws DataBaseException, LogicException;
	
	public int autenticarSinToken(String username, String password) throws LogicException, DataBaseException;
}
