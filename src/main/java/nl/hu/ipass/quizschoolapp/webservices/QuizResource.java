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

@Path("/quizzes")
public class QuizResource {
	
	@GET
	@Produces("application/json")
	public String getQuizzes() {
		QuizService service = ServiceProvider.getQuizService();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		for (Quiz q : service.getAllQuizes()) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("QuizId", q.getId());
			job.add("Naam", q.getNaam());
			job.add("Vak", q.getVak());
			job.add("Gemaakt_Door", q.getGemaaktDoor());
			job.add("Niveau", q.getNiveau());
			
			jab.add(job);
		}
		
		JsonArray array = jab.build();
		return array.toString();
	}
	
	@GET
	@Path("/questions")
	@Produces("application/json")
	public String getQuestions() {
		QuizService service = ServiceProvider.getQuizService();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		for (Question q : service.getAllQuestions()) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("QuestionId", q.getId());
			job.add("Question", q.getQuestion());
			job.add("QuizId", q.getQuizId().getId());
			
			jab.add(job);
		}
		
		JsonArray array = jab.build();
		return array.toString();
	}
	
	@GET
	@Path("/question/{id}")
	@Produces("application/json")
	public String getQuestionsByQuizId(@PathParam("id") int id) {
		QuizService service = ServiceProvider.getQuizService();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		for (Question q : service.getQuestionByQuizId(id)) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("QuestionId", q.getId());
			job.add("Question", q.getQuestion());
			job.add("QuizId", q.getQuizId().getId());
			
			jab.add(job);
			
		}
		
		JsonArray array = jab.build();
		return array.toString();
	}
	
	@GET
	@Path("/overzicht/{vak}/{niveau}")
	@Produces("application/json")
	public String getOverzicht(@PathParam("vak") String vak,
							   @PathParam("niveau") String niveau) {
		QuizService service = ServiceProvider.getQuizService();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		for (Quiz q : service.getOverzicht(vak, niveau)) {
			JsonObjectBuilder job =Json.createObjectBuilder();
			job.add("QuizId", q.getId());
			job.add("Naam", q.getNaam());
			job.add("Vak", q.getVak());
			job.add("Gemaakt_Door", q.getGemaaktDoor());
			job.add("Niveau", q.getNiveau());
			
			jab.add(job);
		}
		
		JsonArray array = jab.build();
		return array.toString();
	}
	
	@GET
	@Path("/byDocent/{docent}")
	@Produces("application/json")
	public String getOverzicht(@PathParam("docent") String docent) {
		QuizService service = ServiceProvider.getQuizService();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		for (Quiz q : service.getByDocent(docent)) {
			JsonObjectBuilder job =Json.createObjectBuilder();
			job.add("QuizId", q.getId());
			job.add("Naam", q.getNaam());
			job.add("Vak", q.getVak());
			job.add("Gemaakt_Door", q.getGemaaktDoor());
			job.add("Niveau", q.getNiveau());
			
			jab.add(job);
		}
		
		JsonArray array = jab.build();
		return array.toString();
	}
	
	@GET
	@Path("/answer/{id}")
	@Produces("application/json")
	public String getAnswersByQuestionId(@PathParam("id") int id) {
		QuizService service = ServiceProvider.getQuizService();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		for (Answer a : service.getAnswersByQuestionId(id)) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("AnswerId", a.getId());
			job.add("Answer", a.getAnswer());
			job.add("IsCorrect", a.getIsCorrect());
			job.add("QuestionId", a.getQuestionId().getId());
			
			jab.add(job);
		}
		
		JsonArray array = jab.build();
		return array.toString();
	}
	
	@GET
	@Path("{id}")
	@Produces("application/json")
	public String getQuizById(@PathParam("id") int id) {
		QuizService service = ServiceProvider.getQuizService();
		Quiz quiz = service.getQuizById(id);
		
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("Naam", quiz.getNaam());
		job.add("Vak", quiz.getVak());
		job.add("Gemaakt_Door", quiz.getGemaaktDoor());
		job.add("Niveau", quiz.getNiveau());
		
		return job.build().toString();
	}
	
	@POST
	@RolesAllowed({ "docent", "admin"})
	@Produces("application/json")
	public String createQuiz(@FormParam("naamInsert") String naam,
							 @FormParam("vakInsert") String vak,
							 @FormParam("gemaaktDoorInsert") String gemaaktDoor,
							 @FormParam("niveauInsert") String niveau,
							 @FormParam("questionInsert") String question,
							 @FormParam("answerInsert") String answer,
							 @FormParam("isCorrect") boolean isCorrect) {
		QuizService service = ServiceProvider.getQuizService();
		Quiz newQuiz = new Quiz(vak, niveau, gemaaktDoor, naam);
		Question newQuestion = new Question(question);
		Answer newAnswer = new Answer(answer, isCorrect);
		
		service.insertQuiz(newQuiz);
		service.insertQuestion(newQuestion);
		service.insertAnswer(newAnswer);
		return quizToJson(newQuiz, newQuestion, newAnswer).build().toString();
		
	}
	
	private JsonObjectBuilder quizToJson(Quiz quiz, Question question, Answer answer) {
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("Naam", quiz.getNaam());
		job.add("Vak", quiz.getVak());
		job.add("Gemaakt_Door", quiz.getGemaaktDoor());
		job.add("Niveau", quiz.getNiveau());
		job.add("Vraag", question.getQuestion());
		job.add("Antwoord", answer.getAnswer());
		return job;
	}
	
	@POST
	@RolesAllowed({ "docent", "admin" })
	@Path("/insertAnswer")
	@Produces("application/json")
	public String createAnswer(@FormParam("answerInsert2") String answer,
							   @FormParam("isCorrect2") boolean isCorrect) {
		QuizService service = ServiceProvider.getQuizService();
		Answer newAnswer = new Answer(answer, isCorrect);
		
		service.insertAnswer(newAnswer);
		return answerToJson(newAnswer).build().toString();
	}
	
	private JsonObjectBuilder answerToJson(Answer answer) {
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("Antwoord", answer.getAnswer());
		job.add("Goede Antwoord", answer.getIsCorrect());
		return job;
	}
	
	@POST
	@RolesAllowed({ "docent", "admin" })
	@Path("/insertQuestion")
	@Produces("application/json")
	public String createQuestion(@FormParam("questionInsert2") String question,
								 @FormParam("answerInsert3") String answer,
								 @FormParam("isCorrect3") boolean isCorrect) {
		QuizService service = ServiceProvider.getQuizService();
		Question newQuestion = new Question(question);
		Answer newAnswer = new Answer(answer, isCorrect);
		
		service.insertQuestion(newQuestion);
		service.insertAnswer(newAnswer);
		return questionToJson(newQuestion, newAnswer).build().toString();
	}
	
	private JsonObjectBuilder questionToJson(Question question, Answer answer) {
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("Vraag", question.getQuestion());
		job.add("Antwoord", answer.getAnswer());
		job.add("Goed Antwoord", answer.getIsCorrect());
		return job;
	}
	
	@DELETE
	@RolesAllowed({ "docent", "admin" })
	@Path("{id}")
	public Response deleteCountry(@PathParam("id") int id) {
		QuizService service = ServiceProvider.getQuizService();
		Quiz found = null;
		for (Quiz q : service.getAllQuizes()) {
			if (q.getId() == id) {
				found = q; break;
				
			}
		}
		
		if (found == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			service.deleteQuiz(found);
			return Response.ok().build();
		}
	}
}
