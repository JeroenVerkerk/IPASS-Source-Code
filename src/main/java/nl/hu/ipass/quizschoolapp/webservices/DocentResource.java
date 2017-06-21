package nl.hu.ipass.quizschoolapp.webservices;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/docenten")
public class DocentResource {
	@GET
	@Produces("application/json")
	public String getAllDocenten() {
		DocentService service = ServiceProvider.getDocentService();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		for (Docent d : service.getAllDocenten()) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("Docentnummer", d.getDocentnummer());
			job.add("Niveau", d.getNiveau());
			job.add("Naam", d.getNaam());
			job.add("Achternaam", d.getAchternaam());
			job.add("Postcode", d.getPostcode());
			job.add("Woonplaats", d.getWoonplaats());
			job.add("Geboortedatum", d.getGeboortedatum());
			job.add("Inlognaam", d.getInlognaam().getInlognaam());
			
			jab.add(job);
		}
		
		
		JsonArray array = jab.build();
		return array.toString();
	}
	
	@GET
	@Path("{inlognaam}")
	@Produces("application/json")
	public String getDocentByInlognaam(@PathParam("inlognaam") String inlognaam) {
		DocentService service = ServiceProvider.getDocentService();
		Docent d = service.getByInlognaam(inlognaam);
		
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("Docentnummer", d.getDocentnummer());
		job.add("Niveau", d.getNiveau());
		job.add("Naam", d.getNaam());
		job.add("Achternaam", d.getAchternaam());
		job.add("Postcode", d.getPostcode());
		job.add("Woonplaats", d.getWoonplaats());
		job.add("Geboortedatum", d.getGeboortedatum());
		job.add("Inlognaam", d.getInlognaam().getInlognaam());
		
		return job.build().toString();
	}
}
