package nl.hu.ipass.quizschoolapp.webservices;

public class Account {
	private String inlognaam;
	private String role;
	private String wachtwoord;
	
	public Account(String inlog, String role, String wacht) {
		this.inlognaam = inlog;
		this.role = role;
		this.wachtwoord = wacht;
	}

	public String getInlognaam() {
		return inlognaam;
	}

	public void setInlognaam(String inlognaam) {
		this.inlognaam = inlognaam;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getWachtwoord() {
		return wachtwoord;
	}

	public void setWachtwoord(String wachtwoord) {
		this.wachtwoord = wachtwoord;
	}
	
	
}
