package nl.hu.ipass.quizschoolapp.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import nl.hu.ipass.quizschoolapp.webservices.*;

public class QuestionDao extends BaseDao {
	private QuizDao quizDao = new QuizDao();
	
	public List<Question> selectQuestions(String query) {
		List<Question> questions = new ArrayList<Question>();
		
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				int id = rs.getInt("question_id");
				String question = rs.getString("question");
				int quizId = rs.getInt("quiz_id");
				Quiz quiz = (Quiz) quizDao.findById(quizId);
				
				Question newQuestion = new Question(id, question);
				newQuestion.setQuizId(quiz);
				questions.add(newQuestion);
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return questions;
	}
	
	public List<Question> findAll() {
		return selectQuestions("SELECT * FROM question");
	}
	
	public Question findById(int id) {
		return selectQuestions("SELECT * FROM question WHERE question_id="+ id).get(0);
	}
	
	public List<Question> findByQuizId(int id) {
		return selectQuestions("SELECT * FROM question WHERE quiz_id="+ id);
	}
	
	public boolean updateQuestion(Question question) {
		boolean result = false;
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			String query = "UPDATE question SET question="+question.getQuestion();
			int i = stmt.executeUpdate(query);
			
			if (i == 1) {
				result = true;
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return result;
	}
	
	public boolean deleteQuestion(Question question) {
		boolean result = false;
		boolean questionExist = findById(question.getId()) != null;
		
		if (questionExist) {
			String query = "DELETE FROM question WHERE id=" + question.getId();
			
			try (Connection conn = super.getConnection()) {
				Statement stmt = conn.createStatement();
				if (stmt.executeUpdate(query) == 1) {
					result = true;
				}
			}
			catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		return result;
	}
	
	public boolean insertQuestion(Question question) {
		boolean result = false;
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO question (question, quiz_id) SELECT '"+question.getQuestion()+"', last_value FROM quiz_quiz_id_seq";
			int i = stmt.executeUpdate(query);
			
			if (i == 1) {
				result = true;
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return result;
	}
}
