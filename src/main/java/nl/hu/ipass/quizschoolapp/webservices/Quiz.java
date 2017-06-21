package nl.hu.ipass.quizschoolapp.webservices;

public class Quiz {
	private int id;
	private String vak;
	private String niveau;
	private String gemaaktDoor;
	private String naam;
	
	public Quiz(String vak, String niveau, String gemaakt, String naam) {
		this.vak = vak;
		this.niveau = niveau;
		this.gemaaktDoor = gemaakt;
		this.naam = naam;
	}
	
	public Quiz(int id, String vak, String niveau, String gemaakt, String naam) {
		this.id = id;
		this.vak = vak;
		this.niveau = niveau;
		this.gemaaktDoor = gemaakt;
		this.naam = naam;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVak() {
		return vak;
	}

	public void setVak(String vak) {
		this.vak = vak;
	}

	public String getNiveau() {
		return niveau;
	}

	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}

	public String getGemaaktDoor() {
		return gemaaktDoor;
	}

	public void setGemaaktDoor(String gemaaktDoor) {
		this.gemaaktDoor = gemaaktDoor;
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}
}
