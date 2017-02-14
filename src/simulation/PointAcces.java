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
	private int nbPaquet;
	
	public PointAcces(Cellule c) {
		ur = new ArrayList<UR>();
		users = new ArrayList<Utilisateur>();
		this.cellule = c;
		this.nbPaquet = 0;		
	}		

	public List<UR> getUr() {
		return this.ur;
	}

	public List<Utilisateur> getUsers() {
		return this.users;
	}

}
