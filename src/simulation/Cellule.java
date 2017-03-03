package simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Cellule {	
	public final int NB_UR = 128;
	
	private List<UR> ur;
	private List<Utilisateur> users;
	private HashMap<Utilisateur, Paquet> buffersUsers;
	private int nbPaquetTotal;
	private int numero;
	private int timeslotOffset = 0;

	
	public Cellule(int numero, int timeslotOffset) {	
		this.numero = numero;
		this.ur = new ArrayList<UR>();
		for(int i = 0; i < NB_UR; i++) {
			this.ur.add(new UR(i, this));
		}
		
		this.users = new ArrayList<Utilisateur>();
		this.nbPaquetTotal = 0;		
		this.timeslotOffset = timeslotOffset;
		this.buffersUsers = new HashMap<>();
	}
	
	private void createListUR() {		
		for(int i = 0; i < NB_UR; i++) {
			this.ur.get(i).setAffectation(null);
		}
	}
	
	public void addPaquetsFromInternet() {
		for(int i = 0; i < users.size(); i++) {
			Utilisateur util = users.get(i);
			int length = ThreadLocalRandom.current().nextInt(1, 100000000);
			buffersUsers.put(util, new Paquet(util, length));
		}		
	}
	
	// Paquet demande une UR libre
	public UR getURlibre() {
		int i = 0;
		while(i < this.ur.size() && !this.ur.get(i).estLibre()) {
			i++;
		}
		
		if(i == this.ur.size())
			return null;
		return this.ur.get(i);
	}
	
	public void setUtilisateur(Utilisateur user) {
		this.users.add(user);
	}
	
	public void changeTimeslot() {
		createListUR();
		for(int i = 0; i < users.size(); i++) {
			this.users.get(i).clearUR();
		}
	}
	
	
	public void sendPaquet(Paquet paquetEnvoye) {
		paquetEnvoye.setDebutEnvoie(Simulation.getTemps());
		// TODO : Latence
		paquetEnvoye.setFinEnvoie(Simulation.getTemps());
		System.out.println("Cellule : Paquet envoyé " + paquetEnvoye.toString());
	}
}
