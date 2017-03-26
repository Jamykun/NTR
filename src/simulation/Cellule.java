package simulation;

import helper.Rnd;
import algorithme.Algorithme;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import graphique.GraphCharge_Delai;
import helper.Print;

public class Cellule {	
    public final int NB_UR = 128;

    private List<UR> ur;
    private List<Utilisateur> users;
    private HashMap<Utilisateur, Deque<Paquet>> buffersUsers;
    private HashMap<Utilisateur, Integer> bitsEnAttentesUsers;
    private Algorithme algo;
    private int numero;   
    private int nbTotalPaquet = 0;
    private int nbBitsGenereTimeslot = 0;
    private int nbBitsFourniTimeslot = 0;
    private int nbTotalBitsGenere = 0;
    private int nbTotalBitsEnvoye = 0;
    private int nbBitsEnvoye50ticks = 0;
    private int nbTotalURutilisee = 0;
    
    public Cellule(int numero) {	
        this.numero = numero;
        this.ur = new ArrayList<>();
        for(int i = 0; i < NB_UR; i++) {
                this.ur.add(new UR(i, this));
        }
        this.users = new ArrayList<>();		
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

   /* public List<UR> getUrs() {
        return this.ur;
    }*/
    
    public void setAlgorithme(Algorithme algo) {
        this.algo = algo;
    }

    /**
     * Affecte les UR � aucun utilisateur
     */
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

    /**
     * Ajoute des paquets � un utilisateur
     * @param util Utilisateur recevant des paquets
     * @param nbBits Nombre de bits � ajouter. Des paquets de 100 bits seront cr��s et le surplus sera conserv� pour la cr�ation d'un prochain paquet.
     */
    public void addPaquetsFromInternet(Utilisateur util, int nbBits) {
        if(nbBits > 0) {
            if(!buffersUsers.containsKey(util)) { // Si le buffer de l'utilisateur n'�tait pas cr��
                buffersUsers.put(util, new LinkedList<>());
            }
            if(!bitsEnAttentesUsers.containsKey(util)) {
            	bitsEnAttentesUsers.put(util, 0);
            }
            
            int temp = nbBits + bitsEnAttentesUsers.get(util); // On r�cup�re le surplus de bits pr�c�demment ajout�
            bitsEnAttentesUsers.put(util, 0);
            this.nbTotalBitsGenere += nbBits;
            this.nbBitsGenereTimeslot += nbBits;
            while (temp > 100) { // On cr�� des paquets de 100 bits
                buffersUsers.get(util).add(new Paquet(this.nbTotalPaquet, 100));
                this.nbTotalPaquet++;
                temp -= 100;
            }   
            
            if(!bitsEnAttentesUsers.containsKey(util)) { // On sauvegarde le nombre de bits restants
            	bitsEnAttentesUsers.put(util, temp);
            }
            else {
            	bitsEnAttentesUsers.put(util, bitsEnAttentesUsers.get(util) + temp);
            }        
        }
    }

    /**
     * R�cup�re le paquet � consommer dans le buffer d'un l'utilisateur
     * @param u Utilisateur choisi
     * @return Paquet en cours de consommation de l'utilisateur. null si son buffer est vide
     */
    private Paquet getPaquetActuel(Utilisateur u) {
        // On r�cup�re la file d'attente de l'utilisateur
        Deque<Paquet> bufferUtil = buffersUsers.get(u);
        if(bufferUtil.isEmpty()) {
                return null;
        }
        // Si le paquet sortant de la file a �t� totalement consomm� et envoy� � l'utilisateur
        if(bufferUtil.getFirst().getNbBitActuel() == 0) {
            bufferUtil.removeFirst(); // On le supprime
            if(bufferUtil.isEmpty()) {
                return null;
            }
        }
        return bufferUtil.getFirst();
    }

    /**
     * @param u Utilisateur demand�
     * @return Nombre de bits total pr�t � �tre envoy� dans le buffer de l'utilisateur
     */
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
    
    /**
     * @return Nombre total de paquet � envoyer pour l'ensemble des utilisateurs
     */
    public int getNbTotalPaquetAEnvoyer() {
        int sum = 0;
        for(Map.Entry<Utilisateur, Deque<Paquet>> item : buffersUsers.entrySet()) {
            sum += this.getNbPaquetAEnvoyer(item.getKey());
        }
        return sum;
    }
    
    /**
     * @param u Utilisateur demand�
     * @return Nombre de paquet pr�t � �tre envoy� dans le buffer de l'utilisateur
     */
    public int getNbPaquetAEnvoyer(Utilisateur u) {
        int sum = 0;
        Deque<Paquet> bufferUtil = buffersUsers.get(u);
        if(bufferUtil == null) {
            return 0;
        }
        return bufferUtil.size();
    }

    /**
     * Demander une UR libre
     * @return L'UR libre. null si toutes les UR sont occup�es
     */
    public UR getURlibre() {
    	
        int i = 0;
        while(i < this.ur.size() && !this.ur.get(i).estLibre()) {
                i++;
        }
        if(i == this.ur.size()) {
                return null;
        }

        UR ur = this.ur.get(i);
        for (Utilisateur util : this.getUsers()){
    	
    	
        if(util.getDistance()==(DistancePointAcces.PROCHE)){
        	ur.setNbBits(Rnd.rndint(3, 7)); // L
        }
        else
        ur.setNbBits(Rnd.rndint(1, 5)); // L'UR peut transporter un nombre al�atoire de bits suivant les conditions radios
        }
        return ur;
    }

    public void addUtilisateur(Utilisateur user) {
        this.users.add(user);
    }

    /**
     * Lib�re les URs et met � 0 les statistiques sur chaque timeslot
     */
    public void changeTimeslot() {
        createListUR();
        for(int i = 0; i < users.size(); i++) {
                this.users.get(i).clearURrecues();
        }
        this.nbBitsGenereTimeslot = 0;
        this.nbBitsFourniTimeslot = 0;
    }
        
    /**
     * @return Nombre total de bits envoy� depuis le d�but de la simulation
     */
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
    	int rtn = this.nbBitsEnvoye50ticks / 50;
    	this.nbBitsEnvoye50ticks = 0;
    	return rtn;
    }
    
    public int getNbBitPaquetEnCreation(Utilisateur util) {
    	return this.bitsEnAttentesUsers.get(util);
    }
    
    public int getNbBitsParNbUR() {
        return this.nbTotalBitsEnvoye / this.nbTotalURutilisee;
    }

    /**
     * @param ur UR � envoyer
     */
    public void envoyerUR(UR ur) {
    	this.nbTotalURutilisee++;
        int nbBitsRestantUR = ur.getNbBits();
        Paquet paquetPrecedent = this.getPaquetActuel(ur.getUtilisateur());

        while(nbBitsRestantUR > 0) { // Tant que l'UR n'est pas pleinement utilis�e
            Paquet paquetActuel = this.getPaquetActuel(ur.getUtilisateur());            
            if(paquetActuel == null) { // S'il n'y a plus de paquet � envoyer, on sort
                nbBitsRestantUR = 0;
            }
            else {
                if(paquetPrecedent != paquetActuel) { // Si on commence l'envoi d'un nouveau paquet
                    Print.paquetDebutEnvoi(ur, paquetActuel);                    
                }
                int nbBitsPaquetActuel = paquetActuel.getNbBitActuel();

                if(nbBitsPaquetActuel < ur.getNbBits()) { // Si l'UR peut envoyer le paquet actuel enti�rement 
                    paquetActuel.subNbBits(nbBitsPaquetActuel); // On "envoi le paquet" : On retire des bits au paquet
                    this.nbTotalBitsEnvoye += nbBitsPaquetActuel;
                    this.nbBitsEnvoye50ticks += nbBitsPaquetActuel;
                    this.nbBitsFourniTimeslot += nbBitsPaquetActuel;
                    paquetActuel.addUrUtilisee(ur);
                    nbBitsRestantUR -= nbBitsPaquetActuel;
                    Print.paquetEnvoye(ur, paquetActuel, this.algo);                    
                    GraphCharge_Delai.add(paquetActuel.getId(),paquetActuel.getDelai()); 
                }
                else { // Si l'UR sera pleinement utilis�e
                    paquetActuel.subNbBits(nbBitsRestantUR); // On "envoi le paquet" : On retire des bits au paquet
                    this.nbTotalBitsEnvoye += nbBitsPaquetActuel;
                    this.nbBitsEnvoye50ticks += nbBitsPaquetActuel;
                    this.nbBitsFourniTimeslot += nbBitsRestantUR;
                    paquetActuel.addUrUtilisee(ur);
                    nbBitsRestantUR = 0;
                    if(paquetActuel.getNbBitActuel() == 0) { // Si on a consomm� entierement un paquet
                        Print.paquetEnvoye(ur, paquetActuel, this.algo);
                        GraphCharge_Delai.add(paquetActuel.getId(), paquetActuel.getDelai()); 
                    }
                }  
            }      
        }

        ur.setAffectation(null); // On lib�re l'UR
    }
}
