package simulation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import algorithme.RR;
import simulation.*;

public class Simulation {
	public static final int NB_PORTEUSES = 128;
	public static final int SIMULATION_TIMESLOTS = 50;
	public static final int NB_TIMESLOT_TRAITEE = 5;
	private static int tick;
	private static int timeslot;
	private static int nbUrUtilisee = 0;
	
	public static void main(String[] args) throws IOException {
		Cellule cellule0 = new Cellule(0, NB_TIMESLOT_TRAITEE);	
		
		Utilisateur util0 = new Utilisateur(0, cellule0, rndDistance());
		cellule0.setUtilisateur(util0);
		cellule0.addPaquetsFromInternet();
		
		HashMap<Integer, Integer> pointsGraph = new HashMap<>();
		for(tick = 0; tick < SIMULATION_TIMESLOTS; tick += NB_TIMESLOT_TRAITEE) {
			System.out.println("Timeslot " + tick + " à " + (tick+NB_TIMESLOT_TRAITEE));
			
			RR rr = new RR(cellule0);
			rr.setUtilisateur(util0);			
			
			for(timeslot = 0; timeslot < NB_TIMESLOT_TRAITEE; timeslot++) {			
				for(int i = 0; i < NB_PORTEUSES; i++) {
					UR ur = cellule0.getURlibre();	
					ur.setNbBits(rndNbBits());
					if(ur != null) {
						rr.allouerUR(ur);
						nbUrUtilisee++;
					}
				}	
				System.out.println("A: " + timeslot);
			}	
			
					
			System.in.read();
			cellule0.changeTimeslot();
			nbUrUtilisee = 0;	
		}
		System.out.println("Fin de simulation");
	}
	
	public static int getTauxutilisationUR() {
		return nbUrUtilisee / NB_PORTEUSES;
	}
	
	public static int getTemps() {
		System.out.println("B: " + timeslot);
		return tick + timeslot;
	}
	
	public static DistancePointAcces rndDistance() {
		Random randomGenerator = new Random();
	    int randomInt = randomGenerator.nextInt(2);
	    switch (randomInt) {
			case 0: return DistancePointAcces.PROCHE;
			default: return DistancePointAcces.LOIN;
	    }
	}
	
	public static int rndNbBits() {
		Random randomGenerator = new Random();
	    return randomGenerator.nextInt(7) + 1;	  // [1..8] 
	}

}