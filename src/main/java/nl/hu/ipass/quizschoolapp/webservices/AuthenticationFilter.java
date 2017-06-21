package nl.hu.ipass.quizschoolapp.webservices;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.*;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
	@Override
	public void filter(ContainerRequestContext requestCtx) throws IOException {
		// Gebruikers worden gasten, tenzij een goeie JWT is opgegeven
		boolean isSecure = requestCtx.getSecurityContext().isSecure();
		MySecurityContext msc = new MySecurityContext("Unknown", "guest", isSecure);
		// check of de HTTP Authorization header er is en goed geformateerd
		String authHeader = requestCtx.getHeaderString(HttpHeaders.AUTHORIZATION);
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			// haal de token uit de HTTP Authorization header
			String token = authHeader.substring("Bearer ".length()).trim();
			try {
				// Kijk of de token goed is
				JwtParser parser = Jwts.parser().setSigningKey(AuthenticationResource.key);
				Claims claims = parser.parseClaimsJws(token).getBody();
				String user = claims.getSubject();
				String role = claims.get("role").toString();
				msc = new MySecurityContext(user, role, isSecure);

			} catch (JwtException | IllegalArgumentException e) {
				System.out.println("Invalid JWT, processing as guest!");
			}
		}

		requestCtx.setSecurityContext(msc);
	}
}