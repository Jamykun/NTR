package simulation;

import java.util.ArrayList;
import java.util.List;

import simulation.helper.Rnd;

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
            if(this.distance == DistancePointAcces.PROCHE) {
            	return Rnd.rndint(4, 8);
            }
            else {
            	return Rnd.rndint(1, 8);
            }
        }
}
