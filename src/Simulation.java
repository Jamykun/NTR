import simulation.*;

public class Simulation {
	private static int SIMULATION_TIME = 2000;

	public static void main(String[] args) {
		Cellule cellule0 = new Cellule(0);
		PointAcces pointAcces = new PointAcces(cellule0);
		cellule0.setAccessPoint(pointAcces);
		
		Utilisateur util0 = new Utilisateur();
		pointAcces.setUtilisateur(util0);

		for(int time = 0; time < SIMULATION_TIME; time ++) {
			// TODO
		}
	}

}
