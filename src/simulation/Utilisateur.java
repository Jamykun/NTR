package simulation;

import java.util.ArrayList;
import java.util.List;

public class Utilisateur {	
        private final int id;
        private List<UR> urRecues = new ArrayList<>();
        private final Cellule cellule;
        private final DistancePointAcces distance;

        public Utilisateur(int id, Cellule cellule, DistancePointAcces distance) {
            this.id = id;
            this.cellule = cellule;
            this.distance = distance;
        }		
        
        public int getId() {
            return id;
        }
        
        public DistancePointAcces getDistance() {
            return this.distance;
        }
		
        public void affecterUR(UR ur) {			
            urRecues.add(ur);
        }
                
        public void clearURrecues() {
            urRecues.clear();
        }        
        
        public int getMkn() {
            int res = 0;
            if(this.distance == DistancePointAcces.PROCHE) {
                res = (int)(Math.random()*(5));
            }
            else {
                res = (int)(Math.random()*(6));
            }
            return res;
        }
}
