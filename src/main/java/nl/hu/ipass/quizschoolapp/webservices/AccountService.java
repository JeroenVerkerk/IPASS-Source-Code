package nl.hu.ipass.quizschoolapp.webservices;

import java.util.List;
import nl.hu.ipass.quizschoolapp.persistence.*;

public class AccountService {
	private AccountDao accountDao = new AccountDao();	
	
	public List<Account> getAllAccounts() {
		return accountDao.findAll();
	}
	
	public Account getByInlognaam(String id) {
		return accountDao.findByInlognaam(id);
	}
}
