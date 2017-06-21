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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/quizzes")
public class QuizResource {
	
	@GET
	@Produces("application/json")
	public String getQuizzes() {
		//haal de service op
		QuizService service = ServiceProvider.getQuizService();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		//maak voor elke quiz een JsonObject aan en voeg deze toe aan de JsonArray
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
		//geef de JsonArray terug
		return array.toString();
	}
	
	@GET
	@Path("/questions")
	@Produces("application/json")
	public String getQuestions() {
		//haal de service op
		QuizService service = ServiceProvider.getQuizService();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		//maak voor elke vraag een nieuwe JsonObject aan en voeg deze toe aan de JsonArray
		for (Question q : service.getAllQuestions()) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("QuestionId", q.getId());
			job.add("Question", q.getQuestion());
			job.add("QuizId", q.getQuizId().getId());
			
			jab.add(job);
		}
		
		JsonArray array = jab.build();
		//geef de JsonArray terug
		return array.toString();
	}
	
	@GET
	@Path("/question/{id}")
	@Produces("application/json")
	//haal de volgende parameter uit de url en wijs deze toe aan een variable
	public String getQuestionsByQuizId(@PathParam("id") int id) {
		//haal de service op
		QuizService service = ServiceProvider.getQuizService();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		//maak voor elke vraag een nieuw JsonObject aan en voeg deze toe aan de JsonArray
		for (Question q : service.getQuestionByQuizId(id)) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("QuestionId", q.getId());
			job.add("Question", q.getQuestion());
			job.add("QuizId", q.getQuizId().getId());
			
			jab.add(job);
			
		}
		
		JsonArray array = jab.build();
		//geef de array terug
		return array.toString();
	}
	
	@GET
	@Path("/overzicht/{vak}/{niveau}")
	@Produces("application/json")
	//wijs de parameters die meegegeven worden bij de url aan een variable toe
	public String getOverzicht(@PathParam("vak") String vak,
							   @PathParam("niveau") String niveau) {
		//haal de service op
		QuizService service = ServiceProvider.getQuizService();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		//maak voor elke quiz die gevonden is een nieuw JsonObject aan en voeg deze toe aan de JsonArray
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
		//geef de Array terug
		return array.toString();
	}
	
	@GET
	@Path("/byDocent/{docent}")
	@Produces("application/json")
	//wijs de parameter die meegegeven wordt bij de url toe aan een variable
	public String getOverzichtByDocent(@PathParam("docent") String docent) {
		//haal de service op
		QuizService service = ServiceProvider.getQuizService();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		//maak voor elke quiz die gevonden wordt een nieuw JsonObject aan en voeg deze toe aan de JsonArray
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
		//geef de JsonArray terug
		return array.toString();
	}
	
	@GET
	@Path("/answer/{id}")
	@Produces("application/json")
	//wijs de parameter die meegegeven wordt bij de url toe aan een variable
	public String getAnswersByQuestionId(@PathParam("id") int id) {
		//haal de service op
		QuizService service = ServiceProvider.getQuizService();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		//maak voor elk antwoord dat gevonden wordt een nieuw JsonObject aan en voeg deze toe aan de JsonArray
		for (Answer a : service.getAnswersByQuestionId(id)) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("AnswerId", a.getId());
			job.add("Answer", a.getAnswer());
			job.add("IsCorrect", a.getIsCorrect());
			job.add("QuestionId", a.getQuestionId().getId());
			
			jab.add(job);
		}
		
		JsonArray array = jab.build();
		//geef de JsonArray terug
		return array.toString();
	}
	
	@GET
	@Path("{id}")
	@Produces("application/json")
	//wijs de parameter die meegegeven wordt bij de url toe aan een variable
	public String getQuizById(@PathParam("id") int id) {
		//haal de service op
		QuizService service = ServiceProvider.getQuizService();
		Quiz quiz = service.getQuizById(id);
		
		//maak voor de gevonden quiz een JsonObject aan
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("Naam", quiz.getNaam());
		job.add("Vak", quiz.getVak());
		job.add("Gemaakt_Door", quiz.getGemaaktDoor());
		job.add("Niveau", quiz.getNiveau());
		
		//geef het object terug
		return job.build().toString();
	}
	
	@POST
	@RolesAllowed({ "docent", "admin" })
	@Produces("application/json")
	//haal de volgende waardes uit het formulier op de html pagina
	//en wijs deze toe aan een variable
	public String createQuiz(@FormParam("naamInsert") String naam,
							 @FormParam("vakInsert") String vak,
							 @FormParam("gemaaktDoorInsert") String gemaaktDoor,
							 @FormParam("niveauInsert") String niveau,
							 @FormParam("questionInsert") String question,
							 @FormParam("answerInsert") String answer,
							 @FormParam("isCorrect") boolean isCorrect) {
		//haal de service op
		QuizService service = ServiceProvider.getQuizService();
		//maak de nieuwe objecten aan
		Quiz newQuiz = new Quiz(vak, niveau, gemaaktDoor, naam);
		Question newQuestion = new Question(question);
		Answer newAnswer = new Answer(answer, isCorrect);
		
		//gebruik de aangemaakte objecten bij de volgende functies
		//die gemaakt zijn inde doa's
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
	//haal de volgende gegevens uit het formulier op de html pagina en
	//wijs deze toe aan een variable
	public String createAnswer(@FormParam("answerInsert2") String answer,
							   @FormParam("isCorrect2") boolean isCorrect) {
		//haal de service op
		QuizService service = ServiceProvider.getQuizService();
		//maak een nieuwe object aan
		Answer newAnswer = new Answer(answer, isCorrect);
		
		//gebruik het aangemaakte object in de volgende functie die gemaat zijn in de dao's
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
	//haal de volgende gegevens uit het html formulier en 
	//wijs ze aan een variable toe
	public String createQuestion(@FormParam("questionInsert2") String question,
								 @FormParam("answerInsert3") String answer,
								 @FormParam("isCorrect3") boolean isCorrect) {
		//haal de service op
		QuizService service = ServiceProvider.getQuizService();
		//maak nieuwe objecten aan met de variablen die hierboven zijn gemaakt
		Question newQuestion = new Question(question);
		Answer newAnswer = new Answer(answer, isCorrect);
		
		//gebruik de nieuwe objecten in de functies de gemaakt zijn in de dao's
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
	//wijs de parameter die meegegeven wordt bij de url
	//aan een variable toe
	public Response deleteQuiz(@PathParam("id") int id) {
		QuizService service = ServiceProvider.getQuizService();
		Quiz found = null;
		//kijk of de quiz bestaat door alle quizzen te doorlopen en te kijke
		//of er één is met het zelfde id als het id dat meegegeven is in de url
		for (Quiz q : service.getAllQuizes()) {
			if (q.getId() == id) {
				found = q; break;
				
			}
		}
		
		//als er geen quiz gevonden is dan wordt er een not found response gestuurd
		if (found == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			//als er wel een quiz gevonden is gebruik deze dan in de volgende functies
			service.deleteQuiz(found);
			return Response.ok().build();
		}
	}
}
