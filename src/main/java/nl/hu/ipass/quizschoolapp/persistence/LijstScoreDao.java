package nl.hu.ipass.quizschoolapp.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import nl.hu.ipass.quizschoolapp.webservices.LijstScore;

public class LijstScoreDao extends BaseDao {
	public List<LijstScore> selectScores(String query) {
		List<LijstScore> scores = new ArrayList<LijstScore>();

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
				String lijstNaam = rs.getString("lijst_naam");
				String datum = rs.getString("datum");

				// maak een nieuw object aan met de opgehaalde gegevens
				LijstScore newScore = new LijstScore(scoreId, score, leerlingnummer, lijstNaam, datum);
				// voeg de gemaakte objecten toe aan de ArrayList
				scores.add(newScore);

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		// geef de ArrayList terug
		return scores;
	}

	// haal alle scores op die het leerlingnummer hebben dat is opgegeven
	public List<LijstScore> findByLeerlingnummer(int nummer) {
		return selectScores("SELECT score_id, score, leerlingnummer, lijst_naam, to_char(datum, 'DD-MM-YYYY') as datum FROM woordenlijstscore WHERE leerlingnummer="+ nummer + " ORDER BY datum DESC");
	}

	public boolean insertScore(LijstScore score) {
		boolean result = false;
		// maak verbinding met de database via de BaseDao
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			// maak de query aan en voeg de opgegeven gegevens toe aan de
			// database
			String query = "INSERT INTO woordenlijstscore (score, leerlingnummer, lijst_naam, datum) VALUES ('"
					+ score.getScore() + "', '" + score.getLeerlingnummer() + "', '" + score.getLijstNaam() + "', '"
					+ score.getDatum() + "')";
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
