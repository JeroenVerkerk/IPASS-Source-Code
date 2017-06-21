package nl.hu.ipass.quizschoolapp.webservices;

public class WoordenLijst {
	private int id;
	private String naam;
	private boolean openbaar;
	private String taal1;
	private String taal2;
	private int leerlingnummer;
	
	public WoordenLijst(int id, String naam, boolean openbaar, String taal1, String taal2, int leerlingnummer) {
		this.id = id;
		this.naam = naam;
		this.openbaar = openbaar;
		this.taal1 = taal1;
		this.taal2 = taal2;
		this.leerlingnummer = leerlingnummer;
	}
	

	public WoordenLijst(String naam, boolean openbaar, String taal1, String taal2, int leerlingnummer) {
		this.naam = naam;
		this.openbaar = openbaar;
		this.taal1 = taal1;
		this.taal2 = taal2;
		this.leerlingnummer = leerlingnummer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public boolean isOpenbaar() {
		return openbaar;
	}

	public void setOpenbaar(boolean openbaar) {
		this.openbaar = openbaar;
	}

	public String getTaal1() {
		return taal1;
	}

	public void setTaal1(String taal1) {
		this.taal1 = taal1;
	}

	public String getTaal2() {
		return taal2;
	}

	public void setTaal2(String taal2) {
		this.taal2 = taal2;
	}

	public int getLeerlingnummer() {
		return leerlingnummer;
	}

	public void setLeerlingnummer(int leerlingnummer) {
		this.leerlingnummer = leerlingnummer;
	}
	
	
}
