package simulation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PointAcces {	
	public static final int NB_UR = 128;
	
	// Parameters
	private List<UR> ur;
	private List<Utilisateur> users;
	private Cellule cellule;
	private int nbPaquetTotal;
	
	public PointAcces(Cellule cellule) {		
		this.ur = new ArrayList<UR>();
		for(int i = 0; i < NB_UR; i++) {
			this.ur.add(new UR(i, this));
		}
		
		this.users = new ArrayList<Utilisateur>();
		this.cellule = cellule;
		this.nbPaquetTotal = 0;		
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

	public List<UR> getUrs() {
		return this.ur;
	}

	public List<Utilisateur> getUsers() {
		return this.users;
	}
	
	public void setUtilisateur(Utilisateur user) {
		this.users.add(user);
	}
}
