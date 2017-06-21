package nl.hu.ipass.quizschoolapp.webservices;

import java.util.List;

import nl.hu.ipass.quizschoolapp.persistence.DocentDao;

public class DocentService {
	private DocentDao docentDao = new DocentDao();
	
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
