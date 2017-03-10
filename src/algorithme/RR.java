package algorithme;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import simulation.Cellule;
import simulation.Helper;
import static simulation.Simulation.NB_PORTEUSES;
import simulation.UR;
import simulation.Utilisateur;

public class RR extends Algorithme {
    private Iterator<Utilisateur> it = null;
    
    public RR(Cellule cellule) {
        super(cellule);
    }  
    
    private UR allouerUR() {
        Utilisateur util = null;
        boolean paquetAEnvoyer = false;
        UR ur = cellule.getURlibre();        

        // Si on est arrivé au bout de la liste des utilisateurs, on retourne au debut de la liste
       // if (this.it == null || !this.it.hasNext()) {
            this.it = this.cellule.getUsers().iterator();
       // }
        
        //On recherche un utilisateur qui a un paquet à envoyer
        while(this.it.hasNext() && !paquetAEnvoyer){
            util = this.it.next();
            if(cellule.getNbBitsAEnvoyer(util) > 0){
                // S'il y aura toujours des bits à transmettre
                if(cellule.getNbBitsAEnvoyer(util) - this.getNbBitsATransmettre(util) > 0) {
                    paquetAEnvoyer = true;
                    this.addNbBitsATransmettre(util, ur.getNbBits());
                }                
            }
        }

        // Si on a trouvé un utilisateur qui à un paquet à envoyer, on lui alloue une UR
        if(util != null && paquetAEnvoyer){
            this.affecterUR(ur, util);    
            Helper.print(getName() + ": UR" + ur.getId() + " affectée Util" + util.getId() + " - " + ur.getNbBits() + " bits");
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
            int nbBitsRestant = cellule.getNbBitsAEnvoyer(ur.getUtilisateur()) - ur.getNbBits();
            Helper.print(getName() + ": UR" + ur.getId() + " > Util" + ur.getUtilisateur().getId() + " : " + ur.getNbBits() + " bits envoyés. Reste " + ((nbBitsRestant < 0) ? "0" : nbBitsRestant) + " bits");
 
            this.cellule.envoyerUR(ur);
            this.calculNbBitsTransmis(ur);
        }       
    }    
}
