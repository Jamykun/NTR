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
		private Cellule cellule;
		private int moyenBruit;
		private int moyenPuissance;
		private int debitCourrant;

		/**
		 * Constructeur
		 * 
		 */
		public Utilisateur(int id, Cellule cellule, int moyenBruit, int moyenPuissance) {

			this.id = id;
			this.cellule = cellule;
			this.moyenBruit = moyenBruit;
			this.moyenPuissance = moyenPuissance;
			this.paquetAenvoyer = new ArrayList<Paquet>();
			fifo = new LinkedList<Paquet>();						
		}

		public void creerPaquet() {			
			Paquet p = new Paquet(this, 1234);
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
		
		public void envoiUR(UR ur) {
			System.out.println("UR" + ur.getId() + " reçu");
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
		 * Enlever un paquet envoy� du buffer
		 *  
		 * */
				
		public void paquetEnvoye() {

			getPacketActuel().setFinEnvoie(2345);
			this.paquetAenvoyer.add(getPacketActuel());
			fifo.removeFirst();

		}
		
		public int getSNR() {
			return moyenBruit / moyenPuissance;
		}
		
		public int getMoyenBruit() {
			return moyenBruit;
		}

		public void setMoyenBruit(int moyenBruit) {
			this.moyenBruit = moyenBruit;
		}

		public int getMoyenPuissance() {
			return moyenPuissance;
		}

		public void setMoyenPuissance(int moyenPuissance) {
			this.moyenPuissance = moyenPuissance;
		}
}
