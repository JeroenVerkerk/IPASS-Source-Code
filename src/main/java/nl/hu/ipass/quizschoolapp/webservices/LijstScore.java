package nl.hu.ipass.quizschoolapp.webservices;

public class LijstScore {
	private int id;
	private double score;
	private int leerlingnummer;
	private String lijstNaam;
	private String datum;
	
	public LijstScore(int id, double score, int leerlingnummer, String lijstNaam, String datum) {
		this.id = id;
		this.score = score;
		this.leerlingnummer = leerlingnummer;
		this.lijstNaam = lijstNaam;
		this.datum = datum;
	}

	public LijstScore(double score, int leerlingnummer, String lijstNaam, String datum) {
		super();
		this.score = score;
		this.leerlingnummer = leerlingnummer;
		this.lijstNaam = lijstNaam;
		this.datum = datum;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public int getLeerlingnummer() {
		return leerlingnummer;
	}

	public void setLeerlingnummer(int leerlingnummer) {
		this.leerlingnummer = leerlingnummer;
	}

	public String getLijstNaam() {
		return lijstNaam;
	}

	public void setLijstNaam(String lijstNaam) {
		this.lijstNaam = lijstNaam;
	}
	
	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}
}
