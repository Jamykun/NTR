package simulation;
import algorithme.RR;
import java.io.IOException;

public class Simulation {
	public static final int NB_PORTEUSES = 128;
	public static final int SIMULATION_TIMESLOTS = 50;
	public static final int NB_TIMESLOT_TRAITEE = 5;
        private static final int NB_UTILISATEURS = 5;
	private static int tick;
	private static int timeslot;
	
	public static void main(String[] args) throws IOException {
            Cellule cellule0 = new Cellule(0, NB_TIMESLOT_TRAITEE);	
            RR rr = new RR(cellule0);

            for(int i = 0; i < NB_UTILISATEURS; i++) {
                Utilisateur util = new Utilisateur(i, cellule0);
                cellule0.setUtilisateur(util);          
                cellule0.addPaquetsFromInternet(util, Helper.rndint(500, 3000));
            }

            for(tick = 0; tick < SIMULATION_TIMESLOTS; tick += NB_TIMESLOT_TRAITEE) {
                Helper.print("Timeslot " + tick + " à " + (tick+NB_TIMESLOT_TRAITEE));

                for(timeslot = 0; timeslot < NB_TIMESLOT_TRAITEE; timeslot++) {		
                    Helper.print("Timeslot " + (tick + timeslot));
                    rr.traiterTimeslot();

                    System.out.println("\nAppuyer sur une touche pour passer au timeslot " + (getTemps() + 1));
                    System.in.read();
                    rr.changerTimeslot();                           
                    //Helper.print("Latence " + Latence.CalculLatence(u) + " tick");
                }

                cellule0.changeTimeslot();	
            }
            System.out.println("Fin de simulation");
	}
	
	public static int getTemps() {		
            return tick + timeslot;
	}
}