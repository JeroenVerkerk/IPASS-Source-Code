package nl.hu.ipass.quizschoolapp.webservices;

import nl.hu.ipass.quizschoolapp.webservices.QuizService;
import nl.hu.ipass.quizschoolapp.webservices.AccountService;

public class ServiceProvider {
	private static QuizService quizService = new QuizService();
	private static AccountService accountService = new AccountService();
	private static ScoreService scoreService = new ScoreService();
	private static LeerlingService leerlingService = new LeerlingService();
	private static DocentService docentService = new DocentService();

	public static QuizService getQuizService() {
		return quizService;
	}
	
	public static AccountService getAccountService() {
		return accountService;
	}
	
	public static ScoreService getScoreService() {
		return scoreService;
	}
	
	public static LeerlingService getLeerlingService() {
		return leerlingService;
	}
	
	public static DocentService getDocentService() {
		return docentService;
	}
}
