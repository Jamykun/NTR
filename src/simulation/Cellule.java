package simulation;

import simulation.helper.Rnd;
import algorithme.Algorithme;
import static java.lang.Math.ceil;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import simulation.graphique.GraphChargeDelai;
import simulation.helper.Print;

public class Cellule {	
    public final int NB_UR = 128;

    private List<UR> ur;
    private List<Utilisateur> users;
    private HashMap<Utilisateur, Deque<Paquet>> buffersUsers;
    private HashMap<Utilisateur, Integer> bitsEnAttentesUsers;
    private Algorithme algo;
    private int numero;
    private int timeslotOffset = 0;
    private int nbTotalPaquet = 0;
    private int nbBitsGenereTimeslot = 0;
    private int nbBitsFourniTimeslot = 0;
    private int nbTotalBitsGenere = 0;
    private int nbTotalBitsEnvoye = 0;
    private int nbTotalURutilisee = 0;
    
    public Cellule(int numero, int timeslotOffset) {	
        this.numero = numero;
        this.ur = new ArrayList<>();
        for(int i = 0; i < NB_UR; i++) {
                this.ur.add(new UR(i, this));
        }
        this.users = new ArrayList<>();		
        this.timeslotOffset = timeslotOffset;
        this.buffersUsers = new HashMap<>();
        this.bitsEnAttentesUsers = new HashMap<>();
    }
    
    public int getNumero() {
        return this.numero;
    }
    
    public int getNbTotalPaquetGenere() {
        return this.nbTotalPaquet;
    }

    public int getNbTotalBitsGenere() {
        return this.nbTotalBitsGenere;
    }
    
    public int getNbTotalBitsEnvoye() {
        return this.nbTotalBitsEnvoye;
    }

    public int getNbTotalURutilisee() {
        return this.nbTotalURutilisee;
    }

    public List<Utilisateur> getUsers() {
        ArrayList<Utilisateur> copy = new ArrayList<>(this.users.size());
        copy.addAll(this.users);
        return copy;
    }

    public List<UR> getUr() {
            return this.ur;
    }
    
    public void setAlgorithme(Algorithme algo) {
        this.algo = algo;
    }

    private void createListUR() {		
        for(int i = 0; i < NB_UR; i++) {
                this.ur.get(i).setAffectation(null);
        }
    }

    /*public void addPaquetsFromInternet() {
        for(int i = 0; i < users.size(); i++) {
            int nbBits = ThreadLocalRandom.current().nextInt(10, 1000);
            this.addPaquetsFromInternet(users.get(i), nbBits);
        }		
    }*/

    public void addPaquetsFromInternet(Utilisateur util, int nbBits) {
        if(nbBits > 0) {
            if(!buffersUsers.containsKey(util)) {
                buffersUsers.put(util, new LinkedList<>());
            }
            if(!bitsEnAttentesUsers.containsKey(util)) {
            	bitsEnAttentesUsers.put(util, 0);
            }
            
            int temp = nbBits + bitsEnAttentesUsers.get(util);
            bitsEnAttentesUsers.put(util, 0);
            this.nbTotalBitsGenere += nbBits;
            this.nbBitsGenereTimeslot += nbBits;
            while (temp > 100) {
                buffersUsers.get(util).add(new Paquet(this.nbTotalPaquet, 100));
                this.nbTotalPaquet++;
                temp -= 100;
            }   
            
            if(!bitsEnAttentesUsers.containsKey(util)) {
            	bitsEnAttentesUsers.put(util, temp);
            }
            else {
            	bitsEnAttentesUsers.put(util, bitsEnAttentesUsers.get(util) + temp);
            }        
        }
    }

    private Paquet getPaquetActuel(Utilisateur u) {
        // On récupère la file d'attente de l'utilisateur
        Deque<Paquet> bufferUtil = buffersUsers.get(u);
        if(bufferUtil.isEmpty()) {
                return null;
        }
        // Si le paquet sortant de la file a été totalement consommé et envoyé à l'utilisateur
        if(bufferUtil.getFirst().getNbBitActuel() == 0) {
            bufferUtil.removeFirst(); // On le supprime
            if(bufferUtil.isEmpty()) {
                return null;
            }
        }
        return bufferUtil.getFirst();
    }

    public int getNbBitAEnvoyer(Utilisateur u) {
        int sum = 0;
        Deque<Paquet> bufferUtil = buffersUsers.get(u);
        if(bufferUtil == null) {
            return 0;
        }
        for(Paquet p : bufferUtil) {
            sum += p.getNbBitActuel();
        }
        return sum;
    }
    
    public int getNbTotalPaquetAEnvoyer() {
        int sum = 0;
        for(Map.Entry<Utilisateur, Deque<Paquet>> item : buffersUsers.entrySet()) {
            sum += this.getNbPaquetAEnvoyer(item.getKey());
        }
        return sum;
    }
    
    public int getNbPaquetAEnvoyer(Utilisateur u) {
        int sum = 0;
        Deque<Paquet> bufferUtil = buffersUsers.get(u);
        if(bufferUtil == null) {
            return 0;
        }
        return bufferUtil.size();
    }

    // Paquet demande une UR libre
    public UR getURlibre() {
        int i = 0;
        while(i < this.ur.size() && !this.ur.get(i).estLibre()) {
                i++;
        }
        if(i == this.ur.size()) {
                return null;
        }

        UR ur = this.ur.get(i);
        ur.setNbBits(Rnd.rndint(1, 7)); // L'UR peut transporter un nombre aléatoire de bits suivant les conditions radios
        return ur;
    }

    public void addUtilisateur(Utilisateur user) {
        this.users.add(user);
    }

    public void changeTimeslot() {
        createListUR();
        for(int i = 0; i < users.size(); i++) {
                this.users.get(i).clearURrecues();
        }
        this.nbBitsGenereTimeslot = 0;
        this.nbBitsFourniTimeslot = 0;
    }
        
    public int getDebitGlobal(){
    	return (nbTotalPaquet * 100) / Simulation.getTemps();
    }
    
    public int getDebitGenere(){
    	//if (Simulation.getTemps()==0 )
    	return this.nbBitsGenereTimeslot /*/ (Simulation.getTemps()+1)*/;
    	/*else 
    		return (this.nbBitsGenereTimeslot * 100) / Simulation.getTemps();*/
    		
    }
    
    public int getDebitFourni(){
    	return this.nbTotalBitsEnvoye / (Simulation.getTemps()+1);
    }
    
    public int getNbBitPaquetEnCreation(Utilisateur util) {
    	return this.bitsEnAttentesUsers.get(util);
    }

    public void envoyerUR(UR ur) {
    	this.nbTotalURutilisee++;
        int nbBitsRestantUR = ur.getNbBits();
        Paquet paquetPrecedent = this.getPaquetActuel(ur.getUtilisateur());

        while(nbBitsRestantUR > 0) {
            Paquet paquetActuel = this.getPaquetActuel(ur.getUtilisateur());            
            if(paquetActuel == null) { // S'il n'y a plus de paquet à envoyer, on sort
                nbBitsRestantUR = 0;
            }
            else {
                if(paquetPrecedent != paquetActuel) {
                    Print.paquetDebutEnvoi(ur, paquetActuel);                    
                }
                int nbBitsPaquetActuel = paquetActuel.getNbBitActuel();

                if(nbBitsPaquetActuel < ur.getNbBits()) {
                    paquetActuel.subNbBits(nbBitsPaquetActuel);
                    this.nbTotalBitsEnvoye += nbBitsPaquetActuel;
                    this.nbBitsFourniTimeslot += nbBitsPaquetActuel;
                    paquetActuel.addUrUtilisee(ur);
                    nbBitsRestantUR -= nbBitsPaquetActuel;
                    Print.paquetEnvoye(ur, paquetActuel, this.algo);                    
                    GraphChargeDelai.add(paquetActuel.getId(),paquetActuel.getDelai()); 
                }
                else {
                    paquetActuel.subNbBits(nbBitsRestantUR);
                    this.nbTotalBitsEnvoye += nbBitsPaquetActuel;
                    this.nbBitsFourniTimeslot += nbBitsRestantUR;
                    paquetActuel.addUrUtilisee(ur);
                    nbBitsRestantUR = 0;
                    if(paquetActuel.getNbBitActuel() == 0) {
                        Print.paquetEnvoye(ur, paquetActuel, this.algo);
                        GraphChargeDelai.add(paquetActuel.getId(), paquetActuel.getDelai()); 
                    }
                }  
            }      
        }

        ur.setAffectation(null);
    }
}
