package nl.hu.ipass.quizschoolapp.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import nl.hu.ipass.quizschoolapp.webservices.Quiz;

public class QuizDao extends BaseDao {
	public List<Quiz> selectQuizes(String query) {
		List<Quiz> allQuizes = new ArrayList<Quiz>();
		
		try(Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				int id = rs.getInt("quiz_id");
				String vak = rs.getString("vak");
				String niveau = rs.getString("niveau");
				String gemaaktDoor = rs.getString("gemaakt_door");
				String naam = rs.getString("naam");
				
				Quiz newQuiz = new Quiz(id, vak, niveau, gemaaktDoor, naam);
				allQuizes.add(newQuiz);
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return allQuizes;
	}
	
	public List<Quiz> findAll() {
		return selectQuizes("SELECT * FROM quiz");
	}
	
	public Quiz findById(int id) {
		return selectQuizes("SELECT * FROM quiz WHERE quiz_id = "+ id ).get(0);
	}
	
	public List<Quiz> findOverzicht(String vak, String niveau) {
		return selectQuizes("SELECT * FROM quiz WHERE vak='"+vak+"' AND niveau='"+niveau+"'");
	}
	
	public List<Quiz> findByDocent(String docent) {
		return selectQuizes("SELECT * FROM quiz WHERE gemaakt_door='"+docent+"'");
	}
	
	public boolean update(Quiz quiz) {
		boolean result = false;
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			String query = "UPDATE quiz SET vak='"+quiz.getVak()+"', niveau='"+quiz.getNiveau()+"', gemaakt_door='"+quiz.getGemaaktDoor()+"', naam='"+quiz.getNaam()+"'";
			int i = stmt.executeUpdate(query);
			
			if(i == 1) {
				result = true;;
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return result;
	}
	
	public boolean deleteQuiz(Quiz quiz) {
		boolean result = false;
		boolean quizExist = findById(quiz.getId()) != null;
		
		if (quizExist) {
			String query = "DELETE FROM quiz WHERE quiz_id="+ quiz.getId();
			
			try (Connection conn = super.getConnection()) {
				Statement stmt = conn.createStatement();
				if (stmt.executeUpdate(query) == 1) {
					result = true;
				};
			}
			catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		return result;
	}
	
	public boolean insertQuiz(Quiz quiz) {
		boolean result = false;
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO quiz (vak, niveau, gemaakt_door, naam) VALUES ('"+quiz.getVak()+"','"+quiz.getNiveau()+"','"+quiz.getGemaaktDoor()+"','"+quiz.getNaam()+"')";
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