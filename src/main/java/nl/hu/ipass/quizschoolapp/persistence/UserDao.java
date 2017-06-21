package nl.hu.ipass.quizschoolapp.persistence;

import java.sql.*;

public class UserDao extends BaseDao {
	public String findRoleForUsernameAndPassword(String username, String password) {
		String role = null;
		//maak de query aan en haal de rol op waar de username ne wachtwoord gelijk zijn aan de opgegeven gegevens
		String query = "SELECT role FROM useraccount WHERE inlognaam = ? AND wachtwoord = ?";
		
		//maak verbinding met de database via de BaseDao
		try (Connection con = super.getConnection()) {

			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, username);
			pstmt.setString(2, password);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
				//haal voor elk resultaat de volgende gegevens op
				role = rs.getString("role");

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		//geef de rol terug
		return role;
	}
}


