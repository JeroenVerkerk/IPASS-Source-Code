package nl.hu.ipass.quizschoolapp.webservices;

public class Leerling {
	private int leerlingnummer;
	private String klascode;
	private String profiel;
	private String niveau;
	private String naam;
	private String achternaam;
	private String geboortedatum;
	private String postcode;
	private String woonplaats;
	private String inlognaam;
	
	public Leerling (int lrn, String klascode, String profiel, String niveau, String naam, String achternaam, String geboortedatum, String postcode, String woonplaats, String inlognaam) {
		this.leerlingnummer = lrn;
		this.klascode = klascode;
		this.profiel = profiel;
		this.niveau = niveau;
		this.naam = naam;
		this.achternaam = achternaam;
		this.geboortedatum = geboortedatum;
		this.postcode = postcode;
		this.woonplaats = woonplaats;
		this.inlognaam = inlognaam;
	}

	public int getLeerlingnummer() {
		return leerlingnummer;
	}

	public void setLeerlingnummer(int leerlingnummer) {
		this.leerlingnummer = leerlingnummer;
	}

	public String getKlascode() {
		return klascode;
	}

	public void setKlascode(String klascode) {
		this.klascode = klascode;
	}

	public String getProfiel() {
		return profiel;
	}

	public void setProfiel(String profiel) {
		this.profiel = profiel;
	}

	public String getNiveau() {
		return niveau;
	}

	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public String getAchternaam() {
		return achternaam;
	}

	public void setAchternaam(String achternaam) {
		this.achternaam = achternaam;
	}

	public String getGeboortedatum() {
		return geboortedatum;
	}

	public void setGeboortedatum(String geboortedatum) {
		this.geboortedatum = geboortedatum;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getWoonplaats() {
		return woonplaats;
	}

	public void setWoonplaats(String woonplaats) {
		this.woonplaats = woonplaats;
	}

	public String getInlognaam() {
		return inlognaam;
	}

	public void setInlognaam(String inlognaam) {
		this.inlognaam = inlognaam;
	}
	
	
}
