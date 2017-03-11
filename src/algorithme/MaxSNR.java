package algorithme;

import simulation.Cellule;
import simulation.helper.Rnd;
import static simulation.Simulation.NB_PORTEUSES;
import simulation.UR;
import simulation.Utilisateur;
import simulation.helper.Print;

public class MaxSNR extends Algorithme { 
    private Utilisateur utilPlusProche = null;
    private int offset = 0;

    public MaxSNR(Cellule cellule) {
        super(cellule);
    }  
    
    private UR allouerUR() {
        UR ur = cellule.getURlibre();
        /*Utilisateur utilActuelProche = this.cellule.getUtilProcheAttPaquet(0);
        
        if(this.utilPlusProche == null || utilActuelProche != this.utilPlusProche) {
            this.utilPlusProche = utilActuelProche;
        }
        
        if(this.utilPlusProche != null) {
            // On verifie que l'utilisateur aura toujours des bits à transmettre
            if(cellule.getNbBitsAEnvoyer(this.utilPlusProche) - this.getNbBitsATransmettre(this.utilPlusProche) > 0) {               
                this.addNbBitsATransmettre(this.utilPlusProche, ur.getNbBits());                 
            }
            else {
                this.utilPlusProche = this.cellule.getUtilProcheAttPaquet(1);
                this.addNbBitsATransmettre(this.utilPlusProche, ur.getNbBits()); 
            }
        }

        // Si on a trouvé l'utilisateur le plus proche qui a un paquet à envoyer, on lui alloue une UR
        if(this.utilPlusProche != null){
            this.affecterUR(ur, this.utilPlusProche);  
           // Helper.print(getName() + ": UR" + ur.getId() + " affectée Util" + this.utilPlusProche.getId() + " dist. " + this.utilPlusProche.getDistancePointAcces() + " - " + ur.getNbBits() + " bits");
        }
*/
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
