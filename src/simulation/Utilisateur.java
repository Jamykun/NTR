package simulation;

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
			PaquetValide(p);
			fifo.add(p);
				
	}
		
		/*
		 * Validation du paquet
		 * 
		 * */
		public void PaquetValide(Paquet p){
			 
			int bits = p.getNbBits() - debitCourrant;
			if(p.getDebutEnvoie()>=p.getFinEnvoie() || p == null || bits<0){
				throw new IllegalArgumentException("paquet n'est pas valide");
			}
			
		}
	
		public void envoiePaquet(){
			
			int bitsAenvoyer = this.getPacketActuel().getNbBits() - debitCourrant;
			
			if(bitsAenvoyer == 0)
				this.paquetEnvoye();

			this.getPacketActuel().setNbBits(bitsAenvoyer);

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

		
		public Paquet getPacketActuel() {

			return fifo.peek();  // premier paquet du fifo

		}

		public List<Paquet> getPaquetsAenvoyer() {
			return paquetAenvoyer;
		}
		
		/*
		 * Enlever un paquet envoyé du buffer
		 *  
		 * */
				
		public void paquetEnvoye() {

			getPacketActuel().setFinEnvoie(pA.getTemps());
			this.paquetAenvoyer.add(getPacketActuel());
			fifo.removeFirst();

		}

		public int getMoyenProche() {
			return moyenProche;
		}

		public void setMoyenProche(int moyenProche) {
			this.moyenProche = moyenProche;
		}

}
