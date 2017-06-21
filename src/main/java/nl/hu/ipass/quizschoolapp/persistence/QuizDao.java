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

		// maak verbinding met de database via de BaseDao
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				// voor elk resultaat in de ResultSet haal de voglende gegevens
				// op
				int id = rs.getInt("quiz_id");
				String vak = rs.getString("vak");
				String niveau = rs.getString("niveau");
				String gemaaktDoor = rs.getString("gemaakt_door");
				String naam = rs.getString("naam");

				// maak een nieuwe object aan met de opgehaalde gegevens
				Quiz newQuiz = new Quiz(id, vak, niveau, gemaaktDoor, naam);
				// voeg het object toe aan de ArrayList
				allQuizes.add(newQuiz);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		// geef de ArrayList terug
		return allQuizes;
	}

	// haal alle quizzen op uit de database
	public List<Quiz> findAll() {
		return selectQuizes("SELECT * FROM quiz");
	}

	// haal alle quizzen op met het opgegeven id en geef alleen het eerste
	// resultaat uit de ArrayList terug
	public Quiz findById(int id) {
		return selectQuizes("SELECT * FROM quiz WHERE quiz_id = " + id).get(0);
	}

	// haal alle quizzen uit de database met het opgegeven vak en niveau
	public List<Quiz> findOverzicht(String vak, String niveau) {
		return selectQuizes("SELECT * FROM quiz WHERE vak='" + vak + "' AND niveau='" + niveau + "'");
	}

	// haal alle quizzen uit de database met het opgegeven docent
	public List<Quiz> findByDocent(String docent) {
		return selectQuizes("SELECT * FROM quiz WHERE gemaakt_door='" + docent + "'");
	}

	public boolean update(Quiz quiz) {
		boolean result = false;

		// maak verbinding met de database via de BaseDao
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			// maak de query aan en update de opgegeven gegevens waar het id
			// gelijk is aan het opgegeven id
			String query = "UPDATE quiz SET vak='" + quiz.getVak() + "', niveau='" + quiz.getNiveau()
					+ "', gemaakt_door='" + quiz.getGemaaktDoor() + "', naam='" + quiz.getNaam() + "' WHERE quiz_id="
					+ quiz.getId();
			int i = stmt.executeUpdate(query);

			// als i 1 is dan is er een rij gewijzigd dus zetten we het
			// resultaat op true
			if (i == 1) {
				result = true;
				;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		// geef het resultaat terug
		return result;
	}

	public boolean deleteQuiz(Quiz quiz) {
		boolean result = false;
		// kijk of de opgegeven quiz bestaat
		boolean quizExist = findById(quiz.getId()) != null;

		// als de quiz bestaat voer dan de volgende functies uit
		if (quizExist) {
			// maak de query aan en verwijder de rijen waar het id gelijk is aan
			// het opgegeven id
			String query = "DELETE FROM quiz WHERE quiz_id=" + quiz.getId();

			// maak verbinding met de database via de BaseDao
			try (Connection conn = super.getConnection()) {
				Statement stmt = conn.createStatement();
				// als de database 1 terug geeft dan is er een rij verwijderd
				// dus zetten we het resultaat op true
				if (stmt.executeUpdate(query) == 1) {
					result = true;
				}
				;
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		// geef het resultaat terug
		return result;
	}

	public boolean insertQuiz(Quiz quiz) {
		boolean result = false;

		// maak verbinding met de database via de BaseDao
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			// maak de query aan en voeg de oppegegeven gegevens toe aan de
			// database
			String query = "INSERT INTO quiz (vak, niveau, gemaakt_door, naam) VALUES ('" + quiz.getVak() + "','"
					+ quiz.getNiveau() + "','" + quiz.getGemaaktDoor() + "','" + quiz.getNaam() + "')";
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