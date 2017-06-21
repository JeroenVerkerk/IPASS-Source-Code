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

		// haal de verbinding op met de BaseDao
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			// maak voor de query een ResultSet aan
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				// haal voor elk resultaat in de ResultSet de volgende gegevens
				// op
				int id = rs.getInt("id");
				String answer = rs.getString("answer");
				boolean isCorrect = rs.getBoolean("is_correct");
				int questionId = rs.getInt("question_id");
				Question question = (Question) questionDao.findById(questionId);

				// maak voor elke resultaat een nieuwe object aan
				Answer newAnswer = new Answer(id, answer, isCorrect);
				// geeft het questionId aan
				newAnswer.setQuestionId(question);
				// voeg het object dat is aangemaakt toe aan de ArrayList
				answers.add(newAnswer);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		// geef de ArrayList terug
		return answers;
	}

	// selecteer alle gegevens uit de database
	public List<Answer> findAll() {
		return selectAnswers("SELECT * FROM answers");
	}

	// selecteer alle gegevens die het question_id hebben dat is meegegeven
	public List<Answer> findById(int id) {
		return selectAnswers("SELECT * FROM answers WHERE question_id=" + id);
	}

	public boolean updateAnswer(Answer answer) {
		boolean result = false;
		// maak de verbinding in de BaseDao
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			// maak de query aan en update de gegevens met de waarde die is
			// meegegeven
			String query = "UPDATE answers SET answer=" + answer.getAnswer();
			int i = stmt.executeUpdate(query);

			// als i i is dan is er een rij gewijzigd dus zetten we het
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

	public boolean deleteAnswer(Answer answer) {
		boolean result = false;
		// kijk of het meegegeven antwoord daadwerkelijk bestaat
		boolean answerExist = findById(answer.getId()) != null;

		// als het antwoord bestaat voer dan de volgende functies uit
		if (answerExist) {
			// maak de query aan en delete de antwoorden die het id hebben dat
			// is meegegeven
			String query = "DELETE FROM answers WHERE id=" + answer.getId();

			// maak verbinding met de database via de BaseDao
			try (Connection conn = super.getConnection()) {
				Statement stmt = conn.createStatement();
				// als de database 1 terug geeft dan is een rij verwijderd dus
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

	public boolean insertAnswer(Answer answer) {
		boolean result = false;

		// maak verbinding met de database via de BaseDao
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			// maak de query aan en voer de meegegeven gegevens in in de
			// database
			String query = "INSERT INTO answers (answer, is_correct, question_id) SELECT '" + answer.getAnswer() + "','"
					+ answer.getIsCorrect() + "', last_value from question_question_id_seq";
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
