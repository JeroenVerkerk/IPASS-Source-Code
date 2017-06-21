package nl.hu.ipass.quizschoolapp.webservices;

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
	public String getQuizzes() {
		ScoreService service = ServiceProvider.getScoreService();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
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
		return array.toString();
	}
	
	@GET
	@Path("{id}")
	@Produces("application/json")
	public String getScoreById(@PathParam("id") int id) {
		ScoreService service = ServiceProvider.getScoreService();
		Score score = service.getScoreById(id);
		
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("ScoreId", score.getId());
		job.add("Score", score.getScore());
		job.add("Leerlingnummer", score.getLeerlingnummer());
		job.add("QuizNaam", score.getQuizNaam());
		job.add("Datum", score.getDatum());
		
		return job.build().toString();
	}
	
	@GET
	@Path("/leerling/{id}")
	@Produces("application/json")
	public String getScoreByLeerling(@PathParam("id") int id) {
		ScoreService service = ServiceProvider.getScoreService();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
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
		return array.toString();
	}
	
	@GET
	@Path("/docent/{id}")
	@Produces("application/json")
	public String getScoreByDocent(@PathParam("id") int id) {
		ScoreService service = ServiceProvider.getScoreService();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
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
		return array.toString();
	}
	
	@POST
	@Produces("application/json")
	public String insertScore(@FormParam("insertCijfer") double cijfer,
						   @FormParam("leerlingnummer") int leerlingnummer,
						   @FormParam("quizNaam") String quizNaam,
						   @FormParam("datum") String datum,
						   @FormParam("insertQuizId") int quizId) {
		ScoreService service = ServiceProvider.getScoreService();
		Score newScore = new Score(cijfer, leerlingnummer, quizNaam, datum, quizId);
		service.insertScore(newScore);
		
		return "Score toegevoegd";
	}
}