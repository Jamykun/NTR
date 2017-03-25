package simulation;
import algorithme.Algorithme;
import algorithme.MaxSNR;
import algorithme.RR;
import simulation.graphique.GraphCharge_Delai;
import simulation.graphique.GraphDebitFourni_Temps;
import simulation.graphique.GraphDebit_NbPaquet;
import simulation.graphique.GraphDebit_NbPaquet_Utilisateur;
import simulation.graphique.Graph_Temps_TauxUR;
import java.io.IOException;
import java.util.ArrayList;
import simulation.helper.Print;
import simulation.helper.Rnd;

public class Simulation {
    public static String ALGO = "MaxSNR";
    public static final boolean PRINT = false;
    
    public static final int NB_PORTEUSES = 128;
    public static final int SIMULATION_TIMESLOTS = 10000;
    public static final int NB_TIMESLOT_TRAITEE = 5;
    public static int NB_UTILISATEURS = 10;
    public static final int DEBIT_GENERE_MOYEN = 40;
    private static int tick;
    private static int timeslot;
    private static ArrayList<GraphDebit_NbPaquet_Utilisateur> graphsDebitUtilisateurs = new ArrayList<>();
    private static Algorithme algo;

    public static void main(String[] args) throws IOException {
        if(args.length == 2) {
            NB_UTILISATEURS = Integer.valueOf(args[0]);
            ALGO = args[1];
        }
        
        Cellule cellule0 = new Cellule(0, NB_TIMESLOT_TRAITEE);	
        if(ALGO.toUpperCase().equals("RR")) {
            algo = new RR(cellule0);
        } else {
            algo = new MaxSNR(cellule0);
        }
        
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
        for(int for_tick = 0; for_tick < SIMULATION_TIMESLOTS; for_tick += NB_TIMESLOT_TRAITEE) {
            Simulation.setTick(for_tick);
            Print.print("\nTimeslot " + Simulation.tick + " à " + (Simulation.tick+NB_TIMESLOT_TRAITEE));                        
            
            for(int for_timeslot = 0; for_timeslot < NB_TIMESLOT_TRAITEE; for_timeslot++) {
                Simulation.setTimeslot(for_timeslot);                
                Print.print("\nTimeslot " + (Simulation.tick + Simulation.timeslot));
                
                intervalMAJdebit--;
                if(intervalMAJdebit == -1) {
                    intervalMAJdebit = Rnd.rndint(4,15);
                    nbBitsInterval = Rnd.rndint(DEBIT_GENERE_MOYEN-(DEBIT_GENERE_MOYEN / 2), DEBIT_GENERE_MOYEN+(DEBIT_GENERE_MOYEN / 2));
                }                 
                int nbBits = Rnd.rndint(nbBitsInterval-(nbBitsInterval / 4), nbBitsInterval+(nbBitsInterval / 4));
                
                // Variation des paquets reçus par les utilisateurs
                for(Utilisateur util : cellule0.getUsers()) {
                    cellule0.addPaquetsFromInternet(util, nbBits);                  
                    
                    Print.print("Utilisateur " + util.getId() + " > Ajout de " + nbBits + " bits dans son buffer. Buffer : " + cellule0.getNbPaquetAEnvoyer(util) + " paquet(s) / Paquet en création : " + cellule0.getNbBitPaquetEnCreation(util) + " bits");
                }                
                
                algo.traiterTimeslot();
                Print.print("Nombre de paquet traité : " + cellule0.getNbTotalPaquetGenere());
                Print.print("Taux utilisation des UR : " + algo.getTauxUtilisationUR() + "%");   
                Print.print("Débit généré : " + cellule0.getDebitGenere());
                
                if(getTemps() % 50 == 0) {
                    GraphDebitFourni_Temps.add(Simulation.getTemps(), cellule0.getDebitFourni());
                }
                Graph_Temps_TauxUR.add(getTemps(), algo.getTauxUtilisationUR());
                GraphDebit_NbPaquet.add(cellule0.getNbTotalPaquetGenere(), (cellule0.getNbTotalURutilisee() == 0) ? 0 : (cellule0.getNbTotalBitsEnvoye() / cellule0.getNbTotalURutilisee()));

                algo.changerTimeslot(); 
                cellule0.changeTimeslot();	
                Print.changerTimeslot();
            }
            Print.print("Debit global : " +  cellule0.getDebitGlobal());;
            //graphsDebitUtilisateurs.get(i).add(cellule0.getNbTotalPaquetGenere(), deb);        
            
            Simulation.setTimeslot(0);            
        }
        System.out.println("\nFin de simulation");
        System.out.println("Nombre de paquet généré : " + cellule0.getNbTotalPaquetGenere() + " paquets / " + cellule0.getNbTotalBitsGenere() + " bits"/* +" => " + ((float)cellule0.getNbTotalBitsGenere()/(float)cellule0.getNbTotalPaquetGenere()) + "%"*/);
        System.out.println("Nombre de paquet restant dans les buffers : " + cellule0.getNbTotalPaquetAEnvoyer());
        
        //Génération des graphiques
        //GraphCharge_Delai.GenerateGraph();
        Graph_Temps_TauxUR.GenerateGraph();
       /* GraphDebit_NbPaquet.GenerateGraph();
        GraphDebitFourni_Temps.GenerateGraph();*/
        /*for(int i = 0; i < cellule0.getUsers().size(); i++) {        	
            graphsDebitUtilisateurs.get(i).GenerateGraph();
        }*/
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