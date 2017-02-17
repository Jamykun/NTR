import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import simulation.*;

public class Simulation {
	public static final int NB_PORTEUSES = 128;
	public static final int SIMULATION_TIMESLOTS = 50;
	public static final int NB_TIMESLOT_TRAITEE = 5;

	public static void main(String[] args) throws IOException {
		Cellule cellule0 = new Cellule(0, NB_TIMESLOT_TRAITEE);	
		
		Utilisateur util0 = new Utilisateur(0, cellule0, 4, 1);
		cellule0.setUtilisateur(util0);

		HashMap<Integer, Integer> pointsGraph = new HashMap<>();
		for(int tick = 0; tick < SIMULATION_TIMESLOTS; tick += NB_TIMESLOT_TRAITEE) {
			System.out.println("Timeslot " + tick + " à " + (tick+NB_TIMESLOT_TRAITEE));
			
			for(int timeslot = 0; timeslot < NB_TIMESLOT_TRAITEE; timeslot++) {
				Paquet paquet1 = new Paquet(util0, tick+timeslot);				
				ArrayList<UR> paquet1urs = paquet1.toUR(cellule0);
				
				for(int i = 0; i < NB_PORTEUSES; i++) {
					if(paquet1urs.size() > 0) {
						util0.envoiUR(paquet1urs.remove(0)); 
					}
				}
			}
			
			System.in.read();
			cellule0.changeTimeslot();
		}
		System.out.println("Fin de simulation");
	}

}