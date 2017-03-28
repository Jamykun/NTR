package simulation;

import java.io.IOException;

import simulation.graphique.generateur.GraphFusion;
import simulation.graphique.generateur.GraphMoyenneValeursFinales;

public class Simulations {
    private static final int NB_UTILISATEURS_MAX = 18;
    private static final String GRAPH = "Graph_Temps_TauxUR";
    
    public static void main(String[] args) throws IOException  {
        // Lancement des simulations avec les deux algorithmes
        for(int nbUtil = 1; nbUtil <= NB_UTILISATEURS_MAX; nbUtil++) {
            String[] params = { nbUtil+"", "RR" }; 
            Simulation.main(params); 
            
            String[] params2 = { nbUtil+"", "MaxSNR" };
            Simulation.main(params2);  
            System.out.println("> Simulations " + (nbUtil*2) + "/" + (NB_UTILISATEURS_MAX*2) + " effectuées");
        }  
        
        // Génération des graphs synthétisants les résultats
        /*String[] params = { GRAPH, "RR" };
        GraphFusion.main(params);
        String[] params2 = { GRAPH, "MaxSNR" };
        GraphFusion.main(params2);*/
        String[] params = { GRAPH, "RR" };
        GraphMoyenneValeursFinales.main(params);
        String[] params2 = { GRAPH, "MaxSNR" };
        GraphMoyenneValeursFinales.main(params2);
        
        System.out.println("> Toutes les simulations sont terminées");
    }
}
