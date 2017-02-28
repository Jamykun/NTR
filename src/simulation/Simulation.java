package simulation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import simulation.*;

public class Simulation {
	public static final int NB_PORTEUSES = 128;
	public static final int SIMULATION_TIMESLOTS = 50;
	public static final int NB_TIMESLOT_TRAITEE = 5;
	private static int tick;
	private static int timeslot;
	
	public static void main(String[] args) throws IOException {
		Cellule cellule0 = new Cellule(0, NB_TIMESLOT_TRAITEE);	
		
		Utilisateur util0 = new Utilisateur(0, cellule0, 4, 1);
		cellule0.setUtilisateur(util0);
		cellule0.addPaquetsFromInternet();
		
		HashMap<Integer, Integer> pointsGraph = new HashMap<>();
		for(tick = 0; tick < SIMULATION_TIMESLOTS; tick += NB_TIMESLOT_TRAITEE) {
			System.out.println("Timeslot " + tick + " à " + (tick+NB_TIMESLOT_TRAITEE));
			
			//util0.creerPaquet();
			
			for(timeslot = 0; timeslot < NB_TIMESLOT_TRAITEE; timeslot++) {			
				for(int i = 0; i < NB_PORTEUSES; i++) {
					UR ur = cellule0.getURlibre();
					if(ur != null) {
						cellule0.sendUR(util0, ur);
					}
				}
			}
			
			System.in.read();
			cellule0.changeTimeslot();
		}
		System.out.println("Fin de simulation");
	}
	
	public static int getTemps() {
		return tick + timeslot;
	}

}