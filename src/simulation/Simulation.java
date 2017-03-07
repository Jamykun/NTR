package simulation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import algorithme.RR;
import calcul.Latence;
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
			Helper.print("Timeslot " + tick + " à " + (tick+NB_TIMESLOT_TRAITEE));
			
			RR rr = new RR(cellule0);
			rr.setUtilisateur(util0);	
			cellule0.addPaquetsFromInternet();
			cellule0.addPaquetsFromInternet();
			
			for(timeslot = 0; timeslot < NB_TIMESLOT_TRAITEE; timeslot++) {		
				Helper.print("Timeslot " + (tick + timeslot));
				int preSub = 0;
				for(int i = 0; i < NB_PORTEUSES; i++) {
					// Si le nombre de bits du paquet est suppérieur au nombre de bits disponible dans les URs
					if ((cellule0.getPacketActuel(util0).getNbBits() - preSub) > 0) {
						UR ur = cellule0.getURlibre();					
						if(ur != null) {	
							ur.setNbBits(Helper.rndint(1, 7));
							rr.allouerUR(ur, util0);
							nbUrUtilisee++;
							preSub += ur.getNbBits();
						}
					}
				}

				rr.envoyerUR();
				//Helper.print("Latence " + Latence.CalculLatence(u) + " tick");
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
}