package simulation.graphique;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

public class GraphChargeDelai {
	private static HashMap<Integer, Integer> points = new HashMap<>();
	private static int nbPaquetTotalEnvoye = 0;
	
	// x, y
	public static void addDelaiActuel(int delai) {
		nbPaquetTotalEnvoye++;
		points.put(nbPaquetTotalEnvoye, delai);
	}
	
	public static void GenerateGraph() throws IOException {
		File fout = new File("graph-ChargeDelai.csv");
		FileOutputStream fos = new FileOutputStream(fout);
	 
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	 
		for (int i = 1; i < points.size(); i++) {
			bw.write(i+";"+points.get(i));
			bw.newLine();
		}
	 
		bw.close();
	}
	
}
