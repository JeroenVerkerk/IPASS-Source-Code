package nl.hu.ipass.quizschoolapp.webservices;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/leerlingen")
public class LeerlingResource {
	
	@GET
	@Produces("application/json")
	public String getAllLeerlingen() {
		LeerlingService service = ServiceProvider.getLeerlingService();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		for (Leerling l : service.getAllLeerlingen()) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("Leerlingnummer", l.getLeerlingnummer());
			job.add("KlasCode", l.getKlascode().getKlascode());
			job.add("Profiel", l.getProfiel());
			job.add("Niveau", l.getNiveau());
			job.add("Naam", l.getNaam());
			job.add("Achternaam", l.getAchternaam());
			job.add("Postcode", l.getPostcode());
			job.add("Woonplaats", l.getWoonplaats());
			job.add("Inlognaam", l.getInlognaam().getInlognaam());
			
			jab.add(job);
		}
		
		
		JsonArray array = jab.build();
		return array.toString();
	}
	
	@GET
	@Path("{inlognaam}")
	@Produces("application/json")
	public String getLeerlingByInlognaam(@PathParam("inlognaam") String inlognaam) {
		LeerlingService service = ServiceProvider.getLeerlingService();
		Leerling l = service.getLeerlingByInlognaam(inlognaam);
		
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("Leerlingnummer", l.getLeerlingnummer());
		job.add("KlasCode", l.getKlascode().getKlascode());
		job.add("Profiel", l.getProfiel());
		job.add("Niveau", l.getNiveau());
		job.add("Naam", l.getNaam());
		job.add("Achternaam", l.getAchternaam());
		job.add("Postcode", l.getPostcode());
		job.add("Woonplaats", l.getWoonplaats());
		job.add("Inlognaam", l.getInlognaam().getInlognaam());
		
		return job.build().toString();
	}
	
}
