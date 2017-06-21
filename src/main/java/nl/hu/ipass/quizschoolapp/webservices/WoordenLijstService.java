package nl.hu.ipass.quizschoolapp.webservices;

import java.util.List;

import nl.hu.ipass.quizschoolapp.persistence.WoordenDao;
import nl.hu.ipass.quizschoolapp.persistence.WoordenLijstDao;

public class WoordenLijstService {
	//maak nieuwe dao's aan
	private WoordenLijstDao lijstDao = new WoordenLijstDao();
	private WoordenDao woordenDao = new WoordenDao();
	
	//maak nieuwe functie aan die de functies uit de dao's halen
	//zodat ze in de resources gebruikt kunnen worden
	public List<WoordenLijst> getLijstenByOpenbaar() {
		return lijstDao.findOpenbaar();
	}
	
	public List<WoordenLijst> getAllLijsten() {
		return lijstDao.findAll();
	}
	
	public List<WoordenLijst> getLijstenByLeerlingnummer(int nummer) {
		return lijstDao.findByLeerlingnummer(nummer);
	}
	
	public List<Woorden> getWoordenByLijstId(int id) {
		return woordenDao.findByLijstId(id);
	}
	
	public List<Woorden> getWoordenByLijstId2(int id) {
		return woordenDao.findByLijstId2(id);
	}
	
	public WoordenLijst getWoordenLijstById(int id) {
		return lijstDao.findByLijstId(id);
	}
	
	public List<Woorden> getAllWoorden() {
		return woordenDao.findAll();
	}
	
	public List<WoordenLijst> getAllOpenbaar() {
		return lijstDao.findOpenbaar();
	}
	
	public boolean insertWoordenLijst(WoordenLijst lijst) {
		return lijstDao.insertWoordenLijst(lijst);
	}
	
	public boolean insertWoorden(Woorden woorden) {
		return woordenDao.insertWoorden(woorden);
	}
	
	public boolean deleteWoordenLijst(WoordenLijst lijst) {
		return lijstDao.deleteWoordenLijst(lijst);
	}
	
	public boolean deleteWoord(Woorden woord) {
		return woordenDao.deleteWoorden(woord);
	}
}
