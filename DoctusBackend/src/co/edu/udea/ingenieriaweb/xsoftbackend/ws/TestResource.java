package co.edu.udea.ingenieriaweb.xsoftbackend.ws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/test")
public class TestResource {

	@GET
	@Path("saludar")
	@Produces(MediaType.APPLICATION_JSON)
	public String helloWordlWs(){
		return "Hello world from backend";
	}
}
