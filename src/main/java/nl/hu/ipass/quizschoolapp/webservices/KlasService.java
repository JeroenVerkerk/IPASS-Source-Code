package nl.hu.ipass.quizschoolapp.webservices;

import java.util.List;

import nl.hu.ipass.quizschoolapp.persistence.KlasDao;

public class KlasService {
	//maak een nieuwe klasDao aan
	private KlasDao klasDao = new KlasDao();
	
	//haal de functies op uit de dao's zodat ze te gebruiken zijn ide resources
	public List<Klas> getAllKlassen() {
		return klasDao.findAll();
	}
}
