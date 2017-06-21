package nl.hu.ipass.quizschoolapp.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import nl.hu.ipass.quizschoolapp.webservices.WoordenLijst;

public class WoordenLijstDao extends BaseDao {
	public List<WoordenLijst> selectWoordenLijsten(String query) {
		List<WoordenLijst> lijsten = new ArrayList<WoordenLijst>();

		// maak verbinding met de database via de BaseDao
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				// voor elk resultaat in de ResultSet haal de volgende gegevens
				// op
				int id = rs.getInt("lijst_id");
				String naam = rs.getString("naam");
				boolean openbaar = rs.getBoolean("openbaar");
				String taal1 = rs.getString("taal1");
				String taal2 = rs.getString("taal2");
				int leerlingnummer = rs.getInt("leerlingnummer");

				// maak een nieuw object aan met de opgehaalde gegevens
				WoordenLijst newWoordenLijst = new WoordenLijst(id, naam, openbaar, taal1, taal2, leerlingnummer);
				// voeg het object toe aan de ArrayList
				lijsten.add(newWoordenLijst);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		// geef de ArrayList terug
		return lijsten;
	}

	// haal alle woordenlijsten op uit de database
	public List<WoordenLijst> findAll() {
		return selectWoordenLijsten("SELECT * FROM woordenlijst");
	}

	// haal alle woordenlijsten op uit de database waar openbaar gelijk is aan
	// true
	public List<WoordenLijst> findOpenbaar() {
		return selectWoordenLijsten("SELECT * FROM woordenlijst WHERE openbaar=true");
	}

	// haal alle woordenlijsten op waar het leerlingnummer gelijk is aan het
	// opgegeven nummer
	public List<WoordenLijst> findByLeerlingnummer(int leerlingnummer) {
		return selectWoordenLijsten("SELECT * FROM woordenlijst WHERE leerlingnummer=" + leerlingnummer);
	}

	// haal de woordenlijsten op waar het id gelijk is aan het opgegeven id en
	// geef alleen de eerste terug uit de ArrayList
	public WoordenLijst findByLijstId(int id) {
		return selectWoordenLijsten("SELECT * FROM woordenlijst WHERE lijst_id=" + id).get(0);
	}

	public boolean insertWoordenLijst(WoordenLijst lijst) {
		boolean result = false;

		// maak verbinding met de database via de BaseDao
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			// maak de query aan en voeg de opgegeven gegevens toe aan de
			// database
			String query = "INSERT INTO woordenlijst (naam, openbaar, taal1, taal2, leerlingnummer) VALUES ('"
					+ lijst.getNaam() + "', '" + lijst.isOpenbaar() + "', '" + lijst.getTaal1() + "', '"
					+ lijst.getTaal2() + "', " + lijst.getLeerlingnummer() + ")";
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

	public boolean deleteWoordenLijst(WoordenLijst lijst) {
		boolean result = false;
		// kijk of de opgegeven lijst bestaat
		boolean lijstExist = findByLijstId(lijst.getId()) != null;

		// als de lijst bestaat voer dan de volgende functies uit
		if (lijstExist) {
			// maak de query aan en verwijder alle woordenlijsten waar het id
			// gelijk is aan het opgegeven id
			String query = "DELETE FROM woordenlijst WHERE lijst_id=" + lijst.getId();

			// maak verbinding met de database via de BaseDao
			try (Connection conn = super.getConnection()) {
				Statement stmt = conn.createStatement();
				// als de database een 1 terug geeft dan is er een rijg
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
