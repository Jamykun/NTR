package algorithme;

import java.util.ArrayList;
import java.util.HashMap;

import simulation.*;
import simulation.graphique.Graph_Temps_BitsUR;

import static simulation.Simulation.NB_PORTEUSES;

public abstract class Algorithme {
    protected ArrayList<UR> urAEnvoyer;
    protected Cellule cellule;
    
    // Statistiques
    protected int nbUrAffectee;
    private int nbBitsEnvoyeTimeslot = 0;
    protected HashMap<Utilisateur, Integer> nbBitsTransmisParUtil;
    protected HashMap<Utilisateur, Integer> nbBitsATransmettreParUtil;
    
    public Algorithme(Cellule cellule) {
        this.urAEnvoyer = new ArrayList<>();
        this.cellule = cellule;
        this.nbUrAffectee = 0;
        this.nbBitsTransmisParUtil = new HashMap<>();
        this.nbBitsATransmettreParUtil = new HashMap<>();
        this.cellule.setAlgorithme(this);
    }
    
    /**
     * @param util Utilisateur demandé
     * @return Nombre de bits à transmettre pour l'utilisateur util pour le timeslot courant
     */
    protected int getNbBitsATransmettre(Utilisateur util) {
        if(nbBitsATransmettreParUtil.containsKey(util)) {
            return nbBitsATransmettreParUtil.get(util);
        }
        return 0;
    }
    
    /**
     * Ajoute un nombre de bits à transmettre pour l'utilisateur util pour le timeslot courant
     * @param util Utilisateur désigné
     * @param nbBits Nombre de bits à ajouter
     */
    protected void addNbBitsATransmettre(Utilisateur util, int nbBits) {
    	this.nbBitsEnvoyeTimeslot += nbBits;
        int sum = 0;
        if(nbBitsATransmettreParUtil.containsKey(util)) {
            sum = nbBitsATransmettreParUtil.get(util);
        }
        nbBitsATransmettreParUtil.put(util, sum + nbBits);
    }
    
    /**
     * Additionne à la somme de l'utilisateur, le nombre de bits transmis par l'UR
     * @param ur UR transmise
     */
    protected void calculNbBitsTransmis(UR ur) {
        int nbBitsPrecTransmis = 0;
        if(this.nbBitsTransmisParUtil.containsKey(ur.getUtilisateur())) {
            nbBitsPrecTransmis = nbBitsTransmisParUtil.get(ur.getUtilisateur());
        }
        this.nbBitsTransmisParUtil.put(ur.getUtilisateur(), nbBitsPrecTransmis + ur.getNbBits());
    }
    
    /**
     * Affecte une ur à un utilisateur
     * @param ur
     * @param util 
     */
    protected void affecterUR(UR ur, Utilisateur util) {	
        ur.setAffectation(util);
        util.affecterUR(ur);        
    }
    
    /**
     * @return Nombre d'UR à envoyer dans le timeslot courant
     */
    public int getNbURUtiliseeTimeslot() {
    	return this.urAEnvoyer.size();
    }
    
    public int getNbBitsEnvoyeTimeslot() {
    	return this.nbBitsEnvoyeTimeslot;
    }
    
    /**
     * @return Taux d'utilisation des UR dans le timeslot courant
     */
    public double getTauxUtilisationUR() {
    	return (this.urAEnvoyer.size() / (double)NB_PORTEUSES) * 100;
    }
    
    /**
     * Calcul le débit de l'utilisateur depuis le dernier relevé
     * @param u
     * @return Debit en bits/tick moyen
     */
   /* public int getDebit(Utilisateur u){
        int time = Simulation.getTemps();
        int debit;
        if(time - time_dernierReleveDebit == 0) {
            debit = nbBitsTransmisParUtil.get(u);
        } 
        else {
            debit = nbBitsTransmisParUtil.get(u) / (time - time_dernierReleveDebit);
            time_dernierReleveDebit = time;
        }
        nbBitsTransmisParUtil.put(u, 0);
        return debit;
    }*/
    
    public void changerTimeslot() {
        this.urAEnvoyer.clear();
        this.nbBitsATransmettreParUtil.clear();
        this.nbBitsEnvoyeTimeslot = 0;
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

    /**
     * Allouer et envoyer les URs
     */
    public abstract void traiterTimeslot();
}
