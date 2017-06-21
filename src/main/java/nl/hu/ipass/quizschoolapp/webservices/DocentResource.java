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
		//maak een nieuwe JsonArrayBuilder aan
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		//maak voor alle docenten uit de database een nieuwe JsonObject aan
		for (Docent d : service.getAllDocenten()) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("Docentnummer", d.getDocentnummer());
			job.add("Niveau", d.getNiveau());
			job.add("Naam", d.getNaam());
			job.add("Achternaam", d.getAchternaam());
			job.add("Postcode", d.getPostcode());
			job.add("Woonplaats", d.getWoonplaats());
			job.add("Geboortedatum", d.getGeboortedatum());
			job.add("Inlognaam", d.getInlognaam());
			
			//voeg de objecten toe aan de ArrayBuilder
			jab.add(job);
		}
		
		//maak een array aan
		JsonArray array = jab.build();
		//geef de array terug
		return array.toString();
	}
	
	@GET
	@Path("{inlognaam}")
	@Produces("application/json")
	//haal de volgende gegeven uit de url en geef ze aan een variable
	public String getDocentByInlognaam(@PathParam("inlognaam") String inlognaam) {
		//haal de serivce op
		DocentService service = ServiceProvider.getDocentService();
		//haal de docent op by de inlognaam die meegegeven is aan de url
		Docent d = service.getByInlognaam(inlognaam);
		
		//maak voor deze docent een nieuw JsonObject aan
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("Docentnummer", d.getDocentnummer());
		job.add("Niveau", d.getNiveau());
		job.add("Naam", d.getNaam());
		job.add("Achternaam", d.getAchternaam());
		job.add("Postcode", d.getPostcode());
		job.add("Woonplaats", d.getWoonplaats());
		job.add("Geboortedatum", d.getGeboortedatum());
		job.add("Inlognaam", d.getInlognaam());
		
		//geef het object terug
		return job.build().toString();
	}
	
	@GET
	@Path("/docent/{id}")
	@Produces("application/json")
	//haal de volgende gegeven uit de url en geef ze aan een variable
	public String getDocentByDocentnummer(@PathParam("id") int id) {
		//haal de serivce op
		DocentService service = ServiceProvider.getDocentService();
		//haal de docent op by het nummer die meegegeven is aan de url
		Docent d = service.getByDocentnummer(id);
		
		//maak voor deze docent een nieuw JsonObject aan
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("Docentnummer", d.getDocentnummer());
		job.add("Niveau", d.getNiveau());
		job.add("Naam", d.getNaam());
		job.add("Achternaam", d.getAchternaam());
		job.add("Postcode", d.getPostcode());
		job.add("Woonplaats", d.getWoonplaats());
		job.add("Geboortedatum", d.getGeboortedatum());
		job.add("Inlognaam", d.getInlognaam());
		
		//geef het object terug
		return job.build().toString();
	}
	
}
