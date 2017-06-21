package nl.hu.ipass.quizschoolapp.webservices;

import java.util.List;

import nl.hu.ipass.quizschoolapp.persistence.LeerlingDao;

public class LeerlingService {
	private LeerlingDao leerlingDao = new LeerlingDao(); 
			
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
