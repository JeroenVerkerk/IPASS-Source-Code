package nl.hu.ipass.quizschoolapp.webservices;

import java.security.Key;
import java.util.Calendar;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import nl.hu.ipass.quizschoolapp.persistence.UserDao;

@Path("/authentication")
public class AuthenticationResource {
	final static public Key key = MacProvider.generateKey();

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response authenticateUser(@FormParam("username") String username, @FormParam("password") String password) {
		System.out.print("authenticating");
		try {
			// Authenticate de gebruiker tegen de database
			UserDao dao = new UserDao();
			String role = dao.findRoleForUsernameAndPassword(username, password);

			if (role == null) {
				throw new IllegalArgumentException("No user found!");
			}

			// maak een token voor de gebruiker
			Calendar expiration = Calendar.getInstance();
			expiration.add(Calendar.MINUTE, 30);

			String token = Jwts.builder().setSubject(username).claim("role", role).setExpiration(expiration.getTime())
					.signWith(SignatureAlgorithm.HS512, key).compact();
			// geef de token terug bij de response
			System.out.println("rol="+role);
			return Response.ok(token).build();
		} catch (JwtException | IllegalArgumentException e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
}