package simulation;

import algorithme.Algorithme;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import simulation.graphique.Graph_Temps_Delai;
import simulation.helper.Print;

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
    private int nbTotalURenvoye = 0;
    private int nbURutiliseeTimeslot = 0;
    
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
    
    public int getNbBitsFourniTimeslot() {
    	return nbBitsFourniTimeslot;
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

    public int getNbTotalURenvoye() {
        return this.nbTotalURenvoye;
    }

    public List<Utilisateur> getUsers() {
        ArrayList<Utilisateur> copy = new ArrayList<>(this.users.size());
        copy.addAll(this.users);
        return copy;
    }

    public int getNbURutiliseeTimeslot() {
        return this.nbURutiliseeTimeslot;
    }
    
    public void setAlgorithme(Algorithme algo) {
        this.algo = algo;
    }

    /**
     * Affecte les UR à aucun utilisateur
     */
    private void createListUR() {		
        for(int i = 0; i < NB_UR; i++) {
                this.ur.get(i).setAffectation(null);
        }
    }

    /**
     * Ajoute des paquets à un utilisateur
     * @param util Utilisateur recevant des paquets
     * @param nbBits Nombre de bits à ajouter. Des paquets de 100 bits seront créés et le surplus sera conservé pour la création d'un prochain paquet.
     */
    public void addPaquetsFromInternet(Utilisateur util, int nbBits) {
        if(nbBits > 0) {
            if(!buffersUsers.containsKey(util)) { // Si le buffer de l'utilisateur n'était pas créé
                buffersUsers.put(util, new LinkedList<>());
            }
            if(!bitsEnAttentesUsers.containsKey(util)) {
            	bitsEnAttentesUsers.put(util, 0);
            }
            
            int temp = nbBits + bitsEnAttentesUsers.get(util); // On récupère le surplus de bits précédemment ajouté
            bitsEnAttentesUsers.put(util, 0);
            this.nbTotalBitsGenere += nbBits;
            this.nbBitsGenereTimeslot += nbBits;
            while (temp > 100) { // On créé des paquets de 100 bits
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
     * Récupère le paquet à consommer dans le buffer d'un l'utilisateur
     * @param u Utilisateur choisi
     * @return Paquet en cours de consommation de l'utilisateur. null si son buffer est vide
     */
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

    /**
     * @param u Utilisateur demandé
     * @return Nombre de bits total prêt à être envoyé dans le buffer de l'utilisateur
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
     * @return Nombre total de paquet à envoyer pour l'ensemble des utilisateurs
     */
    public int getNbTotalPaquetAEnvoyer() {
        int sum = 0;
        for(Map.Entry<Utilisateur, Deque<Paquet>> item : buffersUsers.entrySet()) {
            sum += this.getNbPaquetAEnvoyer(item.getKey());
        }
        return sum;
    }
    
    /**
     * @param u Utilisateur demandé
     * @return Nombre de paquet prêt à être envoyé dans le buffer de l'utilisateur
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
     * @return L'UR libre. null si toutes les UR sont occupées
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
        ur.setAffectation(null);
        return ur;
    }

    public void addUtilisateur(Utilisateur user) {
        this.users.add(user);
    }

    /**
     * Libère les URs et met à 0 les statistiques sur chaque timeslot
     */
    public void changeTimeslot() {
        createListUR();
        for(int i = 0; i < users.size(); i++) {
                this.users.get(i).clearURrecues();
        }
        this.nbBitsGenereTimeslot = 0;
        this.nbBitsFourniTimeslot = 0;
        this.nbURutiliseeTimeslot = 0;
    }
        
    /**
     * @return Nombre total de bits envoyé depuis le début de la simulation
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
    
   /* public int getNbBitsParNbUR() {
        return this.nbTotalBitsEnvoye / this.nbTotalURutilisee;
    }*/

    /**
     * @param ur UR à envoyer
     */
    public void envoyerUR(UR ur) {    	
    	this.nbTotalURenvoye++; 	
    	this.nbURutiliseeTimeslot++;
        int nbBitsRestantUR = ur.getNbBits();
        Paquet paquetPrecedent = this.getPaquetActuel(ur.getUtilisateur());

        while(nbBitsRestantUR > 0) { // Tant que l'UR n'est pas pleinement utilisée
            Paquet paquetActuel = this.getPaquetActuel(ur.getUtilisateur());            
            if(paquetActuel == null) { // S'il n'y a plus de paquet à envoyer, on sort
                nbBitsRestantUR = 0;
            }
            else {
                if(paquetPrecedent != paquetActuel) { // Si on commence l'envoi d'un nouveau paquet
                    Print.paquetDebutEnvoi(ur, paquetActuel);                    
                }
                int nbBitsPaquetActuel = paquetActuel.getNbBitActuel();

                if(nbBitsPaquetActuel < ur.getNbBits()) { // Si l'UR peut envoyer le paquet actuel entièrement 
                    paquetActuel.subNbBits(nbBitsPaquetActuel); // On "envoi le paquet" : On retire des bits au paquet
                    this.nbTotalBitsEnvoye += nbBitsPaquetActuel;
                    this.nbBitsEnvoye50ticks += nbBitsPaquetActuel;
                    this.nbBitsFourniTimeslot += nbBitsPaquetActuel;
                    paquetActuel.addUrUtilisee(ur);
                    nbBitsRestantUR -= nbBitsPaquetActuel;
                    Print.paquetEnvoye(ur, paquetActuel, this.algo);                    
                    Graph_Temps_Delai.add(Simulation.getTemps(), paquetActuel.getDelai(), ur.getUtilisateur().getDistance()); 
                }
                else { // Si l'UR sera pleinement utilisée
                    paquetActuel.subNbBits(nbBitsRestantUR); // On "envoi le paquet" : On retire des bits au paquet
                    this.nbTotalBitsEnvoye += nbBitsRestantUR;
                    this.nbBitsEnvoye50ticks += nbBitsRestantUR;
                    this.nbBitsFourniTimeslot += nbBitsRestantUR;
                    paquetActuel.addUrUtilisee(ur);
                    nbBitsRestantUR = 0;
                    if(paquetActuel.getNbBitActuel() == 0) { // Si on a consommé entierement un paquet
                        Print.paquetEnvoye(ur, paquetActuel, this.algo);
                        Graph_Temps_Delai.add(Simulation.getTemps(), paquetActuel.getDelai(), ur.getUtilisateur().getDistance()); 
                    }
                }                
            }      
        }

        ur.setAffectation(null); // On libère l'UR
    }
}
