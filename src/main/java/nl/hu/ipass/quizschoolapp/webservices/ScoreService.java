package nl.hu.ipass.quizschoolapp.webservices;

import java.util.List;
import nl.hu.ipass.quizschoolapp.persistence.*;

public class ScoreService {
	//maak nieuwe dao's aan
	private ScoreDao scoreDao = new ScoreDao();
	private LijstScoreDao lijstScoreDao = new LijstScoreDao();
	
	//maak functies met functies uit de dao's zodat deze gebruikt kunne worden in de resources
	public List<Score> getAllScores() {
		return scoreDao.findAll();
	}
	
	public boolean insertScore(Score score) {
		return scoreDao.insertScore(score);
	}
	
	public Score getScoreById(int id) {
		return scoreDao.findById(id);
	}
	
	public List<Score> getScoreByLeerling(int id) {
		return scoreDao.findByLeerlingnummer(id);
	}
	
	public List<Score> getScoreByDocent(int id) {
		return scoreDao.findByQuizId(id);
	}
	
	public List<LijstScore> getLijstScoresByLeerlingnummer(int nummer) {
		return lijstScoreDao.findByLeerlingnummer(nummer);
	}
	
	public boolean insertLijstScore(LijstScore score) {
		return lijstScoreDao.insertScore(score);
	}
}
