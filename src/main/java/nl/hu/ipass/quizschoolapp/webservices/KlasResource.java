package nl.hu.ipass.quizschoolapp.webservices;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/klassen")
public class KlasResource {
	
	@GET
	@Produces("application/json")
	public String getAllKlassen() {
		//haal de serivce op
		KlasService service = ServiceProvider.getKlasService();
		//maak een nieuw JsonArrayBuilder aan
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		//maak voor elke klas een nieuw JsonObject aan en voeg deze toe aan de JsonArray
		for (Klas k : service.getAllKlassen()) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("KlasCode", k.getKlascode());
			job.add("Niveau", k.getNiveau());
			
			jab.add(job);
		}
		
		
		JsonArray array = jab.build();
		//geef de array terug
		return array.toString();
	}
}
