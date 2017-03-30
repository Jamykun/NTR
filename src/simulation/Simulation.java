package simulation;
import algorithme.Algorithme;
import algorithme.MaxSNR;
import algorithme.RR;
import simulation.graphique.Graph_Temps_Delai;
import simulation.graphique.Graph_Temps_DebitFourni;
import simulation.graphique.Graph_Temps_Debit;
import simulation.graphique.GraphDebit_NbPaquet_Utilisateur;
import simulation.graphique.Graph_Temps_BitsUR;
import simulation.graphique.Graph_Temps_TauxUR;
import java.io.IOException;
import java.util.ArrayList;
import simulation.helper.Print;
import simulation.helper.Rnd;

public class Simulation {
    public static String ALGO = "MaxSNR";
    public static final boolean PRINT = false;
    
    public static final int NB_PORTEUSES = 128;
    public static final int SIMULATION_TIMESLOTS = 2000;
    public static final int NB_TIMESLOT_TRAITEE = 5;
    public static int NB_UTILISATEURS = 4;
    public static final int DEBIT_GENERE_MOYEN = 50;
    private static int tick;
    private static int timeslot;
    private static ArrayList<GraphDebit_NbPaquet_Utilisateur> graphsDebitUtilisateurs = new ArrayList<>();
    private static Algorithme algo;

    public static void main(String[] args) throws IOException {
        // Choix des options de la simulation
        if(args.length == 2) {
            NB_UTILISATEURS = Integer.valueOf(args[0]);
            ALGO = args[1];
        }
        
        Cellule cellule0 = new Cellule(0);	
        if(ALGO.toUpperCase().equals("RR")) {
            algo = new RR(cellule0);
        } else {
            algo = new MaxSNR(cellule0);
        }
        
        // Création des utilisateurs. Autant d'utilisateur proche que loin
        DistancePointAcces dist = DistancePointAcces.PROCHE;
        for(int i = 0; i < NB_UTILISATEURS; i++) {
            Utilisateur util = new Utilisateur(i, cellule0, dist);
            dist = DistancePA.change(dist);
            cellule0.addUtilisateur(util);         
            Print.creationUtil(util);
            
            graphsDebitUtilisateurs.add(new GraphDebit_NbPaquet_Utilisateur(util));
        }

        int intervalMAJdebit = 0;
        int nbBitsInterval = 0;
        int debitGenere = 0;
        for(int for_tick = 0; for_tick < SIMULATION_TIMESLOTS; for_tick += NB_TIMESLOT_TRAITEE) {
            Simulation.setTick(for_tick);
            Print.print("\nTimeslot " + Simulation.tick + " à " + (Simulation.tick+NB_TIMESLOT_TRAITEE));                        
            
            for(int for_timeslot = 0; for_timeslot < NB_TIMESLOT_TRAITEE; for_timeslot++) {
                Simulation.setTimeslot(for_timeslot);                
                Print.print("\nTimeslot " + (Simulation.tick + Simulation.timeslot));
                
                // Génération d'un débit aléatoire
                // A interval régulier, on change la valeur du débit moyen toujours dans un interval autour de DEBIT_GENERE_MOYEN
                // afin d'obtenir un débit qui varie de façon vraiment aléatoire
                intervalMAJdebit--;
                if(intervalMAJdebit == -1) {
                    intervalMAJdebit = Rnd.rndint(1,100);
                    nbBitsInterval = /*Rnd.rndint(*/DEBIT_GENERE_MOYEN/*-(DEBIT_GENERE_MOYEN / 4), DEBIT_GENERE_MOYEN+(DEBIT_GENERE_MOYEN / 4))*/;
                }                 
                
                               
                for(Utilisateur util : cellule0.getUsers()) {
                    int nbBits = Rnd.rndint(nbBitsInterval-(nbBitsInterval / 8), nbBitsInterval+(nbBitsInterval / 8));
                    debitGenere += nbBits;
                    cellule0.addPaquetsFromInternet(util, nbBits);
                    Print.print("Utilisateur " + util.getId() + " > Ajout de " + nbBits + " bits dans son buffer. Buffer : " + cellule0.getNbPaquetAEnvoyer(util) + " paquet(s) / Paquet en création : " + cellule0.getNbBitPaquetEnCreation(util) + " bits");
                }                
                
                // On traite le timeslot
                algo.traiterTimeslot();
                              

                // Mémorisation des valeurs pour les graphiques
                if(getTemps() % 50 == 0) {
                    Graph_Temps_DebitFourni.add(Simulation.getTemps(), cellule0.getDebitFourni());
                }                
                Graph_Temps_TauxUR.add(getTemps(), algo.getTauxUtilisationUR());
                Graph_Temps_Debit.add(getTemps(), (cellule0.getNbTotalURenvoye() == 0) ? 0 : (cellule0.getNbTotalBitsEnvoye() / cellule0.getNbTotalURenvoye()));
                
                double bitsParUR = (cellule0.getNbURutiliseeTimeslot() == 0) ? 0 : cellule0.getNbBitsFourniTimeslot() / (double)cellule0.getNbURutiliseeTimeslot();
                //if(cellule0.getNbBitsFourniTimeslot() > 0) {
                    Graph_Temps_BitsUR.add(Simulation.getTemps(), bitsParUR);
                //}
                
                Print.print("Nombre de paquet traité : " + cellule0.getNbTotalPaquetGenere());
                Print.print("Nb bits fourni dans le timeslot : " + cellule0.getNbBitsFourniTimeslot());
                Print.print("Taux utilisation des UR : " + algo.getTauxUtilisationUR() + "%");   
                Print.print("Débit généré : " + cellule0.getDebitGenere());
                Print.print("Nb UR totalement utilisée : " + cellule0.getNbTotalURenvoye());
                Print.print("Nb UR utilisée dans le timeslot : " + cellule0.getNbURutiliseeTimeslot());
                Print.print("Bits/UR timeslot : " + bitsParUR);                
                
                algo.changerTimeslot(); 
                cellule0.changeTimeslot();	
                Print.changerTimeslot();
            }
            Print.print("Debit global : " +  cellule0.getDebitGlobal());      
            
            Simulation.setTimeslot(0);            
        }
        Print.print("Nombre de paquet généré : " + cellule0.getNbTotalPaquetGenere() + " paquets / " + cellule0.getNbTotalBitsGenere() + " bits"/* +" => " + ((float)cellule0.getNbTotalBitsGenere()/(float)cellule0.getNbTotalPaquetGenere()) + "%"*/);
        Print.print("Nombre de paquet restant dans les buffers : " + cellule0.getNbTotalPaquetAEnvoyer());
        Print.print("Nombre de bits totalement généré : " + debitGenere);
        if(args.length != 2) {
            System.out.println("Fin de simulation");
        }     
        //Graph_Temps_BitsUR.add(Simulation.getTemps(), (cellule0.getNbTotalBitsEnvoye() / cellule0.getNbTotalURenvoye()));
        
        //Génération des graphiques
        Graph_Temps_TauxUR.GenerateGraph();
        Graph_Temps_BitsUR.GenerateGraph();
        Graph_Temps_Delai.GenerateGraph();
        Graph_Temps_DebitFourni.GenerateGraph();
    }

    public static synchronized int getTemps() {		
        return Simulation.tick + Simulation.timeslot;
    }
    
    public static synchronized void setTick(int tick) {		
        Simulation.tick = tick;
    }
    
    public static synchronized void setTimeslot(int timeslot) {		
        Simulation.timeslot = timeslot;
    }
}