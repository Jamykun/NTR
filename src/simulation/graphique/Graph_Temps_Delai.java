package simulation.graphique;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;
import simulation.DistancePointAcces;
import simulation.Simulation;

public class Graph_Temps_Delai {
    private static HashMap<Integer, ArrayList<Integer>> pointsUltiProche = new HashMap<>();
    private static HashMap<Integer, ArrayList<Integer>> pointsUltiLoin = new HashMap<>();
    private static final String GRAPH_NAME = "Graph_Temps_Delai";
    
    public static void add(int temps, int delaiPaquet, DistancePointAcces dist) {
        if(dist == DistancePointAcces.LOIN) {
            if(!pointsUltiLoin.containsKey(temps)) {
                pointsUltiLoin.put(temps, new ArrayList<>());
            }
            pointsUltiLoin.get(temps).add(delaiPaquet);
        } else {
            if(!pointsUltiProche.containsKey(temps)) {
                pointsUltiProche.put(temps, new ArrayList<>());
            }
            pointsUltiProche.get(temps).add(delaiPaquet);
        }
    }
    
    public static void GenerateGraph() throws IOException {
        // Tri par x
        Comparator<Integer> comparator;
        comparator = (Integer o1, Integer o2) -> o1 - o2;    	
    	SortedSet<Integer> keysLoin = new TreeSet<>(comparator);
    	keysLoin.addAll(pointsUltiLoin.keySet());
   	
    	SortedSet<Integer> keysProche = new TreeSet<>(comparator);
    	keysProche.addAll(pointsUltiProche.keySet());
        
        // Création du fichier et des répertoires
        File f = new File("exports"+ File.separator + GRAPH_NAME + File.separator + GRAPH_NAME +"-"+Simulation.ALGO+"-"+Simulation.NB_UTILISATEURS+"-Loin-"+System.currentTimeMillis()+".csv");
        f.getParentFile().mkdirs(); 
        f.createNewFile();
        FileOutputStream fos = new FileOutputStream(f);
        
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
    	for(Integer key : keysLoin) {
            int sum = 0;
            for(Integer i : pointsUltiLoin.get(key)) {
                sum += i;
            }
            bw.write(key+";"+(sum/pointsUltiLoin.get(key).size()));
            bw.newLine();
    	}

        bw.close();
        
        
        
        
        File f2 = new File("exports"+ File.separator + GRAPH_NAME + File.separator + GRAPH_NAME +"-"+Simulation.ALGO+"-"+Simulation.NB_UTILISATEURS+"-Proche-"+System.currentTimeMillis()+".csv");
        f2.getParentFile().mkdirs(); 
        f2.createNewFile();
        FileOutputStream fos2 = new FileOutputStream(f2);
        
        BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(fos2));
    	for(Integer key : keysProche) {
            int sum = 0;
            for(Integer i : pointsUltiProche.get(key)) {
                sum += i;
            }
            bw2.write(key+";"+(sum/pointsUltiProche.get(key).size()));
            bw2.newLine();
    	}

        bw2.close();
    }	
}
