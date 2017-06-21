package nl.hu.ipass.quizschoolapp.webservices;

public class Docent {
	private int docentnummer;
	private String naam;
	private String achternaam;
	private String postcode;
	private String woonplaats;
	private String niveau;
	private String geboortedatum;
	private String inlognaam;
	
	public Docent(int docentnummer, String naam, String achternaam, String postcode, String woonplaats, String niveau,String geboortedatum, String inlognaam) {
		super();
		this.docentnummer = docentnummer;
		this.naam = naam;
		this.achternaam = achternaam;
		this.postcode = postcode;
		this.woonplaats = woonplaats;
		this.niveau = niveau;
		this.geboortedatum = geboortedatum;
		this.inlognaam = inlognaam;
	}

	public int getDocentnummer() {
		return docentnummer;
	}

	public void setDocentnummer(int docentnummer) {
		this.docentnummer = docentnummer;
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

	public String getNiveau() {
		return niveau;
	}

	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}

	public String getGeboortedatum() {
		return geboortedatum;
	}

	public void setGeboortedatum(String geboortedatum) {
		this.geboortedatum = geboortedatum;
	}

	public String getInlognaam() {
		return inlognaam;
	}

	public void setInlognaam(String inlognaam) {
		this.inlognaam = inlognaam;
	}
	
	
}
