package simulation;

import java.io.IOException;
import simulation.graphique.generateur.GraphDebitCharge;

public class Simulations {
    private static final int NB_UTILISATEURS_MAX = 15;
    private static final String GRAPH = "Graph_Temps_TauxUR";
    
    public static void main(String[] args) throws IOException  {        
        for(int nbUtil = 1; nbUtil <= NB_UTILISATEURS_MAX; nbUtil++) {
            String[] params = { nbUtil+"", "RR" };
            Simulation.main(params);  
        }  
        
        for(int nbUtil = 1; nbUtil <= NB_UTILISATEURS_MAX; nbUtil++) {
            String[] params = { nbUtil+"", "MaxSNR" };
            Simulation.main(params);  
        }  
        
        String[] params = { GRAPH, "RR" };
        GraphDebitCharge.main(params);
        String[] params2 = { GRAPH, "MaxSNR" };
        GraphDebitCharge.main(params2);
    }
}
