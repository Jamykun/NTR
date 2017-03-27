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
import java.util.logging.Level;
import java.util.logging.Logger;
import simulation.Simulation;

public class Graph_BitsUR_Temps {
    private static final String GRAPH_NAME = "Graph_BitsUR_Temps";
    private static HashMap<Integer, Integer> points = new HashMap<>();

    public static void add(int x, int y) {
        points.put(x, y);
    }

    public static void GenerateGraph() {      
        // Tri par x
        Comparator<Integer> comparator;
        comparator = (Integer o1, Integer o2) -> o1 - o2;    	
    	SortedSet<Integer> keys = new TreeSet<>(comparator);
    	keys.addAll(points.keySet());
        
        // Création du fichier et des répertoires
        File f = new File("exports"+ File.separator + GRAPH_NAME + File.separator + GRAPH_NAME +"-"+Simulation.ALGO+"-"+Simulation.NB_UTILISATEURS+"-"+System.currentTimeMillis()+".csv");
        f.getParentFile().mkdirs(); 
        try {
            f.createNewFile();

            FileOutputStream fos = new FileOutputStream(f);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            for(Integer key : keys) {
                bw.write(key+";"+points.get(key));
                bw.newLine();
            }

            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Graph_Temps_TauxUR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }	
}
