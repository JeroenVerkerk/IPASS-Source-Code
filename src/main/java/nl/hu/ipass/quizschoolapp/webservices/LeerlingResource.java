package nl.hu.ipass.quizschoolapp.webservices;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/leerlingen")
public class LeerlingResource {

	@GET
	@Produces("application/json")
	public String getAllLeerlingen() {
		// haal de service op
		LeerlingService service = ServiceProvider.getLeerlingService();
		JsonArrayBuilder jab = Json.createArrayBuilder();

		// maak voor elke leerling een nieuw JsonObject aan en voeg deze toe aan
		// de JsonArray
		for (Leerling l : service.getAllLeerlingen()) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("Leerlingnummer", l.getLeerlingnummer());
			job.add("KlasCode", l.getKlascode());
			job.add("Profiel", l.getProfiel());
			job.add("Niveau", l.getNiveau());
			job.add("Naam", l.getNaam());
			job.add("Achternaam", l.getAchternaam());
			job.add("Postcode", l.getPostcode());
			job.add("Woonplaats", l.getWoonplaats());
			job.add("Geboortedatum", l.getGeboortedatum());
			job.add("Inlognaam", l.getInlognaam());

			jab.add(job);
		}

		JsonArray array = jab.build();
		// geef de Array terug
		return array.toString();
	}

	@GET
	@Path("{inlognaam}")
	@Produces("application/json")
	// wijs de parameters die meegegeven worden bij de url aan een variable toe
	public String getLeerlingByInlognaam(@PathParam("inlognaam") String inlognaam) {
		// haal de service op
		LeerlingService service = ServiceProvider.getLeerlingService();
		// haal de volgende functie uit de service
		Leerling l = service.getLeerlingByInlognaam(inlognaam);

		// maak voor de gevonden leerling een JsonObject aan
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("Leerlingnummer", l.getLeerlingnummer());
		job.add("KlasCode", l.getKlascode());
		job.add("Profiel", l.getProfiel());
		job.add("Niveau", l.getNiveau());
		job.add("Naam", l.getNaam());
		job.add("Achternaam", l.getAchternaam());
		job.add("Postcode", l.getPostcode());
		job.add("Woonplaats", l.getWoonplaats());
		job.add("Geboortedatum", l.getGeboortedatum());
		job.add("Inlognaam", l.getInlognaam());

		// geef het object terug
		return job.build().toString();
	}

	@GET
	@Path("/leerling/{id}")
	@Produces("application/json")
	// wijs de parameters die meegegeven worden bij de url aan een variable toe
	public String getLeerlingByLeerlingnummer(@PathParam("id") int nummer) {
		// haal de service op
		LeerlingService service = ServiceProvider.getLeerlingService();
		// haal de volgende functie uit de service
		Leerling l = service.getLeerlingByLeerlingNummer(nummer);

		// maak voor de gevonden leerling een JsonObject aan
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("Leerlingnummer", l.getLeerlingnummer());
		job.add("KlasCode", l.getKlascode());
		job.add("Profiel", l.getProfiel());
		job.add("Niveau", l.getNiveau());
		job.add("Naam", l.getNaam());
		job.add("Achternaam", l.getAchternaam());
		job.add("Postcode", l.getPostcode());
		job.add("Woonplaats", l.getWoonplaats());
		job.add("Geboortedatum", l.getGeboortedatum());
		job.add("Inlognaam", l.getInlognaam());

		// geef het object terug
		return job.build().toString();
	}

}
