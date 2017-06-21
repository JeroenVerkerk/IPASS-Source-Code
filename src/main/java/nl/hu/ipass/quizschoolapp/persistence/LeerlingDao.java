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
		
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				int leerlingnummer = rs.getInt("leerlingnummer");
				String klasCode = rs.getString("klascode");
				String profiel = rs.getString("profiel");
				String niveau = rs.getString("niveau");
				String naam = rs.getString("naam");
				String achternaam = rs.getString("achternaam");
				String geboortedatum = rs.getString("geboortedatum");
				String postcode = rs.getString("postcode");
				String woonplaats =  rs.getString("woonplaats");
				String inlognaam = rs.getString("inlognaam");
				Account inlogn = (Account) accountDao.findByInlognaam(inlognaam);
				Klas klas = (Klas) klasDao.findByKlasCode(klasCode);
				
				Leerling newLeerling = new Leerling(leerlingnummer, profiel, niveau, naam, achternaam, geboortedatum, postcode, woonplaats);
				newLeerling.setKlascode(klas);
				newLeerling.setInlognaam(inlogn);
				leerlingen.add(newLeerling);
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return leerlingen;
	}
	
	public List<Leerling> findAll() {
		return selectLeerlingen("SELECT * FROM leerling");
	}
	
	public Leerling findByLeerlingnummer(int nummer) {
		return selectLeerlingen("SELECT * FROM leerling WHERE leerlingnummer="+nummer).get(0);
	}
	
	public Leerling findByInlognaam(String naam) {
		return selectLeerlingen("SELECT * FROM leerling WHERE inlognaam='"+naam+"'").get(0);
	}
	
	public boolean updateLeerling(Leerling leerling) {
		boolean result = false;
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			String query = "UPDATE leerling SET klascode='"+leerling.getKlascode()+"', profiel='"+leerling.getProfiel()+"', niveau='"+leerling.getNiveau()+"', naam='"+leerling.getNaam()+"', achternaam='"+leerling.getAchternaam()+"', geboortedatum='"+leerling.getGeboortedatum()+"', postcode='"+leerling.getPostcode()+"'";
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
	
	public boolean deleteLeerling(Leerling leerling) {
		boolean result = false;
		boolean klasExist = findByLeerlingnummer(leerling.getLeerlingnummer()) != null;
		
		if (klasExist) {
			String query = "DELETE FROM leerling WHERE leerling="+ leerling.getLeerlingnummer();
			
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
	
	public boolean insertLeerling(Leerling leerling) {
		boolean result = false;
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO leerling (leerlingnummer, klascode, profiel, niveau, naam, achternaam, geboortedatum, postcode, woonplaats, inlognaam) VALUES ('"+leerling.getLeerlingnummer()+"', '"+leerling.getKlascode()+"', '"+leerling.getProfiel()+"', '"+leerling.getNiveau()+"', '"+leerling.getNaam()+"', '"+leerling.getAchternaam()+"', '"+leerling.getGeboortedatum()+"', '"+leerling.getPostcode()+"', '"+leerling.getWoonplaats()+"', '"+leerling.getInlognaam()+"')";
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

