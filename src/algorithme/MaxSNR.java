package algorithme;

import simulation.Cellule;
import simulation.helper.Rnd;
import static simulation.Simulation.NB_PORTEUSES;
import simulation.UR;
import simulation.Utilisateur;
import simulation.helper.Print;

public class MaxSNR extends Algorithme { 
    private int offset = 0;

    public MaxSNR(Cellule cellule) {
        super(cellule);
    }  
    
    private UR allouerUR() {
        UR ur = cellule.getURlibre();
        
        int mknmax = -1;
        Utilisateur utilMknmax = null;
        for(Utilisateur util : this.cellule.getUsers()) {
        	if(this.cellule.getNbPaquetAEnvoyer(util) > 0) {
        		int mkn = util.getMkn();
        		if(mknmax == -1 || mkn > mknmax) {
            		mknmax = mkn;
            		utilMknmax = util;
            	}
        	}        	
        }
        
        
        if(utilMknmax != null) {
        	this.addNbBitsATransmettre(utilMknmax, ur.getNbBits()); 
        	this.affecterUR(ur, utilMknmax);  
        	//TODO: Corriger erreur 100% d'allocation d'UR
        	//Print.print(utilMknmax.getId() + " ("+ur.getId() + ") : " + mknmax);
        	Print.affectationUR(this, utilMknmax, ur);
        }
         
        return ur;
    }
    
    public void traiterTimeslot() {
        // Affectations
        for(int i = 0; i < NB_PORTEUSES; i++) {
            // Pour chaque porteuse, on alloue une UR
            UR ur = this.allouerUR();
            if(!ur.estLibre()) { // Si l'UR est affectée à un utilisateur, on l'ajouter à la file des UR à envoyer
                this.urAEnvoyer.add(ur);
            }
        }
        
        // Une fois que toutes les affectations ont été réalisées, ont peut envoyer les UR et ainsi traiter le timeslot courant
        for(UR ur : this.urAEnvoyer) {
            int nbBitsRestant = cellule.getNbBitAEnvoyer(ur.getUtilisateur()) - ur.getNbBits();
            //Print.print(getName() + ": UR" + ur.getId() + " > Util" + ur.getUtilisateur().getId() + " : " + ur.getNbBits() + " bits envoyés. Reste " + ((nbBitsRestant < 0) ? "0" : nbBitsRestant) + " bits");
 
            this.calculNbBitsTransmis(ur);  
            this.cellule.envoyerUR(ur);
        }  
        this.offset = 0;
    }    
}
