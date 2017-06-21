package nl.hu.ipass.quizschoolapp.webservices;

public class Klas {
	private String klascode;
	private String niveau;
	
	public Klas(String klascode, String niveau) {
		this.klascode = klascode;
		this.niveau = niveau;
	}

	public String getKlascode() {
		return klascode;
	}

	public void setKlascode(String klascode) {
		this.klascode = klascode;
	}

	public String getNiveau() {
		return niveau;
	}

	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}
}
