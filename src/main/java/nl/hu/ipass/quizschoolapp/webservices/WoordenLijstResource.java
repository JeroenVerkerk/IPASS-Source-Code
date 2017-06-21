package nl.hu.ipass.quizschoolapp.webservices;

import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/woordenlijsten")
public class WoordenLijstResource {

	@POST
	@RolesAllowed({"leerling", "admin"})
	@Produces("application/json")
	//maak voor elke waarde uit het html formulier een nieuwe variable aan
	public String createLijst(@FormParam("naamInsert") String naam,
							 @FormParam("insertOpenbaar") boolean openbaar,
							 @FormParam("taal1Insert") String taal1,
							 @FormParam("taal2Insert") String taal2,
							 @FormParam("leerlingnummerInsert") int leerlingnummer,
							 @FormParam("taal1WoordInsert") String taal1Woord,
							 @FormParam("taal2WoordInsert") String taal2Woord) {
		//haal de service op
		WoordenLijstService service = ServiceProvider.getWoordenLijstSerivice();
		//maak nieuwe objecten aan met de hierboven gemaakte variabelen
		WoordenLijst newLijst = new WoordenLijst(naam, openbaar, taal1, taal2, leerlingnummer);
		Woorden newWoorden = new Woorden(taal1Woord, taal2Woord);
		
		//gebruik de objecten hierboven in de onderstaande functies uit de dao's
		service.insertWoordenLijst(newLijst);
		service.insertWoorden(newWoorden);
		return null;
	}
	
	@POST
	@RolesAllowed({"leerling", "admin"})
	@Path("/insertWoord")
	@Produces("application/json")
	//maak voor elke waarde uit het html formulier een variable
	public String createWoord(@FormParam("taal1WoordInsert2") String taal1Woord,
							  @FormParam("taal2WoordInsert2") String taal2Woord) {
		//haal de service op
		WoordenLijstService service = ServiceProvider.getWoordenLijstSerivice();
		//maak een nieuw object aan met de hierboven aangemaakte objecten
		Woorden newWoorden = new Woorden(taal1Woord, taal2Woord);
		
		//gebruik het object in de onderstaande functie uit de dao
		service.insertWoorden(newWoorden);
		return null;
	}
	
	@GET
	@Path("{id}")
	@Produces("application/json")
	//wijs de parameter die meegegeven wordt in de url toe aan een variable
	public String getWoordenlijsten(@PathParam("id") int nummer) {
		WoordenLijstService service = ServiceProvider.getWoordenLijstSerivice();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		//maak voor elke gevonden woordenlijst een nieuw JsonObject aan
		//en voeg deze toe aan de JsonArray
		for (WoordenLijst w : service.getLijstenByLeerlingnummer(nummer)) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("LijstId", w.getId());
			job.add("Naam", w.getNaam());
			job.add("Openbaar", w.isOpenbaar());
			job.add("Taal1", w.getTaal1());
			job.add("Taal2", w.getTaal2());
			job.add("Leerlingnummer", w.getLeerlingnummer());
			
			jab.add(job);
		}
		
		JsonArray array = jab.build();
		//geef de JsonArray terug
		return array.toString();
	}
	
	@GET
	@Path("/woorden/{id}")
	@Produces("application/json")
	//wijs de parameter die meegegeven wordt in de url toe aan een variable
	public String getWoordenByLijstId(@PathParam("id") int id) {
		WoordenLijstService service = ServiceProvider.getWoordenLijstSerivice();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		//maak voor elke gevonden woord een nieuw JsonObject aan
		//en voeg deze toe aan de JsonArray
		for (Woorden w : service.getWoordenByLijstId(id)) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("WoordId", w.getWoordId());
			job.add("Taal1Woord", w.getTaal1Woord());
			job.add("Taal2Woord", w.getTaal2Woord());
			
			jab.add(job);
		}
		
		JsonArray array = jab.build();
		//geef de Array terug
		return array.toString();
	}
	
	@GET
	@Path("/woordenVersie1/{id}")
	@Produces("application/json")
	//wijs de parameter die meegegeven wordt in de url toe aan een variable
	public String getWoordenByLijstId1(@PathParam("id") int id) {
		//haal de service op
		WoordenLijstService service = ServiceProvider.getWoordenLijstSerivice();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		//maak voor elke gevonden wordt een nieuw JsonObject aan
		//en voeg deze toe aan de JsonArray
		for (Woorden w : service.getWoordenByLijstId(id)) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("WoordId", w.getWoordId());
			job.add("Taal1Woord", w.getTaal1Woord());
			job.add("Taal2Woord", w.getTaal2Woord());
			
			jab.add(job);
		}
		
		JsonArray array = jab.build();
		//geef de Array terug
		return array.toString();
	}
	
	@GET
	@Path("/woordenVersie2/{id}")
	@Produces("application/json")
	//wijs de parameter die meegegeven wordt in de url toe aan een variable
	public String getWoordenByLijstId2(@PathParam("id") int id) {
		//haal de service op
		WoordenLijstService service = ServiceProvider.getWoordenLijstSerivice();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		//maak voor elke gevonden woord een nieuw JsonObject aan
		//en voeg deze toe aan de JsonArray
		for (Woorden w : service.getWoordenByLijstId2(id)) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("WoordId", w.getWoordId());
			job.add("Taal1Woord", w.getTaal1Woord());
			job.add("Taal2Woord", w.getTaal2Woord());
			
			jab.add(job);
		}
		
		JsonArray array = jab.build();
		//geef de Array terug
		return array.toString();
	}
	
	@GET
	@Path("/openbaar")
	@Produces("application/json")
	public String getLijstenOpenbaar() {
		//haal de service op
		WoordenLijstService service = ServiceProvider.getWoordenLijstSerivice();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		//maak voor elke gevonden woordenlijst een nieuw JsonObject aan
		//en voeg deze toe aan de JsonArray
		for (WoordenLijst w : service.getAllOpenbaar()) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("LijstId", w.getId());
			job.add("Naam", w.getNaam());
			job.add("Openbaar", w.isOpenbaar());
			job.add("Taal1", w.getTaal1());
			job.add("Taal2", w.getTaal2());
			job.add("Leerlingnummer", w.getLeerlingnummer());
		
			
			jab.add(job);
		}
		
		JsonArray array = jab.build();
		//geef de Array terug
		return array.toString();
	}
	
	@GET
	@Path("/lijst/{id}")
	@Produces("application/json")
	//wijs de parameter die meegegeven wordt in de url toe aan een variable
	public String getLijstById(@PathParam("id") int id) {
		//haal de service op
		WoordenLijstService service = ServiceProvider.getWoordenLijstSerivice();
		WoordenLijst lijst = service.getWoordenLijstById(id);
		
		//maak voor de gevonden woordenlijst een nieuw JsonObject aan
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("LijstId", lijst.getId());
		job.add("Naam", lijst.getNaam());
		job.add("Openbaar", lijst.isOpenbaar());
		job.add("Taal1", lijst.getTaal1());
		job.add("Taal2", lijst.getTaal2());
		job.add("Leerlingnummer", lijst.getLeerlingnummer());
		
		//geef het object terug
		return job.build().toString();
	}
	
	@DELETE
	@RolesAllowed({ "leerling", "admin" })
	@Path("{id}")
	//wijs de parameter die meegegeven wordt in de url toe aan een variable
	public Response deleteWoordenLijst(@PathParam("id") int id) {
		//haal de service op
		WoordenLijstService service = ServiceProvider.getWoordenLijstSerivice();
		WoordenLijst found = null;
		
		//zoek of er woordenlijsten zijn die hetzelfde id hebben als het opgegeven id
		for (WoordenLijst w : service.getAllLijsten()) {
			if (w.getId() == id) {
				found = w; break;
				
			}
		}
		
		//als er geen woordenlijst gevonden is stuur dan een not found response terug
		if (found == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			//als er wel een lijst gevonden is gebruik deze dan in de onderstaande functie uit de dao
			service.deleteWoordenLijst(found);
			return Response.ok().build();
		}
	}
	
	@DELETE
	@RolesAllowed({ "leerling", "admin" })
	@Path("/deleteWoord/{id}")
	//wijs de parameter die meegegeven wordt in de url toe aan een variable
	public Response deleteWoord(@PathParam("id") int id) {
		//haal de service op
		WoordenLijstService service = ServiceProvider.getWoordenLijstSerivice();
		Woorden found = null;
		
		//kijk of eer een woord is met hetzelfde id als het id dat is meegegeven
		for (Woorden w : service.getAllWoorden()) {
			if (w.getWoordId() == id) {
				found = w; break;
				
			}
		}
		
		//als er geen lijsten zijn gevonden geef dan een not found response terug
		if (found == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			//als er wel een lijst gevonden is gebruik deze dan in de onderstaande functie uit de dao
			service.deleteWoord(found);
			return Response.ok().build();
		}
	}
}
