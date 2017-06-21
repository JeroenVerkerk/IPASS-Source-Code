package nl.hu.ipass.quizschoolapp.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import nl.hu.ipass.quizschoolapp.webservices.*;

public class LeerlingDao extends BaseDao {
	private AccountDao accountDao = new AccountDao();
	private KlasDao klasDao = new KlasDao();

	public List<Leerling> selectLeerlingen(String query) {
		List<Leerling> leerlingen = new ArrayList<Leerling>();

		// maak verbinding met de database via de BaseDao
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				// voor elk resultaat in de ResultSet haal de volgende gegevens
				// op
				int leerlingnummer = rs.getInt("leerlingnummer");
				String klasCode = rs.getString("klascode");
				String profiel = rs.getString("profiel");
				String niveau = rs.getString("niveau");
				String naam = rs.getString("naam");
				String achternaam = rs.getString("achternaam");
				String geboortedatum = rs.getString("geboortedatum");
				String postcode = rs.getString("postcode");
				String woonplaats = rs.getString("woonplaats");
				String inlognaam = rs.getString("inlognaam");

				// maak een nieuw object aan met de opgehaalde gegevens
				Leerling newLeerling = new Leerling(leerlingnummer, klasCode, profiel, niveau, naam, achternaam,
						geboortedatum, postcode, woonplaats, inlognaam);
				// voeg aangemaakte objecten toe aan de ArrayList
				leerlingen.add(newLeerling);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		// geef de ArrayList terug
		return leerlingen;
	}

	// haal alle leerlingen op uit de database
	public List<Leerling> findAll() {
		return selectLeerlingen("SELECT * FROM leerling");
	}

	// haal alle leerlingen op die het nummer hebben dat is opgegeven en geef
	// het eerste resultaat uit de ArrayList terug
	public Leerling findByLeerlingnummer(int nummer) {
		return selectLeerlingen("SELECT * FROM leerling WHERE leerlingnummer=" + nummer).get(0);
	}

	// haal alle leerlingen die de inlognaam hebben die is meegegeven en geef
	// alleen het eerste resultaat uit de ArrayList terug
	public Leerling findByInlognaam(String naam) {
		return selectLeerlingen("SELECT * FROM leerling WHERE inlognaam='" + naam + "'").get(0);
	}

	public boolean updateLeerling(Leerling leerling) {
		boolean result = false;

		// maak verbinding met de database via de BaseDao
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			// maak de query aan en update de gegevens waar het leerlingnummer
			// gelijk is aan het meegegeven leerlingnummer
			String query = "UPDATE leerling SET klascode='" + leerling.getKlascode() + "', profiel='"
					+ leerling.getProfiel() + "', niveau='" + leerling.getNiveau() + "', naam='" + leerling.getNaam()
					+ "', achternaam='" + leerling.getAchternaam() + "', geboortedatum='" + leerling.getGeboortedatum()
					+ "', postcode='" + leerling.getPostcode() + "' WHERE leerlingnummer="
					+ leerling.getLeerlingnummer();
			int i = stmt.executeUpdate(query);

			// als i 1 is dan is er een rij geüpdate dus zetten we resultaat op
			// true
			if (i == 1) {
				result = true;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		// geef het resultaat terug
		return result;
	}

	public boolean deleteLeerling(Leerling leerling) {
		boolean result = false;
		boolean leerlingExist = findByLeerlingnummer(leerling.getLeerlingnummer()) != null;

		if (leerlingExist) {
			// maak de query aan en verwijder alle leerlingen die het
			// leerlingnummer hebben dat is meegegeven
			String query = "DELETE FROM leerling WHERE leerlingnummer=" + leerling.getLeerlingnummer();

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

	public boolean insertLeerling(Leerling leerling) {
		boolean result = false;

		// maak verbinding met de database via de BaseDao
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			// maak de query aan en voeg de meegegeven waardes toe aan de
			// database
			String query = "INSERT INTO leerling (leerlingnummer, klascode, profiel, niveau, naam, achternaam, geboortedatum, postcode, woonplaats, inlognaam) VALUES ('"
					+ leerling.getLeerlingnummer() + "', '" + leerling.getKlascode() + "', '" + leerling.getProfiel()
					+ "', '" + leerling.getNiveau() + "', '" + leerling.getNaam() + "', '" + leerling.getAchternaam()
					+ "', '" + leerling.getGeboortedatum() + "', '" + leerling.getPostcode() + "', '"
					+ leerling.getWoonplaats() + "', '" + leerling.getInlognaam() + "')";
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
