package algorithme;

import java.util.Iterator;
import simulation.Cellule;
import simulation.DistancePointAcces;

import static simulation.Simulation.NB_PORTEUSES;
import simulation.UR;
import simulation.Utilisateur;
import simulation.helper.Print;
import simulation.helper.Rnd;

public class RR extends Algorithme {
    private Iterator<Utilisateur> it = null;
    
    public RR(Cellule cellule) {
        super(cellule);
    }  
    
    /**
     * Alloue une UR au prochain utilisateur qui a un paquet à recevoir
     * @return UR allouée. null si aucun utilisateur n'a besoin d'une UR
     */
    private UR allouerUR() {
        Utilisateur util = null;
        boolean paquetAEnvoyer = false;
        UR ur = cellule.getURlibre();        

        // Si on est arrivé au bout de la liste des utilisateurs, on retourne au debut de la liste
        if (this.it == null || !this.it.hasNext()) {
            this.it = this.cellule.getUsers().iterator();
        }
        
        //On recherche un utilisateur qui a un paquet à envoyer
        while(this.it.hasNext() && !paquetAEnvoyer){
            util = this.it.next();
            if(cellule.getNbBitAEnvoyer(util) > 0){
                // S'il y aura toujours des bits à transmettre à ce moment de l'allocation
                if(cellule.getNbBitAEnvoyer(util) - this.getNbBitsATransmettre(util) > 0) {
                    paquetAEnvoyer = true;                    
                }                
            }
        }

        // Si on a trouvé un utilisateur qui à un paquet à envoyer, on lui alloue une UR
        if(util != null && paquetAEnvoyer){
            /*if(util.getDistance() == DistancePointAcces.PROCHE){
            	ur.setNbBits(7); 
            }
            else {
            	ur.setNbBits(3); // L'UR peut transporter un nombre aléatoire de bits suivant les conditions radios
            }*/
            
            this.addNbBitsATransmettre(util, ur.getNbBits());
            this.affecterUR(ur, util);      
            Print.affectationUR(this, util, ur);
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
            int nbBitsRestant = cellule.getNbBitAEnvoyer(ur.getUtilisateur()) - ur.getNbBits();
            //Print.print(getName() + ": UR" + ur.getId() + " > Util" + ur.getUtilisateur().getId() + " : " + ur.getNbBits() + " bits envoyés. Reste " + ((nbBitsRestant < 0) ? "0" : nbBitsRestant) + " bits");
 
            this.calculNbBitsTransmis(ur);
            this.cellule.envoyerUR(ur);
        }       
        Print.forceAffectationUR();
    }    
}
