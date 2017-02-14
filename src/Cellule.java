

import java.util.ArrayList;
import java.util.List;

public class Cellule {
	private List<PointAcces> pointAcc;
	private int nombrePointAcces;
	
	public Cellule(){
		pointAcc = new ArrayList<PointAcces>();
	}
	
	// return le nombre de points d'cces
	public int nbpointacces(){
		return nombrePointAcces;
	}
	
	// return la liste des point d'acces 
	public List<PointAcces> getAccessPoints() {		
		return this.pointAcc;
	}
}

