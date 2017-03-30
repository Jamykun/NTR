package algorithme;

import simulation.Cellule;

import static simulation.Simulation.NB_PORTEUSES;
import simulation.UR;
import simulation.Utilisateur;
import simulation.helper.Print;

public class MaxSNR extends Algorithme { 
    public MaxSNR(Cellule cellule) {
        super(cellule);
    }  
    
    /**
     * Alloue une UR à l'utilisateur qui a un paquet à recevoir et le meilleur mkn 
     * @return UR allouée. null si aucun utilisateur n'a besoin d'une UR
     */
    private UR allouerUR() {
        UR ur = cellule.getURlibre();
        
        int mknmax = -1;
        Utilisateur utilMknmax = null;
        
        // Pour chaque utilisateurs, on va regarder ceux qui ont des paquets à recevoir et celui qui a le meilleur mkn
        for(Utilisateur util : this.cellule.getUsers()) {
            // Si il a au moins un paquet à envoyer
            if(this.cellule.getNbBitAEnvoyer(util) > 0) { 
                // Si l'utilisateur aura encore des bits à recevoir à ce moment ci de l'allocation
                if(this.cellule.getNbBitAEnvoyer(util) - this.getNbBitsATransmettre(util) > 0) {
                    int mkn = util.getMkn();
                    //Print.print("Util " + util.getId() + " Mkn: " + mkn);
                    // On mémorise l'utilisateur si son mkn est supérieur aux autres
                    if(mknmax == -1 || mkn > mknmax) { 
                        mknmax = mkn;
                        utilMknmax = util;
                        //Print.print("Meilleur mkn temporaire à Util " + utilMknmax.getId() + " - UR : "+ur.getId() + " Mkn: " + mknmax);
                    }
                }
            }        	
        }
        
        // Si on a trouvé un utilisateur, on lui affecte l'UR
        if(utilMknmax != null) {            
            ur.setNbBits(mknmax); 
            this.addNbBitsATransmettre(utilMknmax, ur.getNbBits()); 
            this.affecterUR(ur, utilMknmax);             
        
            //Print.print("Affecté à Util " + utilMknmax.getId() + " - UR : "+ur.getId() + " Mkn: " + mknmax);
            Print.affectationUR(this, utilMknmax, ur);
        }
         
        return ur;
    }
    
    /**
     * On alloue les UR puis on les envoie
     */
    @Override
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
            //int nbBitsRestant = cellule.getNbBitAEnvoyer(ur.getUtilisateur()) - ur.getNbBits();
            //Print.print(getName() + ": UR" + ur.getId() + " > Util" + ur.getUtilisateur().getId() + " : " + ur.getNbBits() + " bits envoyés. Reste " + ((nbBitsRestant < 0) ? "0" : nbBitsRestant) + " bits");
 
            this.calculNbBitsTransmis(ur);  
            this.cellule.envoyerUR(ur);
        }  
        Print.forceAffectationUR();
    }    
}
