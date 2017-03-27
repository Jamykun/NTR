package simulation.graphique;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class MoyenneGraphs {
	 
	protected static String GRAPH_NAME = "GraphDebitsDelai";
	private static HashMap<Integer, Integer> points = new HashMap<>();
	private String nameFichero ;
    public static void add(int x, int y) {
        points.put(x, y);
    }
    public void GenerateGraph() throws IOException {
    	 File f = new File("exports"+ File.separator + GRAPH_NAME + File.separator + GRAPH_NAME +"-"+System.currentTimeMillis()+".csv");
         f.getParentFile().mkdirs(); 
         f.createNewFile();
         FileOutputStream fos = new FileOutputStream(f);
         // TODO
  /*       BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
         Double p = this.lireFichier(this.nameFichero).lastIndexOf(this.lireFichier(this.nameFichero));
         SortedSet<Integer> keys = new TreeSet<Integer>(8);
     	keys.addAll(points.keySet());*/
         
    }
    
    public String getNameFichero(){
    	return this.nameFichero;
    }
	public List<Double> lireFichier(String nameFichero) {
		List<String> aux = new ArrayList<String>();
		List<Double> auxOut = new ArrayList<Double>();
		try {
			// Files.lines(Paths.get(nameFichero)).forEach(System.out::println);
			Files.lines(Paths.get(nameFichero)).forEach(x -> aux.add(x));
			System.out.println("list::" + aux);
			double moyenneDebit = 0;
			double deuxieme = 0;
			int premier = 0;
			//String auxPremier;

			for (int i = 0; i < aux.size(); i++) {
				String[] parts = aux.get(i).split(";");
				if (parts[0].length() > 1) {
					
						premier = Integer.parseInt(parts[0]);
					

				} else {
					premier = Character.getNumericValue(parts[0].charAt(0));
				}
				if (parts[1].length() > 1) {
					
						deuxieme = Integer.parseInt(parts[1]);	

				} else {
				deuxieme = (Character.getNumericValue(aux.get(i).charAt(2)) + deuxieme);
				}
				System.out.println("premier___________________________"
						+ premier);
				System.out.println("deuxi�me______________" + deuxieme);
				if (premier != 0)
					moyenneDebit =  ((deuxieme) / premier);
				auxOut.add(moyenneDebit);
				System.out.println("Moyen " + moyenneDebit);
				aux.get(i).charAt(2);

			}
			System.out.println("Result:>>> " + auxOut);
		} catch (IOException e) {
			System.out.println("Error en lectura del fichero: " + nameFichero);
		}
		return auxOut;
	}

	public static void main(String[] args) {

		//MoyenneGraphs.lireFichier("src/exports/GraphChargeDelai/GraphChargeDelai.csv");
		// String number = "10";
		// int result = Integer.parseInt(number);
		// System.out.println(result);

	}

}
