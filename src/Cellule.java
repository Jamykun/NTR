package allocation;

import java.util.ArrayList;
import java.util.List;

public class cellule {
	private List<PointAcces> pointAcc;
	private int nombrePointAcces;
	
	public cellule(){
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

