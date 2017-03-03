package simulation;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Utilisateur {
	
	// Parameters
		private int id;
		private List<Paquet> paquetAenvoyer;
		private List<UR> urRecues;
		private Deque<Paquet> fifo;
		private Cellule cellule;
		private int debitCourrant;
		private int moyenBruit;
		private int moyenPuissance;
		private DistancePointAcces ProcheLoin;
		

		/**
		 * Constructeur
		 * 
		 */
		public Utilisateur(int id, Cellule cellule, DistancePointAcces d) {

			this.id = id;
			this.cellule = cellule;
			//this.moyenBruit = moyenBruit;
			//this.moyenPuissance = moyenPuissance;
			this.paquetAenvoyer = new ArrayList<Paquet>();
			this.urRecues = new ArrayList<UR>();
			fifo = new LinkedList<Paquet>();
			this.ProcheLoin = d;
						
		}
		
		// A expliquer.....
		
		public void envoiUR(UR ur) {
			System.out.println("UR" + ur.getId() + " reçu");
			urRecues.add(ur);
			if(fifo.size() > 0) {
				Paquet p = fifo.peek();
				p.setDebutEnvoie(Simulation.getTemps());
				cellule.sendPaquet(p);
			}
		}
		public void clearUR() {
			urRecues.clear();
		}

		public void creerPaquet() {
			
			Paquet p = new Paquet(this, Simulation.getTemps(), 1024);
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

			getPacketActuel().setFinEnvoie(2345);
			this.paquetAenvoyer.add(getPacketActuel());
			fifo.removeFirst();

		}
		public int getSNR() {
			return moyenBruit / moyenPuissance;
		}
//		public int getMoyenBruit() {
//			return moyenBruit;
//		}
//
//		public void setMoyenBruit(int moyenBruit) {
//			this.moyenBruit = moyenBruit;
//		}
//
//		public int getMoyenPuissance() {
//			return moyenPuissance;
//		}
//
//		public void setMoyenPuissance(int moyenPuissance) {
//			this.moyenPuissance = moyenPuissance;
//		}
		public DistancePointAcces getProcheLoin() {
			return this.ProcheLoin;
		}

//		public void setMoyenProche(int moyenProche) {
//			this.moyenProche = moyenProche;
//		}

}
