package nl.hu.ipass.quizschoolapp.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import nl.hu.ipass.quizschoolapp.webservices.*;

public class ScoreDao extends BaseDao {

	public List<Score> selectScores(String query) {
		List<Score> scores = new ArrayList<Score>();

		// maak verbinding met de database via de BaseDao
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				// voor elk resultaat in de ResultSet haal de volgende gegevens
				// op
				int scoreId = rs.getInt("score_id");
				double score = rs.getDouble("score");
				int leerlingnummer = rs.getInt("leerlingnummer");
				String quizNaam = rs.getString("quiz_naam");
				String datum = rs.getString("datum");
				int quizId = rs.getInt("quiz_id");

				// maak een nieuw object met aan met de opgehaalde gegevens
				Score newScore = new Score(scoreId, score, leerlingnummer, quizNaam, datum, quizId);
				// voeg het object toe aan de ArrayList
				scores.add(newScore);

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		// geef de ArrayList terug
		return scores;
	}

	// haal alle scores uit de database
	public List<Score> findAll() {
		return selectScores("SELECT * FROM score");
	}

	// haal alle scores uit de database waar het leerlingnummer gelijk is aan
	// het opgegeven nummer
	public List<Score> findByLeerlingnummer(int leerlingnummer) {
		return selectScores(
				"SELECT score_id, score, leerlingnummer, quiz_naam, to_char(datum, 'DD-MM-YYYY') as datum, quiz_id FROM score WHERE leerlingnummer="
						+ leerlingnummer + " ORDER BY datum DESC");
	}

	// haal alle scores op waar het id gelijk is aan het opgegeven id en geef
	// alleen het eerster resultaat uide ArrayList terug
	public Score findById(int id) {
		return selectScores("SELECT * FROM score WHERE score_id=" + id).get(0);
	}

	// haal alle scores uit de database waar het quiz_id gelijk is aan het
	// opgegeven id
	public List<Score> findByQuizId(int id) {
		return selectScores(
				"SELECT score_id, score, leerlingnummer, quiz_naam, to_char(datum, 'DD-MM-YYYY') as datum, quiz_id FROM score WHERE quiz_id="+ id);
	}

	public boolean deleteScore(Score score) {
		boolean result = false;
		// kijk of de opgegeven score echt bestaat
		boolean scoreExist = findById(score.getId()) != null;

		// als de score bestaat voer dan de volgende functies uit
		if (scoreExist) {
			// maak de query aan en verwijder alle rijen waar het opgegven id
			// gelijk is aan het score_id
			String query = "DELETE FROM score WHERE id=" + score.getId();

			// maak verbinding met de database via de BaseDao
			try (Connection conn = super.getConnection()) {
				Statement stmt = conn.createStatement();
				// als de database 1 terug geeft is er een rij verwijderd dus
				// zetten we het resultaat op true
				if (stmt.executeUpdate(query) == 1) {
					result = true;
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		// geef het resultaat terug
		return result;
	}

	public boolean insertScore(Score score) {
		boolean result = false;

		// maak verbinding met de database via de BaseDao
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			// maak de query aan en voeg de opgegeven gegevens toe aan de
			// database
			String query = "INSERT INTO score (score, leerlingnummer, quiz_naam, datum, quiz_id) VALUES ('"
					+ score.getScore() + "', '" + score.getLeerlingnummer() + "', '" + score.getQuizNaam() + "', '"
					+ score.getDatum() + "', '" + score.getQuizId() + "')";
			int i = stmt.executeUpdate(query);

			// als i 1 is dan is er een rij toegevoegd dus zetten we het
			// resultaat op true
			if (i == 1) {
				result = true;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		// geef het resultaat terug
		return result;
	}
}
