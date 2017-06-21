package nl.hu.ipass.quizschoolapp.webservices;

import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/scores")
public class ScoreResource {
	
	@GET
	@Produces("application/json")
	public String getScores() {
		//haal de service op
		ScoreService service = ServiceProvider.getScoreService();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		//maak voor elke gevonden score een nieuw JsonObject aan en voeg
		//deze toe aan de JsonArray
		for (Score s : service.getAllScores()) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("ScoreId", s.getId());
			job.add("Score", s.getScore());
			job.add("Leerlingnummer", s.getLeerlingnummer());
			job.add("QuizNaam", s.getQuizNaam());
			job.add("Datum", s.getDatum());
			
			jab.add(job);
		}
		
		JsonArray array = jab.build();
		//geef de JsonArray terug
		return array.toString();
	}
	
	@GET
	@Path("{id}")
	@Produces("application/json")
	//wijs de parameter die meegegeven wordt in de url toe aan een variable
	public String getScoreById(@PathParam("id") int id) {
		//haal de service op
		ScoreService service = ServiceProvider.getScoreService();
		Score score = service.getScoreById(id);
		
		//maak voor de gevonden score een nieuw JsonObject aan
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("ScoreId", score.getId());
		job.add("Score", score.getScore());
		job.add("Leerlingnummer", score.getLeerlingnummer());
		job.add("QuizNaam", score.getQuizNaam());
		job.add("Datum", score.getDatum());
		
		//geef het object terug
		return job.build().toString();
	}
	
	@GET
	@Path("/leerling/{id}")
	@Produces("application/json")
	//wijs de parameter die meegegeven wordt in de url toe aan een variable
	public String getScoreByLeerling(@PathParam("id") int id) {
		//haal de service op
		ScoreService service = ServiceProvider.getScoreService();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		//maak voor elke gevonden score een nieuw JsonObject aan en voeg
		//deze toe aan de JsonArray
		for (Score s : service.getScoreByLeerling(id)) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("ScoreId", s.getId());
			job.add("Score", s.getScore());
			job.add("Leerlingnummer", s.getLeerlingnummer());
			job.add("QuizNaam", s.getQuizNaam());
			job.add("Datum", s.getDatum());
			
			jab.add(job);
		}
		
		JsonArray array = jab.build();
		//geef de Array terug
		return array.toString();
	}
	
	@GET
	@Path("/leerling/lijst/{id}")
	@Produces("application/json")
	//wijs de parameter die meegegeven wordt in de url toe aan een variable
	public String getLijstScoreByLeerling(@PathParam("id") int id) {
		//haal de service op
		ScoreService service = ServiceProvider.getScoreService();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		//voor elke score die gevonden is wordt een nieuwe object aangemaakt
		//en deze wordt toe gevoegd aan de JsonArray
		for (LijstScore l : service.getLijstScoresByLeerlingnummer(id)) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("ScoreId", l.getId());
			job.add("Score", l.getScore());
			job.add("Leerlingnummer", l.getLeerlingnummer());
			job.add("LijstNaam", l.getLijstNaam());
			job.add("Datum", l.getDatum());
			
			jab.add(job);
		}
		
		JsonArray array = jab.build();
		//geef de JsonArray terug
		return array.toString();
	}
	
	@GET
	@Path("/docent/{id}")
	@Produces("application/json")
	//wijs de parameter die meegegeven wordt in de url toe aan een variable
	public String getScoreByDocent(@PathParam("id") int id) {
		//haal de service op
		ScoreService service = ServiceProvider.getScoreService();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		//maak voor elke gevonden score een nieuw JsonObject aan en
		//voeg deze toe aan de JsonArray
		for (Score s : service.getScoreByDocent(id)) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("ScoreId", s.getId());
			job.add("Score", s.getScore());
			job.add("Leerlingnummer", s.getLeerlingnummer());
			job.add("QuizNaam", s.getQuizNaam());
			job.add("Datum", s.getDatum());
			
			jab.add(job);
		}
		
		JsonArray array = jab.build();
		//geef de Array terug
		return array.toString();
	}
	
	@POST
	@RolesAllowed({ "leerling", "admin" })
	@Produces("application/json")
	//maak voor elke waarde uit het html formulier een variable aan
	public String insertScore(@FormParam("insertCijfer") double cijfer,
						   @FormParam("leerlingnummer") int leerlingnummer,
						   @FormParam("quizNaam") String quizNaam,
						   @FormParam("datum") String datum,
						   @FormParam("insertQuizId") int quizId) {
		//haal de service op
		ScoreService service = ServiceProvider.getScoreService();
		//maak een nieuwe object aan met de waardes die hierboven zijn aangemaakt
		Score newScore = new Score(cijfer, leerlingnummer, quizNaam, datum, quizId);
		//gebruik dit object in de onderstaande functie uit de dao
		service.insertScore(newScore);
		
		return null;
	}
	
	@POST
	@RolesAllowed({ "leerling", "admin" })
	@Path("/lijst")
	@Produces("application/json")
	//maak voor elke waarde uit het html formulier een variable aan
	public String insertLijstScore(@FormParam("insertCijfer") double cijfer,
						   @FormParam("leerlingnummer") int leerlingnummer,
						   @FormParam("lijstNaam") String lijstNaam,
						   @FormParam("datum") String datum) {
		//haal de service op
		ScoreService service = ServiceProvider.getScoreService();
		//maak een nieuwe object aan met de waardes die hierboven zijn aangemaakt
		LijstScore newScore = new LijstScore(cijfer, leerlingnummer, lijstNaam, datum);
		//gebruik dit object in de onderstaande functie uit de dao
		service.insertLijstScore(newScore);
		
		return null;
	}
}
