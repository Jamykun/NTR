package simulation;
import algorithme.MaxSNR;
import algorithme.RR;
import simulation.graphique.GraphChargeDelai;

import java.io.IOException;

public class Simulation {
	public static final int NB_PORTEUSES = 128;
	public static final int SIMULATION_TIMESLOTS = 70;
	public static final int NB_TIMESLOT_TRAITEE = 5;
    private static final int NB_UTILISATEURS = 500;
    public static final boolean PRINT = false;
	private static int tick;
	private static int timeslot;
	
	public static void main(String[] args) throws IOException {
            Cellule cellule0 = new Cellule(0, NB_TIMESLOT_TRAITEE);	
            RR rr = new RR(cellule0);
            
            int nbUtil = 2;
            /*for(int i = 0; i < NB_UTILISATEURS; i++) {
                Utilisateur util = new Utilisateur(i, cellule0);
                cellule0.setUtilisateur(util);          
                cellule0.addPaquetsFromInternet(util, Helper.rndint(20000, 30000));
                Helper.print(util.toString());
            }*/
            
            //TODO : Date d'arrivée des paquets = debut de la mesure du délai
            // Faire varier le nombre de paquet recu par internet au cours du temps
            // Augmenter au fur et a mesure le nombre d'utilisateur * 2

            for(tick = 0; tick < SIMULATION_TIMESLOTS; tick += NB_TIMESLOT_TRAITEE) {
            	for(int i = 0; i < nbUtil; i++) {
                    Utilisateur util = new Utilisateur(i, cellule0, DistancePointAcces.PROCHE);
                    cellule0.setUtilisateur(util);          
                    cellule0.addPaquetsFromInternet(util, Helper.rndint(20000, 30000));
                    Helper.print(util.toString());
                }
            	nbUtil = nbUtil * 2;
                
                Helper.print("\nTimeslot " + tick + " à " + (tick+NB_TIMESLOT_TRAITEE));

                for(timeslot = 0; timeslot < NB_TIMESLOT_TRAITEE; timeslot++) {		
                    System.out.println("Timeslot " + (tick + timeslot));
                    rr.traiterTimeslot();

                    Helper.print("\nAppuyer sur une touche pour passer au timeslot " + (getTemps() + 1));
                    //System.in.read();
                    rr.changerTimeslot();                           
                    //Helper.print("Latence " + Latence.CalculLatence(u) + " tick");
                }

                cellule0.changeTimeslot();	
            }
            System.out.println("Fin de simulation");
            GraphChargeDelai.GenerateGraph();
	}
	
	public static int getTemps() {		
            return tick + timeslot;
	}
}