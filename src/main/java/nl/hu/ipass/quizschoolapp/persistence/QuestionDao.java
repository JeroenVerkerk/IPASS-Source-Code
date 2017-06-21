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

		// maak verbinding met de database via de BaseDao
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				// voor elk resultaat in de ResultSet haal de volgende gegevens
				// op
				int id = rs.getInt("question_id");
				String question = rs.getString("question");
				int quizId = rs.getInt("quiz_id");
				Quiz quiz = (Quiz) quizDao.findById(quizId);

				// maak me de opgehaalde gegevens een nieuw object aan
				Question newQuestion = new Question(id, question);
				newQuestion.setQuizId(quiz);
				// voeg het gemaakte object toe aan de ArrayList
				questions.add(newQuestion);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		// geef de ArrayList terug
		return questions;
	}

	// haal alle vragen op uit de database
	public List<Question> findAll() {
		return selectQuestions("SELECT * FROM question");
	}

	// haal alle vragen op die het id hebben dat is opgegeven en geef alleen het
	// eerste resultaat terug uit de ArrayList
	public Question findById(int id) {
		return selectQuestions("SELECT * FROM question WHERE question_id=" + id).get(0);
	}

	// haal alle vragen uit de database die het quiz_id hebben dat gelijk is aan
	// het opgegeven id
	public List<Question> findByQuizId(int id) {
		return selectQuestions("SELECT * FROM question WHERE quiz_id=" + id);
	}

	public boolean updateQuestion(Question question) {
		boolean result = false;

		// maak verbinding met de database via de BaseDao
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			// maak de query aan en update de opgegeven gegevens in de database
			// waar question_id gelijk is aan het opgegeven id
			String query = "UPDATE question SET question='" + question.getQuestion() + "' WHERE question_id="
					+ question.getId();
			int i = stmt.executeUpdate(query);

			// als i 1 is dan is er een rij geüpdate dus zetten we het resultaat
			// op true
			if (i == 1) {
				result = true;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		// geef het resultaat terug
		return result;
	}

	public boolean deleteQuestion(Question question) {
		boolean result = false;
		// kijk of de opgegeven vraag echt bestaat
		boolean questionExist = findById(question.getId()) != null;

		// als de vraag bestaat voer de volgende gegevens uit
		if (questionExist) {
			// maak de query aan en verwijder de rijen uit de database waar het
			// id gelijk is aan het opgegeven id
			String query = "DELETE FROM question WHERE id=" + question.getId();

			// maak verbinding met de database via BaseDao
			try (Connection conn = super.getConnection()) {
				Statement stmt = conn.createStatement();
				// als de database 1 terug geeft dan is er en rij verwijderd dus
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

	public boolean insertQuestion(Question question) {
		boolean result = false;

		// maak verbinding met de database via de BaseDao
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			// maak de query aan en voeg de opgegeven gegevens aan de database
			// toe
			String query = "INSERT INTO question (question, quiz_id) SELECT '" + question.getQuestion()
					+ "', last_value from quiz_quiz_id_seq";
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
