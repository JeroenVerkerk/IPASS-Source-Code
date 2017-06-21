package nl.hu.ipass.quizschoolapp.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import nl.hu.ipass.quizschoolapp.webservices.*;

public class AnswerDao extends BaseDao {
	private QuestionDao questionDao = new QuestionDao();
	
	public List<Answer> selectAnswers(String query) {
		List<Answer> answers = new ArrayList<Answer>();
		
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				int id = rs.getInt("id");
				String answer = rs.getString("answer");
				boolean isCorrect = rs.getBoolean("is_correct");
				int questionId = rs.getInt("question_id");
				Question question = (Question) questionDao.findById(questionId);
				
				Answer newAnswer = new Answer(id, answer, isCorrect);
				newAnswer.setQuestionId(question);
				answers.add(newAnswer);
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return answers;
	}
	
	public List<Answer> findAll() {
		return selectAnswers("SELECT * FROM answers");
	}
	
	public List<Answer> findById(int id) {
		return selectAnswers("SELECT * FROM answers WHERE question_id=" + id);
	}
	
	public boolean updateAnswer(Answer answer) {
		boolean result = false;
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			String query = "UPDATE answers SET answer="+answer.getAnswer();
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
	
	public boolean deleteAnswer(Answer answer) {
		boolean result = false;
		boolean answerExist = findById(answer.getId()) != null;
		
		if (answerExist) {
			String query = "DELETE FROM answers WHERE id=" + answer.getId();
			
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
	
	public boolean insertAnswer(Answer answer) {
		boolean result = false;
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO answers (answer, is_correct, question_id) SELECT '"+answer.getAnswer()+"','"+answer.getIsCorrect()+"', last_value from question_question_id_seq";
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
