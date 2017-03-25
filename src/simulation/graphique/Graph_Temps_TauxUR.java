package simulation.graphique;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;
import simulation.Simulation;

public class Graph_Temps_TauxUR {
    private static final String GRAPH_NAME = "Graph_Temps_TauxUR";
    private static HashMap<Integer, Double> points = new HashMap<>();

    public static void add(int temps, double utilisationUR) {
        points.put(temps, utilisationUR);
    }

    public static void GenerateGraph() throws IOException {
        Double sum = 0.0;
        for(Double d : points.values()) {
            sum += d;
        }
        int finalResult = (int)((sum / Simulation.SIMULATION_TIMESLOTS) * 100);
        
        
        // Tri par x
        /*Comparator<Integer> comparator;
        comparator = (Integer o1, Integer o2) -> o1 - o2;    	
    	SortedSet<Integer> keys = new TreeSet<>(comparator);
    	keys.addAll(points.keySet());*/
        
        // Création du fichier et des répertoires
        File f = new File("exports"+ File.separator + GRAPH_NAME + File.separator + GRAPH_NAME +"-"+Simulation.ALGO+"-"+Simulation.NB_UTILISATEURS+"-"+System.currentTimeMillis()+".csv");
        f.getParentFile().mkdirs(); 
        f.createNewFile();
        FileOutputStream fos = new FileOutputStream(f);
        
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
    	//for(Integer key : keys) {
            bw.write(Simulation.NB_UTILISATEURS+";"+finalResult);
            bw.newLine();
    	//}

        bw.close();
    }	
    
    /*// Get the final value
			

			// The file writing
			nameFile = world.getNbAccessPoints() + "_cell_" + world.getResAllocAlg().getName() + "_percentage_ur_used_of_cell_number_" + accessPointId + ".csv"; 

			// Care, exceptions can occur here
			try {

				// Get a new pointer on the file
				file = new File(nameFile);

				// Try to create it if it doesn't exist
				file.createNewFile();

				// Write by appending the results
				writer = new BufferedWriter(new FileWriter(nameFile, true));
				writer.write(nbUsers + ";" + finalResult + "\n");

			// All the exception handling
			} catch(IOException e) {
				e.printStackTrace();
			} finally {
				try {
					writer.close();
				} catch(IOException e) {
					e.printStackTrace();
				}
			}

*/
}
