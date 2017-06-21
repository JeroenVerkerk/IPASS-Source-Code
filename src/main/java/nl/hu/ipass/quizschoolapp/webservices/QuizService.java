package nl.hu.ipass.quizschoolapp.webservices;

import java.util.List;
import nl.hu.ipass.quizschoolapp.persistence.*;

public class QuizService {
	//maak nieuwe dao's aan
	private QuizDao quizDao = new QuizDao();
	private QuestionDao questionDao = new QuestionDao();
	private AnswerDao answerDao = new AnswerDao();
	
	//haal de volgende functies op uit de dao's zodat ze gebruikt kunnen worden in de resources
	public List<Quiz> getAllQuizes() {
		return quizDao.findAll();
	}
	
	public List<Quiz> getOverzicht(String vak, String niveau) {
		return quizDao.findOverzicht(vak, niveau);
	}
	
	public List<Quiz> getByDocent(String docent) {
		return quizDao.findByDocent(docent);
	}
	
	public List<Question> getAllQuestions() {
		return questionDao.findAll();
	}
	
	public List<Answer> getAllAnswers() {
		return answerDao.findAll();
	}
	
	public Quiz getQuizById(int id) {
		return quizDao.findById(id);
	}
	
	public Question getQuestionById(int id) {
		return questionDao.findById(id);
	}
	
	
	public List<Question> getQuestionByQuizId(int id) {
		return questionDao.findByQuizId(id);
	}
	
	public List<Answer> getAnswersByQuestionId(int id) {
		return answerDao.findById(id);
	}
	
	public boolean updateQuiz(Quiz quiz) {
		return quizDao.update(quiz);
	}
	
	public boolean insertQuiz(Quiz quiz) {
		return quizDao.insertQuiz(quiz);
	}
	
	public boolean deleteQuestion(Question question) {
		return questionDao.deleteQuestion(question);
	}
	
	public boolean updateQuestion(Question question) {
		return questionDao.updateQuestion(question);
	}
	
	public boolean insertQuestion(Question question) {
		return questionDao.insertQuestion(question);
	}
	
	public boolean deleteAnswer(Answer answer) {
		return answerDao.deleteAnswer(answer);
	}
	
	public boolean updateAnswer(Answer answer) {
		return answerDao.updateAnswer(answer);
	}
	
	public boolean insertAnswer(Answer answer) {
		return answerDao.insertAnswer(answer);
	}
	
	public boolean deleteQuiz(Quiz quiz) {
		return quizDao.deleteQuiz(quiz);
	}
	
}
