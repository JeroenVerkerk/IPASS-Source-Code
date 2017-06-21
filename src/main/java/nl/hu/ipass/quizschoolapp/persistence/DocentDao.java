package nl.hu.ipass.quizschoolapp.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import nl.hu.ipass.quizschoolapp.webservices.*;


public class DocentDao extends BaseDao {
	private AccountDao accountDao = new AccountDao();
	
	public List<Docent> selectDocenten(String query) {
		List<Docent> docenten = new ArrayList<Docent>();
		
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				int docentnummer = rs.getInt("docentnummer");
				String naam = rs.getString("naam");
				String achternaam = rs.getString("achternaam");
				String postcode = rs.getString("postcode");
				String woonplaats= rs.getString("woonplaats");
				String niveau= rs.getString("niveau");
				String geboortedatum = rs.getString("geboortedatum");
				String inlognaam = rs.getString("inlognaam");
				Account inlogn = (Account) accountDao.findByInlognaam(inlognaam);
				
				Docent newDocent = new Docent(docentnummer, naam, achternaam, postcode, woonplaats, niveau, geboortedatum);
				newDocent.setInlognaam(inlogn);
				docenten.add(newDocent);
				

			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return docenten;
	}
	
	public List<Docent> findAll() {
		return selectDocenten("SELECT * FROM docent");
	}
	
	public Docent findByDocentnummer(int nummer) {
		return selectDocenten("SELECT * FROM docent WHERE docentnummer="+nummer).get(0);
	}
	
	public Docent findByInlognaam(String naam) {
		return selectDocenten("SELECT * FROM docent WHERE inlognaam='"+naam+"'").get(0);
	}
	
	public boolean updateDocent(Docent docent) {
		boolean result = false;
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			String query = "UPDATE docent SET naam='"+docent.getNaam()+"', achternaam='"+docent.getAchternaam()+"', postcode='"+docent.getPostcode()+"', woonplaats='"+docent.getWoonplaats()+"', niveau='"+docent.getNiveau()+"', geboortedatum='"+docent.getGeboortedatum()+"' WHERE docentnummer="+docent.getDocentnummer();
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
	
	public boolean deleteDocent(Docent docent) {
		boolean result = false;
		boolean docentExist = findByDocentnummer(docent.getDocentnummer()) != null;
		
		if (docentExist) {
			String query = "DELETE FROM leerling WHERE docentnummer="+ docent.getDocentnummer();
			
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
	
	public boolean insertDocent(Docent docent) {
		boolean result = false;
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO docent (leerlingnummer, naam, achternaam, postcode, woonplaats, niveau, geboortedatum, inlognaam) VALUES ('"+docent.getDocentnummer()+"', '"+docent.getNaam()+"', '"+docent.getPostcode()+"', '"+docent.getWoonplaats()+"', '"+docent.getNiveau()+"', '"+docent.getGeboortedatum()+"', '"+docent.getInlognaam()+"')";
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

