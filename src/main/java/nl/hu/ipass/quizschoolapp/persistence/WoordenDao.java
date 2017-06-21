package nl.hu.ipass.quizschoolapp.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import nl.hu.ipass.quizschoolapp.webservices.Woorden;

public class WoordenDao extends BaseDao {
	public List<Woorden> selectWoorden(String query) {
		List<Woorden> woorden = new ArrayList<Woorden>();

		// maak verbinding met de database via de BaseDao
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				// haal voor elk resultaat in de ResultSet de volgende gegvens
				// op
				int id = rs.getInt("woord_id");
				String taal1Woord = rs.getString("taal1_woord");
				String taal2Woord = rs.getString("taal2_woord");
				int lijstId = rs.getInt("lijst_id");

				// maak een nieuw object aan met opgehaalde gegevens
				Woorden newWoorden = new Woorden(id, taal1Woord, taal2Woord, lijstId);
				// voeg het object toe aan de ArrayList
				woorden.add(newWoorden);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		// geef de ArrayList terug
		return woorden;
	}

	// haal alle woorden uit de database
	public List<Woorden> findAll() {
		return selectWoorden("SELECT * FROM woorden");
	}

	// haal alle woorden uit de database waar lijst_id is gelijk aan het
	// opgegeven id
	public List<Woorden> findByLijstId(int id) {
		return selectWoorden("SELECT * FROM woorden WHERE lijst_id=" + id);
	}

	// haal alle woorden uit de database waar lijst_id is gelijk aan het
	// opgegeven id alleen draai taal1 en taal2 om
	public List<Woorden> findByLijstId2(int id) {
		return selectWoorden(
				"SELECT woord_id, taal1_woord as taal2_woord, taal2_woord as taal1_woord, lijst_id FROM woorden WHERE lijst_id="
						+ id);
	}

	// haal alle woorden uit de database waar het id gelijk is aan het opgegeven
	// id en geef alleen het eerste resultaat terug uit de ArrayList
	public Woorden findById(int id) {
		return selectWoorden("SELECT * FROM woorden WHERE woord_id=" + id).get(0);
	}

	public boolean insertWoorden(Woorden woorden) {
		boolean result = false;

		// maak verbinding met de database via de BaseDao
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			// maak de query aan en voeg de opgegeven gegevens toe aan de
			// database
			String query = "INSERT INTO woorden (taal1_woord, taal2_woord, lijst_id) SELECT '" + woorden.getTaal1Woord()
					+ "', '" + woorden.getTaal2Woord() + "', last_value FROM woordenlijst_lijst_id_seq";
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

	public boolean deleteWoorden(Woorden woorden) {
		boolean result = false;
		// kijk of het opgegeven woord bestaat
		boolean woordenExist = findById(woorden.getWoordId()) != null;

		// als het opgegeven woord bestaat voer dan de volgende functies uit
		if (woordenExist) {
			// maak de query aan en verwijder de woorden met het id die gelijk
			// is aan het opgegeven id
			String query = "DELETE FROM woorden WHERE woord_id=" + woorden.getWoordId();

			// maak verbinding met de database via de BaseDao
			try (Connection conn = super.getConnection()) {
				Statement stmt = conn.createStatement();
				// als de database een 1 terug geeft dan is er een rij
				// verwijderd dus zetten we het resultaat op true
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
}
