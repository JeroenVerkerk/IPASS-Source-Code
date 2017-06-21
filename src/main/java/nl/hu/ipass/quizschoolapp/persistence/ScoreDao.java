package nl.hu.ipass.quizschoolapp.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import nl.hu.ipass.quizschoolapp.webservices.*;

public class ScoreDao extends BaseDao {
	private LeerlingDao leerlingDao = new LeerlingDao();
	
	public List<Score> selectScores(String query) {
		List<Score> scores = new ArrayList<Score>();
		
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				int scoreId = rs.getInt("score_id");
				double score = rs.getDouble("score");
				int leerlingnummer = rs.getInt("leerlingnummer");
				String quizNaam = rs.getString("quiz_naam");
				String datum = rs.getString("datum");
				int quizId = rs.getInt("quiz_id");
				
				Score newScore = new Score(scoreId, score,leerlingnummer, quizNaam, datum, quizId);
				scores.add(newScore);
				
				
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return scores;
	}
	
	public List<Score> findAll() {
		return selectScores("SELECT * FROM score");
	}
	
	public List<Score> findByLeerlingnummer(int leerlingnummer) {
		return selectScores("SELECT score_id, score, leerlingnummer, quiz_naam, to_char(datum, 'DD-MM-YYYY') as datum, quiz_id FROM score WHERE leerlingnummer=" + leerlingnummer + " ORDER BY datum DESC"); 
	}
	
	public Score findById(int id) {
		return selectScores("SELECT * FROM score WHERE score_id=" + id).get(0);
	}
	
	public List<Score> findByQuizId(int id) {
		return selectScores("SELECT score_id, score, leerlingnummer, quiz_naam, to_char(datum, 'DD-MM-YYYY') as datum, quiz_id FROM score WHERE quiz_id=" + id);
	}
	
	public boolean deleteScore(Score score) {
		boolean result = false;
		boolean scoreExist = findById(score.getId()) != null;
		
		if (scoreExist) {
			String query = "DELETE FROM score WHERE id=" + score.getId();
			
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
	
	public boolean insertScore(Score score) {
		boolean result = false;
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO score (score, leerlingnummer, quiz_naam, datum, quiz_id) VALUES ('"+score.getScore()+"', '"+score.getLeerlingnummer()+"', '"+score.getQuizNaam()+"', '"+score.getDatum()+"', '"+score.getQuizId()+"')";
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
