package simulation;

import java.util.ArrayList;
import java.util.LinkedList;

public class Cellule {
	private ArrayList<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
	private LinkedList<UR> fifoEnvoi = new LinkedList<UR>(); 
	private LinkedList<UR> fifoReception = new LinkedList<UR>();
	private int nbURsimultane;
}
