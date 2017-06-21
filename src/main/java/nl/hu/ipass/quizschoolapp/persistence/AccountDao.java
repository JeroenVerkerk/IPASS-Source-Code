package nl.hu.ipass.quizschoolapp.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import nl.hu.ipass.quizschoolapp.webservices.Account;
import nl.hu.ipass.quizschoolapp.webservices.Klas;
import nl.hu.ipass.quizschoolapp.webservices.Quiz;


public class AccountDao extends BaseDao{
	public List<Account> selectAccounts(String query) {
		List<Account> accounts = new ArrayList<Account>();
		
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				String inlognaam = rs.getString("inlognaam");
				String role = rs.getString("role");
				String wachtwoord = rs.getString("wachtwoord");

				Account newAccount = new Account(inlognaam, role, wachtwoord);
				accounts.add(newAccount);
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return accounts;
	}
	
	public List<Account> findAll() {
		return selectAccounts("SELECT * FROM useraccount");
	}
	
	public Account findByInlognaam(String naam) {
		return selectAccounts("SELECT * FROM useraccount WHERE inlognaam='"+naam+"'").get(0);
	}
	
	public boolean updateAccount(Account account) {
		boolean result = false;
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			String query = "UPDATE useraccount SET wachtwoord='"+account.getWachtwoord()+"', role='"+account.getRole()+"' WHERE inlognaam='"+account.getInlognaam()+"'";
			int i = stmt.executeUpdate(query);
			
			if (i == 1) {
				result = true;
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return result;
	}
	
	public boolean deleteAccount(Account account) {
		boolean result = false;
		boolean klasExist = findByInlognaam(account.getInlognaam()) != null;
		
		if (klasExist) {
			String query = "DELETE FROM useraccount WHERE inlognaam='"+account.getInlognaam()+"'";
			
			try (Connection conn = super.getConnection()) {
				Statement stmt = conn.createStatement();
				if (stmt.executeUpdate(query) == 1) {
					result = true;
				};
			}
			catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		return result;
	}
	
	public boolean insertAccount(Account account) {
		boolean result = false;
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO account (inlognaam, role, wachtwoord) VALUES ('"+account.getInlognaam()+"','"+account.getRole()+"','"+account.getWachtwoord()+"')";
			int i = stmt.executeUpdate(query);
			
			if (i == 1) {
				result = true;
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return result;
	}
}