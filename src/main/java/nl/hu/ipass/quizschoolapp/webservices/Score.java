package nl.hu.ipass.quizschoolapp.webservices;

public class Score {
	private int id;
	private double score;
	private int leerlingnummer;
	private String quizNaam;
	private String datum;
	private int quizId;
	
	public Score(int id, double score, int leerlingnummer, String quizNaam, String datum, int quizId) {
		this.id = id;
		this.score = score;
		this.leerlingnummer = leerlingnummer;
		this.quizNaam = quizNaam;
		this.datum = datum;
		this.quizId = quizId;
	}
	
	public Score(double score, int leerlingnummer, String quizNaam, String datum, int quizId) {
		this.score = score;
		this.leerlingnummer = leerlingnummer;
		this.quizNaam = quizNaam;
		this.datum = datum;
		this.quizId = quizId;
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

	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

	public String getQuizNaam() {
		return quizNaam;
	}

	public void setQuizNaam(String quizNaam) {
		this.quizNaam = quizNaam;
	}
	
	public String getDatum() {
		return datum;
	}
	
	public void setDatum(String datum) {
		this.datum = datum;
	}
}
