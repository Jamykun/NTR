package simulation;

import java.util.ArrayList;

public class Paquet {
    private int id;
    private int debutEnvoie;
    private int finEnvoie = -1;
    private int nbBitActuel;
    private int nbBitOrigine;
    private ArrayList<UR> urUtilisees = new ArrayList<>();

    public Paquet(int id, int taille) {
        this.debutEnvoie = Simulation.getTemps();
        this.nbBitActuel = taille;
        this.nbBitOrigine = taille;
        this.id = id;
    }
           
    public int getId() {
            return this.id;
    }

    public int getDebutEnvoie() {
            return this.debutEnvoie;
    }

    public int getFinEnvoie() {
            return this.finEnvoie;
    }

    /**
     * @return Nombre de bits restant à consommer
     */
    public int getNbBitActuel() {
            return this.nbBitActuel;
    }
    
    public int getNbBitOrigine() {
            return this.nbBitOrigine;
    }

    /**
     * @return Nombre de timeslot sur lequels le paquet s'est envoyé. -1 si le paquet n'est pas encore totalement envoyé à l'utilisateur.
     */
    public int getDelai() {
        if(this.finEnvoie == -1) {
            return -1;
        }
        return this.finEnvoie - this.debutEnvoie;
    }

    /**
     * Soustrait nbBits au paquet. Si le nombre de bits devient <= 0, FinEnvoie est mis à jour au temps courant
     * @param nbBits Nombre de bits à soustraire
     */
    public void subNbBits(int nbBits) {
        this.nbBitActuel -= nbBits;
        if(this.nbBitActuel <= 0) { // Quand le paquet a été entièrement envoyé
            this.nbBitActuel = 0;    
            if(this.finEnvoie == -1) { 
                this.finEnvoie = Simulation.getTemps();               
            }
        } 
    }
    
    /**
     * Utiliser pour les statistiques
     * @param ur 
     */
    public void addUrUtilisee(UR ur) {
        urUtilisees.add(ur);
    }
    
    /**
     * Utiliser pour les statistiques 
     * @return String Liste des URs utilisées pour envoyer le paquet
     */
    public String getUrUtilisees() {
        StringBuilder stb = new StringBuilder();
        int i = 0;
        int sum = 0;
        while(i < this.urUtilisees.size()) {
            stb.append(this.urUtilisees.get(i).getId());
            if(i < this.urUtilisees.size() -1) {
                stb.append("-");
            }            
            sum += this.urUtilisees.get(i).getNbBits();
            i++;
        }
        return stb.toString()+" = "+sum+" bits";
    }
}
