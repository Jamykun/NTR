package simulation;

public class Paquet {
	
	// taille de chaque paquet 
	public static final int PACKET_SIZE = 100;  	
	private int creation;
	private int debutEnvoie;
	private int finEnvoie;
	private int nbBits;
	private Utilisateur user;
	
	public Paquet(Utilisateur u, int creation) {
		this.creation = creation;
		this.nbBits = PACKET_SIZE;
		this.user = u;
	}
	
	public Paquet(Utilisateur u, int creation, int taille) {
		this.creation = creation;
		this.user = u;
		this.nbBits = taille;
	}

	public int getDebutEnvoie() {
		return this.debutEnvoie;
	}

	public void setDebutEnvoie(int time) {
		this.debutEnvoie = time;
	}

	public int getFinEnvoie() {
		return this.finEnvoie;
	}

	public void setFinEnvoie(int time) {
		this.finEnvoie = time;
	}

	public int getNbBits() {
		return this.nbBits;
	}

	public int getCreation() {
		return creation;
	}
	
}
