package nl.hu.ipass.quizschoolapp.webservices;

public class Woorden {
		private int woordId;
		private String taal1Woord;
		private String taal2Woord;
		private int lijstId;
		
		public Woorden(int woordId, String taal1Woord, String taal2Woord, int lijstId) {;
			this.woordId = woordId;
			this.taal1Woord = taal1Woord;
			this.taal2Woord = taal2Woord;
			this.lijstId = lijstId;
		}
		
		public Woorden(String taal1Woord, String taal2Woord) {;
		this.taal1Woord = taal1Woord;
		this.taal2Woord = taal2Woord;
	}

		public int getWoordId() {
			return woordId;
		}

		public void setWoordId(int woordId) {
			this.woordId = woordId;
		}

		public String getTaal1Woord() {
			return taal1Woord;
		}

		public void setTaal1Woord(String taal1Woord) {
			this.taal1Woord = taal1Woord;
		}

		public String getTaal2Woord() {
			return taal2Woord;
		}

		public void setTaal2Woord(String taal2Woord) {
			this.taal2Woord = taal2Woord;
		}

		public int getLijstId() {
			return lijstId;
		}

		public void setLijstId(int lijstId) {
			this.lijstId = lijstId;
		}
}
