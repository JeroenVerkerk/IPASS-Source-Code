package nl.hu.ipass.quizschoolapp.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import nl.hu.ipass.quizschoolapp.webservices.Account;

public class AccountDao extends BaseDao {
	public List<Account> selectAccounts(String query) {
		List<Account> accounts = new ArrayList<Account>();

		// get de connectie vanuit de BaseDao
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			// execute de query die opgegeven wordt
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				// voor elk resultaat in rs haal de volgende gegevens op
				String inlognaam = rs.getString("inlognaam");
				String role = rs.getString("role");
				String wachtwoord = rs.getString("wachtwoord");

				// voor elk resultaat maak een nieuw object aan
				Account newAccount = new Account(inlognaam, role, wachtwoord);
				// voeg het object dat zojuist is aangemaakt toe aan de
				// arraylist
				accounts.add(newAccount);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return accounts;
	}

	// haal alle gegevens uit de tabel useraccount
	public List<Account> findAll() {
		return selectAccounts("SELECT * FROM useraccount");
	}

	// haal alle gegevens uit de database waar de inlognaam is gelijk aan de
	// meegegeven inlognaam
	public Account findByInlognaam(String naam) {
		return selectAccounts("SELECT * FROM useraccount WHERE inlognaam='" + naam + "'").get(0);
	}

	public boolean updateAccount(Account account) {
		// zet het resulaat op false
		boolean result = false;
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			// maak de query aan en geef de waardes mee die moeten worden
			// geüpdate
			String query = "UPDATE useraccount SET wachtwoord='" + account.getWachtwoord() + "', role='"
					+ account.getRole() + "' WHERE inlognaam='" + account.getInlognaam() + "'";
			int i = stmt.executeUpdate(query);

			// als i 1 is dan is er een column in de database gewijzigd dus
			// zetten we het resultaat op true
			if (i == 1) {
				result = true;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		// geef the resultaat terug
		return result;
	}

	public boolean deleteAccount(Account account) {
		// zet het resultaat op false
		boolean result = false;
		// kijk of de meegegeven account daadwerkelijk bestaat
		boolean accountExist = findByInlognaam(account.getInlognaam()) != null;

		// als het account echt bestaat ga dan door met de volgende functies
		// anders gelijk naar het return statement
		if (accountExist) {
			// maak de query aan en delete de rij in de database waar de
			// inlognaam gelijk is aan de meegegeven inlognaam
			String query = "DELETE FROM useraccount WHERE inlognaam='" + account.getInlognaam() + "'";

			// haal de connectie uit de BaseDao
			try (Connection conn = super.getConnection()) {
				Statement stmt = conn.createStatement();
				// als de query een 1 terugn geeft dan betekend het dat er een
				// rij gewijzigd is dus zetten we het resultaat op true
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

	public boolean insertAccount(Account account) {
		// zet het resultaat op false
		boolean result = false;

		// haal de verbinding uit de BaseDao
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			// maak de query aan in voeg de meegegeven gegevens toe aan de
			// database
			String query = "INSERT INTO useraccount (inlognaam, role, wachtwoord) VALUES ('" + account.getInlognaam()
					+ "','" + account.getRole() + "','" + account.getWachtwoord() + "')";
			int i = stmt.executeUpdate(query);

			// als i 1 is dan is er een rij toegevoegd in de database dus zetten
			// we het resultaat op true
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