package nl.hu.ipass.quizschoolapp.webservices;

public class Question {
	private int id;
	private String question;
	private Quiz quizId;
	
	public Question(String question) {
		this.question = question;
	}
	
	public Question(int id, String question) {
		this.id = id;
		this.question = question;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Quiz getQuizId() {
		return quizId;
	}

	public void setQuizId(Quiz quizId) {
		this.quizId = quizId;
	}
	
	
}
