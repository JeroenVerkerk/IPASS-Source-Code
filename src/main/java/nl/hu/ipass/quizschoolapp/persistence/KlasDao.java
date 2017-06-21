package nl.hu.ipass.quizschoolapp.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import nl.hu.ipass.quizschoolapp.webservices.Klas;
import nl.hu.ipass.quizschoolapp.webservices.Question;
import nl.hu.ipass.quizschoolapp.webservices.Quiz;

public class KlasDao extends BaseDao {
	public List<Klas> selectKlassen(String query) {
		List<Klas> klassen = new ArrayList<Klas>();
		
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				String klascode = rs.getString("klascode");
				String niveau = rs.getString("niveau");
				
				Klas newKlas = new Klas(klascode, niveau);
				klassen.add(newKlas);
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return klassen;
	}
	
	public List<Klas> findAll() {
		return selectKlassen("SELECT * FROM klas");
	}
	
	public Klas findByKlasCode(String code) {
		return selectKlassen("SELECT * FROM klas WHERE klascode='"+code+"'").get(0);
	}
	
	public boolean updateKlas(Klas klas) {
		boolean result = false;
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			String query = "UPDATE klas SET klascode='"+klas.getKlascode()+"', niveau='"+klas.getNiveau()+"'";
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
	
	public boolean deleteKlas(Klas klas) {
		boolean result = false;
		boolean klasExist = findByKlasCode(klas.getKlascode()) != null;
		
		if (klasExist) {
			String query = "DELETE FROM klas WHERE klascode="+ klas.getKlascode();
			
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
	
	public boolean insertKlas(Klas klas) {
		boolean result = false;
		try (Connection conn = super.getConnection()) {
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO klas (klascode, niveau) VALUES ('"+klas.getKlascode()+"','"+klas.getNiveau()+"')";
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
