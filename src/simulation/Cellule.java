package simulation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cellule {	
	public final int NB_UR = 128;
	
	private List<UR> ur;
	private List<Utilisateur> users;
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
	}
	
	private void createListUR() {
		this.ur = new ArrayList<UR>();
		for(int i = 0; i < NB_UR * timeslotOffset; i++) {
			this.ur.add(new UR(i, this));
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
	}
}
