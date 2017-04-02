package simulation;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import simulation.graphique.generateur.GraphMoyenneValeursFinales;

public class Simulations {
    private static final int NB_UTILISATEURS_MAX = 20;
    
    public static void main(String[] args) throws IOException  {
        if(previousResults(new File("exports/"))) {
            System.out.println("Les précédents résultats exportés doivent être supprimés. Les supprimer ? [o/n]");
            BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));     
            String stdin = buffer.readLine();
            if(stdin.toLowerCase().equals("o")) {
                deletePreviousResults(new File("exports/"));
            }
            else {
                System.exit(0);
            }
        }
        
        // Lancement des simulations avec les deux algorithmes
        System.out.println("Lancement de " + (NB_UTILISATEURS_MAX*2) +" simulations de 2 à " + NB_UTILISATEURS_MAX + " utilisateurs avec RR et MaxSNR");
        for(int nbUtil = 2; nbUtil <= NB_UTILISATEURS_MAX; nbUtil++) {
            String[] params = { nbUtil+"", "RR" }; 
            Simulation.main(params); 
            System.out.println(((nbUtil*2)-1) + "/" + (NB_UTILISATEURS_MAX*2) + " simulations effectuées");
            
            String[] params2 = { nbUtil+"", "MaxSNR" };
            Simulation.main(params2);  
            System.out.println((nbUtil*2) + "/" + (NB_UTILISATEURS_MAX*2) + " simulations effectuées");
        }  
        
        // Génération des graphs synthétisants les résultats
        String[] params = { "Graph_Temps_TauxUR", (Simulation.SIMULATION_TIMESLOTS / 10)+"" };
        GraphMoyenneValeursFinales.main(params);
        String[] params2 = { "Graph_Temps_BitsUR", "1" };
        GraphMoyenneValeursFinales.main(params2);
        String[] params3 = { "Graph_Temps_DebitFourni", (Simulation.SIMULATION_TIMESLOTS / 400)+"" };
        GraphMoyenneValeursFinales.main(params3);
        String[] params4 = { "Graph_Temps_Delai", (Simulation.SIMULATION_TIMESLOTS / 30)+"" };
        GraphMoyenneValeursFinales.main(params4);  
        String[] params5 = { "Graph_Temps_MknMoyen", Simulation.SIMULATION_TIMESLOTS+"" };
        GraphMoyenneValeursFinales.main(params5); 
        
        System.out.println("Toutes les simulations sont terminées");
    }
    
     private static boolean previousResults(File folder) {
        File[] files = folder.listFiles();
        if(folder.listFiles() == null) {
            return false;
        }
        for (final File fileEntry : folder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                return true;
            } else {
                if(previousResults(fileEntry)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private static void deletePreviousResults(File folder) {
        File[] files = folder.listFiles();
        if(files!=null) {
            for(File f: files) {
                if(f.isDirectory()) {
                    deletePreviousResults(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }
}
