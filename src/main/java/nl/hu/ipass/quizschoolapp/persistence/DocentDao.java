package nl.hu.ipass.quizschoolapp.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import nl.hu.ipass.quizschoolapp.webservices.*;

public class DocentDao extends BaseDao {

	public List<Docent> selectDocenten(String query) {
		List<Docent> docenten = new ArrayList<Docent>();

		// maak de verbinding met de database via de BaseDao
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				// voor elk resultaat in de ResultSet haal de volgende gegevens
				// op
				int docentnummer = rs.getInt("docentnummer");
				String naam = rs.getString("naam");
				String achternaam = rs.getString("achternaam");
				String postcode = rs.getString("postcode");
				String woonplaats = rs.getString("woonplaats");
				String niveau = rs.getString("niveau");
				String geboortedatum = rs.getString("geboortedatum");
				String inlognaam = rs.getString("inlognaam");

				// maak met de opgehaalde gegevens een nieuw object aan
				Docent newDocent = new Docent(docentnummer, naam, achternaam, postcode, woonplaats, niveau,
						geboortedatum, inlognaam);
				// voeg dit object toe aan de ArrayList
				docenten.add(newDocent);

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		// geef de ArrayList terug
		return docenten;
	}

	// haal alle gegevens uit de database van de tabel docent
	public List<Docent> findAll() {
		return selectDocenten("SELECT * FROM docent");
	}

	// haal alle docenten op met het opegegeven nummer en geef alleen de eerste
	// uit de ArrayList terug
	public Docent findByDocentnummer(int nummer) {
		return selectDocenten("SELECT * FROM docent WHERE docentnummer=" + nummer).get(0);
	}

	// haal alle docenten op met het opgegeven inlognaam en geef alleen de
	// eerste uit de ArrayList terug
	public Docent findByInlognaam(String naam) {
		return selectDocenten("SELECT * FROM docent WHERE inlognaam='" + naam + "'").get(0);
	}

	public boolean updateDocent(Docent docent) {
		boolean result = false;
		// maak verbinding met de database via de BaseDao
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			// maak de query aan en update de colom met de meegegeven gegevens
			// waar het nummer gelijk is aan het meegegeven nummer
			String query = "UPDATE docent SET naam='" + docent.getNaam() + "', achternaam='" + docent.getAchternaam()
					+ "', postcode='" + docent.getPostcode() + "', woonplaats='" + docent.getWoonplaats()
					+ "', niveau='" + docent.getNiveau() + "', geboortedatum='" + docent.getGeboortedatum()
					+ "' WHERE docentnummer=" + docent.getDocentnummer();
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

	public boolean deleteDocent(Docent docent) {
		boolean result = false;
		// kijk of de opgegeven docent echt bestaat
		boolean docentExist = findByDocentnummer(docent.getDocentnummer()) != null;

		// als de opgegeven docent bestaat voer dan de volgende functies uit
		if (docentExist) {
			// maak de query aan en delete de docenten die het opgegeven nummer
			// hebben
			String query = "DELETE FROM docent WHERE docentnummer=" + docent.getDocentnummer();

			// maak verbinding met de database via de BaseDao
			try (Connection conn = super.getConnection()) {
				Statement stmt = conn.createStatement();
				// als de database een 1 terug geeft dan is er een rij
				// verwijderd dus zetten we het resultaat op true
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

	public boolean insertDocent(Docent docent) {
		boolean result = false;

		// maak verbinding met de database via de BaseDao
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			// maak de query aan en voer de meegegeven gegeven in in de database
			String query = "INSERT INTO docent (docentnummer, naam, achternaam, postcode, woonplaats, niveau, geboortedatum, inlognaam) VALUES ('"
					+ docent.getDocentnummer() + "', '" + docent.getNaam() + "', '" + docent.getAchternaam() + "', '"
					+ docent.getPostcode() + "', '" + docent.getWoonplaats() + "', '" + docent.getNiveau() + "', '"
					+ docent.getGeboortedatum() + "', '" + docent.getInlognaam() + "')";
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
