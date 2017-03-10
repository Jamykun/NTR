package simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import simulation.graphique.GraphChargeDelai;

public class Paquet {
        private int id;
	private int creation;
	private int debutEnvoie = -1;
	private int finEnvoie = -1;
	private int nbBits;
	
	public Paquet(int id, int taille) {
            this.creation = Simulation.getTemps();
            if(taille > 100) {
                try {
                    throw new Exception("La taille d'un paquet ne doit pas dépasser 100 bits");
                } catch (Exception ex) {
                    Logger.getLogger(Paquet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            this.nbBits = taille;
            this.id = id;
	}
           
    public int getId() {
		return this.id;
	}
     
	public int getDebutEnvoie() {
		return this.debutEnvoie;
	}

	public void setDebutEnvoie() {
		this.debutEnvoie = Simulation.getTemps();
	}

	public int getFinEnvoie() {
		return this.finEnvoie;
	}

	public int getNbBits() {
		return this.nbBits;
	}

	public int getCreation() {
		return creation;
	}
        
    public int getDelai() {
        if(this.debutEnvoie == -1 || this.finEnvoie == -1) {
            return -1;
        }
        return this.finEnvoie - this.debutEnvoie + 1;
	}
	
        /**
         * Soustrait nbBits au paquet. Si le nombre de bits devient <= 0, FinEnvoie est mis à jour au temps courant
         * @param nbBits Nombre de bits à soustraire
         */
	public void subNbBits(int nbBits) {
        this.nbBits -= nbBits;
        if(this.nbBits < 0) {
            this.nbBits = 0;
            this.finEnvoie = Simulation.getTemps();
            GraphChargeDelai.addDelaiActuel(this.getDelai());            
        }       
            
	}
}
