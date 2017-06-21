package nl.hu.ipass.quizschoolapp.persistence;

import java.net.URI;
import java.sql.Connection;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

public class BaseDao {
	private DataSource connectionPool;

	public BaseDao() {
		try {
			final String DATABASE_URL_PROP = System.getenv("DATABASE_URL");
			// als de url niet null is voer dan volgende functies uit
			if (DATABASE_URL_PROP != null) {
				URI dbUri = new URI(DATABASE_URL_PROP);
				// geef de url van de database aan
				String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();
				BasicDataSource pool = new BasicDataSource();
				// als de info niet null is haal dan de username en password op
				if (dbUri.getUserInfo() != null) {
					pool.setUsername(dbUri.getUserInfo().split(":")[0]);
					pool.setPassword(dbUri.getUserInfo().split(":")[1]);
				}
				// geef de driver en url aan
				pool.setDriverClassName("org.postgresql.Driver");
				pool.setUrl(dbUrl);
				pool.setInitialSize(1);

				connectionPool = pool;
			} else {
				InitialContext ic = new InitialContext();
				connectionPool = (DataSource) ic.lookup("java:comp/env/jdbc/PostgresDS");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// maak de verbinding met de database
	protected final Connection getConnection() {
		try {
			return connectionPool.getConnection();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
