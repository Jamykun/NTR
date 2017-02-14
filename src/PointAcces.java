package allocation;





import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PointAcces {
	
	public static final int NB_UR = 128;
	
	// Parameters
		private List<ur> ur;
		private List<utilisateur> users;
		private cellule cellule;
		private int nbPaquet;
		
		public PointAcces(cellule c) {

			ur = new ArrayList<ur>();
			users = new ArrayList<utilisateur>();
			this.cellule = c;
			this.nbPaquet = 0;
			
		}
		

		public List<ur> getUr() {
			return this.ur;
		}

		public List<utilisateur> getUsers() {
			return this.users;
		}
		
		
		 



}
