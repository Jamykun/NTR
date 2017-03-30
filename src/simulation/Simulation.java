package simulation;
import algorithme.Algorithme;
import algorithme.MaxSNR;
import algorithme.RR;
import simulation.graphique.Graph_Temps_Delai;
import simulation.graphique.Graph_Temps_DebitFourni;
import simulation.graphique.Graph_Temps_BitsUR;
import simulation.graphique.Graph_Temps_TauxUR;
import java.io.IOException;
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
        }

        for(int for_tick = 0; for_tick < SIMULATION_TIMESLOTS; for_tick += NB_TIMESLOT_TRAITEE) {
            Simulation.setTick(for_tick);
            Print.print("\nTimeslot " + Simulation.tick + " à " + (Simulation.tick+NB_TIMESLOT_TRAITEE));                        
            
            for(int for_timeslot = 0; for_timeslot < NB_TIMESLOT_TRAITEE; for_timeslot++) {
                Simulation.setTimeslot(for_timeslot);                
                Print.print("\nTimeslot " + (Simulation.tick + Simulation.timeslot));
                               
                for(Utilisateur util : cellule0.getUsers()) {
                    int nbBits = Rnd.rndint(DEBIT_GENERE_MOYEN-(DEBIT_GENERE_MOYEN / 8), DEBIT_GENERE_MOYEN+(DEBIT_GENERE_MOYEN / 8));
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
               
                Print.print("Timeslot :");  
                Print.print("Nombre de bits généré : " + cellule0.getNbBitsGenereTimeslot());
                Print.print("Nombre de bits fourni : " + cellule0.getNbBitsFourniTimeslot());                
                Print.print("Taux utilisation des UR : " + algo.getTauxUtilisationUR() + "% ("+cellule0.getNbURutiliseeTimeslot()+"/"+NB_PORTEUSES+")");  
                Print.print("\nTotal :");
                Print.print("Nombre de paquet généré : " + cellule0.getNbTotalPaquetGenere());                
                
                algo.changerTimeslot(); 
                cellule0.changeTimeslot();	
                Print.changerTimeslot();
            }
            Print.print("Debit global : " +  cellule0.getDebitGlobal());      
            
            Simulation.setTimeslot(0);            
        }
        if(args.length != 2) {
            System.out.println("\nFin de simulation");
        }  
        Print.print("Nombre de paquet généré : " + cellule0.getNbTotalPaquetGenere() + " paquets / " + cellule0.getNbTotalBitsGenere() + " bits");
        Print.print("Nombre de paquet restant dans les buffers : " + cellule0.getNbTotalPaquetAEnvoyer());      

        //Génération des graphiques
        Graph_Temps_BitsUR.add(Simulation.getTemps(), (cellule0.getNbTotalBitsEnvoye() / (double)cellule0.getNbTotalURenvoye()));
        Graph_Temps_BitsUR.GenerateGraph();
        Graph_Temps_TauxUR.GenerateGraph();
        Graph_Temps_Delai.GenerateGraph();
        Graph_Temps_DebitFourni.GenerateGraph();
    }

    public static int getTemps() {		
        return Simulation.tick + Simulation.timeslot;
    }
    
    public static void setTick(int tick) {		
        Simulation.tick = tick;
    }
    
    public static void setTimeslot(int timeslot) {		
        Simulation.timeslot = timeslot;
    }
}