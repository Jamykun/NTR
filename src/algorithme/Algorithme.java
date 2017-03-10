package algorithme;

import java.util.ArrayList;
import java.util.HashMap;

import simulation.*;
import static simulation.Simulation.NB_PORTEUSES;

public class Algorithme {
    protected ArrayList<UR> urAEnvoyer;
    protected Cellule cellule;
    
    // Statistiques
    protected int nbUrAffectee;
    protected HashMap<Utilisateur, Integer> nbBitsTransmisParUtil;
    protected HashMap<Utilisateur, Integer> nbBitsATransmettreParUtil;
    private int time_dernierReleveDebit;
    
    public Algorithme(Cellule cellule) {
        this.urAEnvoyer = new ArrayList<>();
        this.cellule = cellule;
        this.nbUrAffectee = 0;
        this.nbBitsTransmisParUtil = new HashMap<>();
        this.nbBitsATransmettreParUtil = new HashMap<>();
        this.cellule.setAlgorithme(this);
    }
    
    protected int getNbBitsATransmettre(Utilisateur util) {
        if(nbBitsATransmettreParUtil.containsKey(util)) {
            return nbBitsATransmettreParUtil.get(util);
        }
        return 0;
    }
    
    protected void addNbBitsATransmettre(Utilisateur util, int nbBits) {
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
    
    protected void affecterUR(UR ur, Utilisateur util) {	
        ur.setAffectation(util);
        util.affecterUR(ur);        
    }
    
    public int getTauxUtilisationUR() {
	return this.urAEnvoyer.size() / NB_PORTEUSES;
    }
    
    /**
     * Calcul le débit de l'utilisateur depuis le dernier relevé
     * @param u
     * @return Debit en bits/tick moyen
     */
    public int getDebit(Utilisateur u){
        if(!nbBitsTransmisParUtil.containsKey(u)) {
            return 0;
        }

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
    }
    
    public void changerTimeslot() {
        this.urAEnvoyer.clear();
        this.nbBitsATransmettreParUtil.clear();
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }
}
