package simulation.graphique.generateur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import simulation.Simulation;

public class GraphFusion {    
    public static HashMap<Double, Double> getGraphValues(String file) {
        List<String> lines = new ArrayList<>();
        HashMap<Double, Double> out = new HashMap<>();
        
        try {
            Files.lines(Paths.get(file)).forEach(x -> lines.add(x));
            double deuxieme = 0;
            double premier = 0;

            for (int i = 0; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(";");
                if (parts[0].length() > 1) {
                    premier = Integer.parseInt(parts[0]);
                } 
                else {
                    premier = Character.getNumericValue(parts[0].charAt(0));
                }

                if (parts[1].length() > 1) {
                    deuxieme = Integer.parseInt(parts[1]);	
                } 
                else {
                    deuxieme = (Character.getNumericValue(lines.get(i).charAt(2)) + deuxieme);
                }

                out.put(premier, deuxieme);
            }
        } 
        catch (IOException e) {
                System.out.println("Error en lectura del fichero: " + file);
        }
        return out;
    }
    
    private static GraphInfo getGraphInfo(String file) {
        GraphInfo gi = new GraphInfo();
        String[] raw = file.split("-");
        if(raw.length < 3) {
            return null;
        }
        gi.GraphName = raw[0];
        gi.AlgoName = raw[1];
        gi.ChargeValue = raw[2];
        return gi;
    } 
    
    private static ArrayList<String> listFiles(String path, String algo, String charge) {
        File folder = new File(path);
        ArrayList<String> files = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                GraphInfo gi = getGraphInfo(fileEntry.getName());                
                if(!fileEntry.getPath().toLowerCase().contains("merged") && !fileEntry.getPath().toLowerCase().contains("bycharge") && gi.AlgoName.toLowerCase().equals(algo.toLowerCase()) && gi.ChargeValue.toLowerCase().equals(charge.toLowerCase())) {
                    files.add(fileEntry.getPath());
                }
            }
        }
        return files;
    }
    
    

    public static void main(String[] args) throws IOException {
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Nom du graphe à fusionner [GraphCharge_Delai] :");        
        String graphName = buffer.readLine();
        if(graphName.equals("")) {
            graphName = "GraphCharge_Delai";
        }
        System.out.println("Graphique : " + graphName+"\n");
        
        System.out.println("Algorithme du graphe à fusionner ["+Simulation.ALGO+"] :");  
        String algoName = buffer.readLine();
        if(algoName.toLowerCase().equals("maxsnr")) {
            algoName = "MaxSNR";
        } else if(algoName.toLowerCase().equals("rr")) {
            algoName = "RR";
        } else if(algoName.equals("")) {
            algoName = Simulation.ALGO;
        }
        System.out.println("Algo : " + algoName+"\n");
        
        System.out.println("Charge ["+Simulation.CHARGE_MOYENNE+"] :");  
        String charge = buffer.readLine();
        if(charge.equals("")) {
            charge = String.valueOf(Simulation.CHARGE_MOYENNE);
        }
        System.out.println("Charge : " + charge+"\n");
        
        ArrayList<String> files = listFiles("./exports/"+graphName+"/", algoName, charge);
        if(files.isEmpty()) {
            System.out.println("Aucun graphique à traiter");
            System.exit(0);
        }
        
        ArrayList<HashMap<Double, Double>> graphsValues = new ArrayList<>();
        HashMap<Double, ArrayList<Double>> premergedGraph = new HashMap<>();
        for(String file : files) {
            graphsValues.add(getGraphValues(file));
        }
        
        for(HashMap<Double, Double> graphValues : graphsValues) { // Pour chaque graphiques
            for(Double x : graphValues.keySet()) { // Pour chaque clé x
                if(!premergedGraph.keySet().contains(x)) { 
                    premergedGraph.put(x, new ArrayList<>());
                }
                premergedGraph.get(x).add(graphValues.get(x)); // On mémorise les différentes valeurs y données pour x dans les différents graphs
            }            
        }
        
        // On fait la moyenne de tout les y
        HashMap<Double, Double> mergedGraph = new HashMap<>();
        for(Double x : premergedGraph.keySet()) {
            if(premergedGraph.get(x).size() == 1) {                
                mergedGraph.put(x, premergedGraph.get(x).get(0));
            }      
            else { // On fait la moyenne des y
                double sum = 0;
                for(Double y : premergedGraph.get(x)) {
                    sum += y;
                }
                mergedGraph.put(x, (sum / premergedGraph.get(x).size()));
            }
        }
        
        GenerateGraph(mergedGraph, "exports/"+graphName+"/"+graphName+"-"+algoName+"-"+charge+"-merged.csv");

        System.out.println(files.size() + " graphique(s) traité(s)");
    }
    
    private static void GenerateGraph(HashMap<Double, Double> points, String path) throws IOException {
        // Tri par x
        Comparator<Double> comparator;
        comparator = (Double o1, Double o2) -> (int) (o1 - o2);    	
    	SortedSet<Double> keys = new TreeSet<>(comparator);
    	keys.addAll(points.keySet());
        
        // Création du fichier et des répertoires
        File f = new File(path); 
        f.getParentFile().mkdirs(); 
        f.createNewFile();
        FileOutputStream fos = new FileOutputStream(f);
        
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
    	for(Double key : keys) {
            bw.write(key.intValue()+";"+points.get(key).intValue());
            bw.newLine();
    	}

        bw.close();
    }	
}
