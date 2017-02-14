
public class Paquet {
	
	// taille de chaque paquet 
		public static final int PACKET_SIZE = 100;  
		
		private int creation;
		private int debutEnvoie;
		private int finEnvoie;
		private int nbBits;
		private Utilisateur user;

		
		public Paquet(Utilisateur u, int time) {

			this.creation = time;
			this.nbBits = PACKET_SIZE;
			this.user = u;

		}


		public int getdebutEnvoie() {
			return this.debutEnvoie;
		}


		public void setdebutEnvoie(int time) {
			this.debutEnvoie = time;
		}


		public int getfinEnvoie() {
			return this.finEnvoie;
		}


		public void setfinEnvoie(int time) {
			this.finEnvoie = time;
		}


		public int getNbBits() {
			return this.nbBits;
		}


		public void setNbBits(int nbBits) {
			this.nbBits = nbBits;
		}

        // Get creation de temps
		 
		public int getCreation() {
			return creation;
		}
		
}
