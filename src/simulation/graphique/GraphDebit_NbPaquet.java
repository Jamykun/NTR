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

public class GraphDebit_NbPaquet {
    private static final String GRAPH_NAME = "GraphDebit_NbPaquet";
    private static HashMap<Integer, Integer> points = new HashMap<>();

    public static void add(int nbPaquet, int debit) {
        points.put(nbPaquet, debit);
    }

    public static void GenerateGraph() throws IOException {
        // Tri par x
        Comparator<Integer> comparator;
        comparator = (Integer o1, Integer o2) -> o1 - o2;    	
    	SortedSet<Integer> keys = new TreeSet<>(comparator);
    	keys.addAll(points.keySet());
        
        // Création du fichier et des répertoires
        File f = new File("exports"+ File.separator + GRAPH_NAME + File.separator + GRAPH_NAME +"-"+Simulation.ALGO+"-"+Simulation.CHARGE_MOYENNE+"-"+System.currentTimeMillis()+".csv");
        f.getParentFile().mkdirs(); 
        f.createNewFile();
        FileOutputStream fos = new FileOutputStream(f);
        
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
    	for(Integer key : keys) {
            bw.write(key+";"+points.get(key));
            bw.newLine();
    	}

        bw.close();
    }	
}
