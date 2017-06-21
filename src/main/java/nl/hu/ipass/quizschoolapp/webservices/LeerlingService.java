package nl.hu.ipass.quizschoolapp.webservices;

import java.util.List;

import nl.hu.ipass.quizschoolapp.persistence.LeerlingDao;

public class LeerlingService {
	//maak een nieuwe leerlingDao aan
	private LeerlingDao leerlingDao = new LeerlingDao(); 
	
	//haal de volgende functies uit de dao zodat deze in de resources gebruikt kunnen worden
	public List<Leerling> getAllLeerlingen() {
		return leerlingDao.findAll();
	}
	
	public Leerling getLeerlingByLeerlingNummer(int nummer) {
		return leerlingDao.findByLeerlingnummer(nummer);
	}
	
	public Leerling getLeerlingByInlognaam(String naam) {
		return leerlingDao.findByInlognaam(naam);
	}
}
