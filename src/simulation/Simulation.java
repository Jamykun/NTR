package simulation;
import simulation.helper.Rnd;
import algorithme.MaxSNR;
import algorithme.RR;
import simulation.graphique.GraphChargeDelai;

import java.io.IOException;
import static java.lang.Math.ceil;
import simulation.helper.Print;

public class Simulation {
    public static final int NB_PORTEUSES = 128;
    public static final int SIMULATION_TIMESLOTS = 100;
    public static final int NB_TIMESLOT_TRAITEE = 5;
    private static final int NB_UTILISATEURS = 5;
    public static final boolean PRINT = true;
    private static int tick;
    private static int timeslot;

    public static void main(String[] args) throws IOException {
        Cellule cellule0 = new Cellule(0, NB_TIMESLOT_TRAITEE);	
        RR rr = new RR(cellule0);
        
        int nbUtil = 1;
        
        for(int i = nbUtil; i < NB_UTILISATEURS; i++) {
            Utilisateur util = new Utilisateur(i, cellule0);
            cellule0.addUtilisateur(util);         
            Print.creationUtil(util);
        }

        for(int for_tick = 0; for_tick < SIMULATION_TIMESLOTS; for_tick += NB_TIMESLOT_TRAITEE) {
            Simulation.setTick(for_tick);
            Print.print("\nTimeslot " + Simulation.tick + " à " + (Simulation.tick+NB_TIMESLOT_TRAITEE));
                        
            // Pour faire augmenter le nombre d'utilisateur au fur et à mesure
            /*for(int i = nbUtil; i < nbUtil * 2; i++) {
                Utilisateur util = new Utilisateur(i, cellule0);
                cellule0.addUtilisateur(util);         
                Helper.print(util.toString());
            }
            nbUtil = nbUtil * 2;*/
            
            // Variation des paquets reçus par les utilisateurs
            for(Utilisateur util : cellule0.getUsers()) {
                int nbBits = Rnd.rndint(0, 1800);
                cellule0.addPaquetsFromInternet(util, nbBits);
                Print.print("Utilisateur " + util.getId() + " > Ajout de " + nbBits + " bits dans son buffer. Buffer : " + cellule0.getNbPaquetAEnvoyer(util) + " paquet(s) / " + cellule0.getNbBitAEnvoyer(util) + " bits");
            }

            for(int for_timeslot = 0; for_timeslot < NB_TIMESLOT_TRAITEE; for_timeslot++) {
                Simulation.setTimeslot(for_timeslot);
                
                Print.print("\nTimeslot " + (Simulation.tick + Simulation.timeslot));
                rr.traiterTimeslot();

                Print.print("\nAppuyer sur une touche pour passer au timeslot " + (getTemps() + 1));
                //System.in.read();
                rr.changerTimeslot(); 
                Print.changerTimeslot();
                //Helper.print("Latence " + Latence.CalculLatence(u) + " tick");
            }       
            Simulation.setTimeslot(0);

            cellule0.changeTimeslot();	
        }
        System.out.println("\nFin de simulation");
        System.out.println("Nombre de paquet généré : " + cellule0.getNbTotalPaquetGenere() + " / " + cellule0.getNbTotalBitsGenere() + " bits"/* +" => " + ((float)cellule0.getNbTotalBitsGenere()/(float)cellule0.getNbTotalPaquetGenere()) + "%"*/);
        System.out.println("Nombre de paquet restant dans les buffers : " + cellule0.getNbTotalPaquetAEnvoyer());
        GraphChargeDelai.GenerateGraph();
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