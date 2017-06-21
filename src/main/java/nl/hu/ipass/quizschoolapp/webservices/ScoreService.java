package nl.hu.ipass.quizschoolapp.webservices;

import java.util.List;
import nl.hu.ipass.quizschoolapp.persistence.*;

public class ScoreService {
	private ScoreDao scoreDao = new ScoreDao();
	
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
}
