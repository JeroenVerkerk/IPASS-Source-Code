package nl.hu.ipass.quizschoolapp.webservices;

import java.util.List;
import nl.hu.ipass.quizschoolapp.persistence.*;

public class AccountService {
	// maak nieuw dao's aan
	private AccountDao accountDao = new AccountDao();
	private LeerlingDao leerlingDao = new LeerlingDao();
	private DocentDao docentDao = new DocentDao();

	// haal voor elke van de onderstaande functies de functies uit de dao's die
	// hierboven zij aangemaakt
	public List<Account> getAllAccounts() {
		return accountDao.findAll();
	}

	public Account getByInlognaam(String id) {
		return accountDao.findByInlognaam(id);
	}

	public boolean inserAccount(Account account) {
		return accountDao.insertAccount(account);
	}

	public boolean insertLeerling(Leerling leerling) {
		return leerlingDao.insertLeerling(leerling);
	}

	public boolean insertDocent(Docent docent) {
		return docentDao.insertDocent(docent);
	}

	public boolean deleteLeerling(Leerling leerling) {
		return leerlingDao.deleteLeerling(leerling);
	}

	public boolean deleteDocent(Docent docent) {
		return docentDao.deleteDocent(docent);
	}

	public boolean deleteAccount(Account account) {
		return accountDao.deleteAccount(account);
	}

	public boolean updateAccount(Account account) {
		return accountDao.updateAccount(account);
	}

	public boolean updateLeerling(Leerling leerling) {
		return leerlingDao.updateLeerling(leerling);
	}

	public boolean updateDocent(Docent docent) {
		return docentDao.updateDocent(docent);
	}

}
