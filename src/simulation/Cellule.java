package simulation;


import java.util.ArrayList;
import java.util.List;

public class Cellule {
	private List<PointAcces> pointAcc;
	
	public Cellule(){
		pointAcc = new ArrayList<PointAcces>();
	}
	
	// return le nombre de points d'acces
	public int getNbPointAcces(){
		return pointAcc.size();
	}
	
	// return la liste des points d'acces 
	public List<PointAcces> getAccessPoints() {		
		return this.pointAcc;
	}
}

