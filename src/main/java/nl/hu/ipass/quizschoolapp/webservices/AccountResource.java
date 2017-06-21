package nl.hu.ipass.quizschoolapp.webservices;

import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@Path("/accounts")
public class AccountResource {
	
	@GET
	@Path("{id}")
	@Produces("application/json")
	public String getRoleByInlognaam(@PathParam("id") String id) {
		AccountService service = ServiceProvider.getAccountService();
		Account account = service.getByInlognaam(id);
		
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("Inlognaam", account.getInlognaam());
		job.add("Rol", account.getRole());
		
		return job.build().toString();
	}
}
