package simulation;

import java.util.ArrayList;
import java.util.List;

public class Paquet {
	
	// taille de chaque paquet 	
	//public static final int PACKET_SIZE = 100;  
	private int creation;
	private int debutEnvoie;
	private int finEnvoie;
	private int nbBits;
	private Utilisateur user;
	
	public Paquet(Utilisateur u, int taille) {
		this.creation = Simulation.getTemps();
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
	
	public void setNbBits(int nbBits) {
		this.nbBits = nbBits;
	}

	public int getCreation() {
		return creation;
	}
	
	public void subNbBits(int nbBits) {
		this.nbBits -= nbBits;
		if(this.nbBits < 0) {
			this.nbBits = 0;
		}
	}
	/*
	public ArrayList<UR> toUR(Cellule cellule) {
		ArrayList<UR> urs = new ArrayList<UR>();
		for(int i = 0; i < this.nbBits; i += PACKET_SIZE) {
			urs.add(new UR(i, cellule));
		}
		return urs;
	}	*/
}
