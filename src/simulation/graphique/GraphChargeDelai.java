package simulation.graphique;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

public class GraphChargeDelai {
    private static final String GRAPH_NAME = "GraphChargeDelai";
    private static HashMap<Integer, Integer> points = new HashMap<>();

    public static void addDelaiActuel(int delai) {
        points.put(points.size() + 1, delai);
    }

    public static void GenerateGraph() throws IOException {
        File f = new File("exports"+ File.separator + GRAPH_NAME + File.separator + GRAPH_NAME +"-"+System.currentTimeMillis()+".csv");
        f.getParentFile().mkdirs(); 
        f.createNewFile();
        FileOutputStream fos = new FileOutputStream(f);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        for (int i = 1; i < points.size(); i++) {
                bw.write(i+";"+points.get(i));
                bw.newLine();
        }

        bw.close();
    }	
}
