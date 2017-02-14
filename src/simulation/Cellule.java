package simulation;

import java.util.ArrayList;
import java.util.List;

public class Cellule {
	private List<PointAcces> pointAcc;
	private int numero;
	
	public Cellule(int numero){
		this.pointAcc = new ArrayList<PointAcces>();
		this.numero = numero;
	}
	
	// return le nombre de points d'acces
	public int getNbPointAcces(){
		return pointAcc.size();
	}
	
	public void setAccessPoint(PointAcces pa) {		
		this.pointAcc.add(pa);
	}
	
	// return la liste des points d'acces 
	public List<PointAcces> getAccessPoints() {		
		return this.pointAcc;
	}
	
	public int getTemps(){
		return this.temps;
	}
}

