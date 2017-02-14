import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Utilisateur {
	// Parameters
		private int id;
		private List<Paquet> paquetAenvoyer;
		private Deque<Paquet> fifo;
		private PointAcces pA;
		private int debitCourrant;
		private int moyenProche;
		

		/**
		 * Constructeur
		 * 
		 */
		public Utilisateur(int id, PointAcces PointAcces, int mp) {

			this.id = id;
			this.pA = PointAcces;
			this.moyenProche = mp;
			this.paquetAenvoyer = new ArrayList<Paquet>();
			fifo = new LinkedList<Paquet>();
						
		}

		public void creerPaquet() {
			
			Paquet p = new Paquet(this, pA.getTemps());
			fifo.add(p);
				
	}


		public int getDebitCourrant() {
			return debitCourrant;
		}


		public void setDebitCourrant(int debitCourrant) {
			this.debitCourrant = debitCourrant;
		}


		public int getId() {
			return id;
		}


		public List<Paquet> getPaquetAenvoyer() {
			return paquetAenvoyer;
		}

}
