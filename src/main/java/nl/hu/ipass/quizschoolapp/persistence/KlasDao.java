package nl.hu.ipass.quizschoolapp.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import nl.hu.ipass.quizschoolapp.webservices.Klas;

public class KlasDao extends BaseDao {
	public List<Klas> selectKlassen(String query) {
		List<Klas> klassen = new ArrayList<Klas>();

		// maak de verbinding met de database via de BaseDao
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				// voor elk resultaat in de ResultSet haal de volgende gegevens
				// op
				String klascode = rs.getString("klascode");
				String niveau = rs.getString("niveau");

				// maak voor elk resultaat een nieuw object aan met de gegeven
				// die opgehaald zijn
				Klas newKlas = new Klas(klascode, niveau);
				// voeg het object toe aan de ArrayList
				klassen.add(newKlas);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		// geef de ArrayList terug
		return klassen;
	}

	// haal alle klassen op uit de database
	public List<Klas> findAll() {
		return selectKlassen("SELECT * FROM klas");
	}

	// haal alle klassen waar de code gelijk is aan de opgegeven code en geef
	// alleen de eerste uit de ArrayList terug
	public Klas findByKlasCode(String code) {
		return selectKlassen("SELECT * FROM klas WHERE klascode='" + code + "'").get(0);
	}

	public boolean updateKlas(Klas klas) {
		boolean result = false;

		// maak verbinding met de database via de BaseDao
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			// maak de query aan en update het niveau waar de klascode gelijk is
			// aan de opgegeven code
			String query = "UPDATE klas SET niveau='" + klas.getNiveau() + "' WHERE klascode='" + klas.getKlascode()
					+ "'";
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

	public boolean deleteKlas(Klas klas) {
		boolean result = false;
		// kijk of de opgegeven klas echt bestaat
		boolean klasExist = findByKlasCode(klas.getKlascode()) != null;

		// als de klas bestaat voer dan de volgende functie uit
		if (klasExist) {
			// maak de query aan en verwijder de rijen waar de klascode gelijk
			// is aan de opgegeven klascode
			String query = "DELETE FROM klas WHERE klascode=" + klas.getKlascode();

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

	public boolean insertKlas(Klas klas) {
		boolean result = false;

		// maak verbdinding met de database via de BaseDao
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			// maak de query aan en voeg de opgegeven gegevens toe aan de
			// database
			String query = "INSERT INTO klas (klascode, niveau) VALUES ('" + klas.getKlascode() + "','"
					+ klas.getNiveau() + "')";
			int i = stmt.executeUpdate(query);

			// als i 1 is dan is er en rij toegevoegd dus zetten we het
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
