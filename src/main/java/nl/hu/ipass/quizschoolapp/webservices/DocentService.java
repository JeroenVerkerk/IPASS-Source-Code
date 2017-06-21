package nl.hu.ipass.quizschoolapp.webservices;

import java.util.List;

import nl.hu.ipass.quizschoolapp.persistence.DocentDao;

public class DocentService {
	//maak nieuwe dao's aan
	private DocentDao docentDao = new DocentDao();
	
	//haal de functies uit de dao's zodat ze te gebruiken zijn in de resources
	public List<Docent> getAllDocenten() {
		return docentDao.findAll();
	}
	
	public Docent getByDocentnummer(int nummer) {
		return docentDao.findByDocentnummer(nummer);
	}
	
	public Docent getByInlognaam(String inlognaam) {
		return docentDao.findByInlognaam(inlognaam);
	}
	
}
