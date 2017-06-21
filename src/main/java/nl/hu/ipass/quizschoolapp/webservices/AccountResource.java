package nl.hu.ipass.quizschoolapp.webservices;

import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@Path("/accounts")
public class AccountResource {
	
	@GET
	@Path("{id}")
	@Produces("application/json")
	public String getRoleByInlognaam(@PathParam("id") String id) {
		//haal de accountservice op
		AccountService service = ServiceProvider.getAccountService();
		//haal deze functie op uit de service
		Account account = service.getByInlognaam(id);
		
		//maak een json object aan met de volgende gegevens ering
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("Inlognaam", account.getInlognaam());
		job.add("Rol", account.getRole());
		job.add("Wachtwoord", account.getWachtwoord());
		
		//geef het aangemaakte object terug
		return job.build().toString();
	}
	
	@POST
	@RolesAllowed("admin")
	@Path("/createLeerling")
	@Produces("application/json")
	//haal de volgende gegevens uit het formulier op de html pagina en geef die aan een variable
	public String createLeerling(@FormParam("insertInlognaam") String inlognaam,
								 @FormParam("insertRole") String role,
								 @FormParam("insertWachtwoord") String wachtwoord,
								 @FormParam("insertLeerlingnummer") int leerlingnummer,
								 @FormParam("insertKlas") String klas,
								 @FormParam("insertProfiel") String profiel,
								 @FormParam("insertNiveau") String niveau,
								 @FormParam("insertNaam") String naam,
								 @FormParam("insertAchternaam") String achternaam,
								 @FormParam("insertGeboortedatum") String geboortedatum,
								 @FormParam("insertPostcode") String postcode,
								 @FormParam("insertWoonplaats") String woonplaats) {
		//haal de service op
		AccountService service = ServiceProvider.getAccountService();
		
		//maak een nieuwe object aan met de gegevens uit het formulier
		Account newAccount = new Account(inlognaam, role, wachtwoord);
		Leerling newLeerling = new Leerling(leerlingnummer, klas, profiel, niveau, naam, achternaam, geboortedatum, postcode, woonplaats, inlognaam);
		
		//gebruik de gemaakte objecten in de volgende functies uit de service
		service.inserAccount(newAccount);
		service.insertLeerling(newLeerling);
		
		return null;
	}
	
	@POST
	@RolesAllowed("admin")
	@Path("/createDocent")
	@Produces("application/json")
	//haal de volgende gegevens uit het formulier op de html pagina en geef die aan een variable
	public String createDocent(@FormParam("insertInlognaam2") String inlognaam,
								 @FormParam("insertRole2") String role,
								 @FormParam("insertWachtwoord2") String wachtwoord,
								 @FormParam("insertDocentnummer") int docentnummer,
								 @FormParam("insertNaam2") String naam,
								 @FormParam("insertAchternaam2") String achternaam,
								 @FormParam("insertPostcode2") String postcode,
								 @FormParam("insertWoonplaats2") String woonplaats,
								 @FormParam("insertNiveau2") String niveau,
								 @FormParam("insertGeboortedatum2") String geboortedatum) {
		//haal de service op
		AccountService service = ServiceProvider.getAccountService();
		
		//maak een nieuwe object aan met de gegevens uit het formulier
		Account newAccount = new Account(inlognaam, role, wachtwoord);
		Docent newDocent = new Docent(docentnummer, naam, achternaam, postcode, woonplaats, niveau, geboortedatum, inlognaam);
		
		//gebruik de gemaakte objecten in de volgende functies uit de service
		service.inserAccount(newAccount);
		service.insertDocent(newDocent);
		
		return null;
	}
	
	@DELETE
	@RolesAllowed("admin")
	@Path("{id}/{nummer}")
	//haal de volgende gegevens uit de url en geef ze aan een variable
	public Response deleteLeerling(@PathParam("id") String id,
								  @PathParam("nummer") int nummer) {
		//haal de servicen op
		AccountService service = ServiceProvider.getAccountService();
		LeerlingService service2 = ServiceProvider.getLeerlingService();
				
		Account found = null;
		Leerling found2 = null;
		
		//doorzoek alle accounts en kijk of de inlognaam gelijk is aan de opgegeven inlognaam 
		for (Account a : service.getAllAccounts()) {
			if (a.getInlognaam().equals(id)) {
				found = a; break;
				
			}
		}
		//doorzoek alle leerlingen en kijk of het leerlingnummer gelijk is aan het opgegeven nummer 
		for (Leerling l : service2.getAllLeerlingen()) {
			if (l.getLeerlingnummer() == nummer) {
				found2 = l; break;
			}
		}
		
		//als voor allebij niets gevonden is geef dan een status not found
		if (found == null && found2 == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			//als er wel wat gevonden is voer dan de volgende functies uit de service uit
			service.deleteLeerling(found2);
			service.deleteAccount(found);
			return Response.ok().build();
		}
	}

	@PUT
	@RolesAllowed("admin")
	@Path("/updateLeerling/{id}")
	@Produces("application/json")
	//haal de volgende gegevens uit het formulier op de html pagina en geef die aan een variable
	public Response updateLeerling(@FormParam("insertInlognaam") String inlognaam,
			 				   	   @FormParam("insertRole") String role,
			 				   	   @FormParam("insertWachtwoord") String wachtwoord,
			 				   	   @FormParam("insertLeerlingnummer") int leerlingnummer,
			 				   	   @FormParam("insertKlas") String klas,
			 				   	   @FormParam("insertProfiel") String profiel,
			 				   	   @FormParam("insertNiveau") String niveau,
			 				   	   @FormParam("insertNaam") String naam,
			 				   	   @FormParam("insertAchternaam") String achternaam,
			 				   	   @FormParam("insertGeboortedatum") String geboortedatum,
			 				   	   @FormParam("insertPostcode") String postcode,
			 				   	   @FormParam("insertWoonplaats") String woonplaats) {
		//haal de servicen op
		AccountService service = ServiceProvider.getAccountService();
		LeerlingService service2 = ServiceProvider.getLeerlingService();
		
		Account found = null;
		Leerling found2 = null;
		
		//doorloop alle accounts en kijk of de inlognaam gelijk is aan het opgegeven inlognaam 
		//en gebruik de setters om de waardes te zetten met de waardes van het formulier
		for (Account a : service.getAllAccounts()) {
			if (a.getInlognaam().equals(inlognaam)) {
				a.setInlognaam(inlognaam);
				a.setRole(role);
				a.setWachtwoord(wachtwoord);
				found = a;
			}
		}
		
		//doorloop alle leerlingen en kijk of het leerlingnummer gelijk is aan het opgegeven leerlingnummer 
		//en gebruik de setters om de waardes te zetten met de waardes van het formulier
		for (Leerling l : service2.getAllLeerlingen()) {
			if (l.getLeerlingnummer() == leerlingnummer) {
				l.setLeerlingnummer(leerlingnummer);
				l.setAchternaam(achternaam);
				l.setGeboortedatum(geboortedatum);
				l.setKlascode(klas);
				l.setNaam(naam);
				l.setNiveau(niveau);
				l.setPostcode(postcode);
				l.setProfiel(profiel);
				l.setWoonplaats(woonplaats);
				l.setInlognaam(inlognaam);
				found2 = l;
			}
		}
		
		//als voor allebij niets gevonden is geef dan een status not found
		if (found == null && found2 == null) {
			throw new WebApplicationException("Leerling not found!");
		}
		else {
			//als er wel wat gevonden is voer dan de volgende functies uit de service uit
			service.updateAccount(found);
			service.updateLeerling(found2);
			return Response.ok().build();
		}
	}
	
	@DELETE
	@RolesAllowed("admin")
	@Path("/docent/{id}/{nummer}")
	//haal de volgende gegevens uit de url en geef ze aan een variable
	public Response deleteDocent(@PathParam("id") String id,
								  @PathParam("nummer") int nummer) {
		//haal de servicen op
		AccountService service = ServiceProvider.getAccountService();
		DocentService service2 = ServiceProvider.getDocentService();
				
		Account found = null;
		Docent found2 = null;
		
		//doorzoek alle accounts en kijk of de inlognaam gelijk is aan de opgegeven inlognaam 
		for (Account a : service.getAllAccounts()) {
			if (a.getInlognaam().equals(id)) {
				found = a; break;
				
			}
		}
		
		//doorzoek alle docenten en kijk of het docentnummer gelijk is aan het opgegeven docentnummer 
		for (Docent d : service2.getAllDocenten()) {
			if (d.getDocentnummer() == nummer) {
				found2 = d; break;
			}
		}
		
		//als voor allebij niets gevonden is geef dan een status not found
		if (found == null && found2 == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			//als er wel wat gevonden is voer dan de volgende functies uit de service uit
			service.deleteDocent(found2);
			service.deleteAccount(found);
			return Response.ok().build();
		}
	}
	
	@PUT
	@RolesAllowed("admin")
	@Path("/updateDocent/{id}")
	@Produces("application/json")
	//haal de volgende gegevens uit het formulier op de html pagina en geef die aan een variable
	public Response updateDocent(@FormParam("insertInlognaam2") String inlognaam,
			 					 @FormParam("insertRole2") String role,
			 					 @FormParam("insertWachtwoord2") String wachtwoord,
			 					 @FormParam("insertDocentnummer") int docentnummer,
			 					 @FormParam("insertNaam2") String naam,
			 					 @FormParam("insertAchternaam2") String achternaam,
			 					 @FormParam("insertPostcode2") String postcode,
			 					 @FormParam("insertWoonplaats2") String woonplaats,
			 					 @FormParam("insertNiveau2") String niveau,
			 					 @FormParam("insertGeboortedatum2") String geboortedatum) {
		//haal de servicen op
		AccountService service = ServiceProvider.getAccountService();
		DocentService service2 = ServiceProvider.getDocentService();
		
		Account found = null;
		Docent found2 = null;
		
		// doorloop alle accounts en kijk of de inlognaam gelijk is aan de opgegeven inlognaam 
		// en gebruik de setters om de waardes te zetten met de waardes van het formulier
		for (Account a : service.getAllAccounts()) {
			if (a.getInlognaam().equals(inlognaam)) {
				a.setInlognaam(inlognaam);
				a.setRole(role);
				a.setWachtwoord(wachtwoord);
				found = a;
			}
		}
		
		// doorloop alle docenten en kijk of het dcoentnummer gelijk is aan het opgegeven docentnummer 
		// en gebruik de setters om de waardes te zetten met de waardes van het formulier
		for (Docent d : service2.getAllDocenten()) {
			if (d.getDocentnummer() == docentnummer) {
				d.setDocentnummer(docentnummer);
				d.setAchternaam(achternaam);
				d.setNaam(naam);
				d.setPostcode(postcode);
				d.setWoonplaats(woonplaats);
				d.setNiveau(niveau);
				d.setGeboortedatum(geboortedatum);
				found2 = d;
			}
		}
		
		// als voor allebij niets gevonden is geef dan een status not found
		if (found == null && found2 == null) {
			throw new WebApplicationException("Docent not found!");
		}
		else {
			// als er wel wat gevonden is voer dan de volgende functies uit de service uit
			service.updateAccount(found);
			service.updateDocent(found2);
			return Response.ok().build();
		}
	}
}
