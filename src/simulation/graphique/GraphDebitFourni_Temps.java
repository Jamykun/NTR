package simulation.graphique;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

public class GraphDebitFourni_Temps {
    private static final String GRAPH_NAME = "GraphDebitFourni_Temps";
    private static HashMap<Integer, Integer> points = new HashMap<>();

    public static void add(int x, int y) {
        points.put(x, y);
    }

    public static void GenerateGraph() throws IOException {
        File f = new File("exports"+ File.separator + GRAPH_NAME + File.separator + GRAPH_NAME +"-"+System.currentTimeMillis()+".csv");
        f.getParentFile().mkdirs(); 
        f.createNewFile();
        FileOutputStream fos = new FileOutputStream(f);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        
        Comparator<Integer> comparator = new Comparator<Integer>() {
    	  public int compare(Integer o1, Integer o2) {
    		  return o1 - o2;
    	  }
    	};
    	
    	SortedSet<Integer> keys = new TreeSet<Integer>(comparator);
    	keys.addAll(points.keySet());
    	
    	for(Integer key : keys) {
    		bw.write(key+";"+points.get(key));
            bw.newLine();
    	}

 

        bw.close();
    }	
}
