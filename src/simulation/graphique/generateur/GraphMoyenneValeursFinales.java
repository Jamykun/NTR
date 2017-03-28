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
import static simulation.Simulation.ALGO;
import static simulation.Simulation.NB_UTILISATEURS;

public class GraphMoyenneValeursFinales {    
    public static double getGraphMeanLastValues(String file) {
        double out = 0;
        List<String> lines = new ArrayList<>();
        
        try {
            Files.lines(Paths.get(file)).forEach(x -> lines.add(x));
            
            //Moyenne des derniers débit du fichier
            int sum = 0;
            int i = lines.size() - 50;
            if(i < 0) {
                i = 0;
            }
            int nbElem = lines.size() - i;
            for (i = i; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(";");
                sum += Double.parseDouble(parts[1]);	
            }
            out = (sum / nbElem);
            
            // Valeur finale
            /*String[] parts = lines.get(lines.size()-1).split(";");
            out = Integer.parseInt(parts[1]);*/
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
    
    private static ArrayList<String> listFiles(String path, String algo) {
        File folder = new File(path);
        ArrayList<String> files = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                GraphInfo gi = getGraphInfo(fileEntry.getName());         
                
                if(!fileEntry.getPath().toLowerCase().contains("merged") && !fileEntry.getPath().toLowerCase().contains("moyennevaleursfinales") && gi.AlgoName.toLowerCase().equals(algo.toLowerCase())) {
                    files.add(fileEntry.getPath());
                }
            }
        }
        return files;
    }    

    public static void main(String[] args) throws IOException {
        String graphName;
        String algoName;
        
        if(args.length == 2) {
            graphName = args[0];
            algoName = args[1];
        }
        else {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Nom du graphe à fusionner [GraphDebitFourni_Temps] :");        
            graphName = buffer.readLine();
            if(graphName.equals("")) {
                graphName = "GraphDebitFourni_Temps";
            }

            System.out.println("Algorithme du graphe à fusionner ["+Simulation.ALGO+"] :");  
            algoName = buffer.readLine();
            if(algoName.toLowerCase().equals("maxsnr")) {
                algoName = "MaxSNR";
            } else if(algoName.toLowerCase().equals("rr")) {
                algoName = "RR";
            } else if(algoName.equals("")) {
                algoName = Simulation.ALGO;
            }
            
        }
        System.out.println("Graphique : " + graphName);
        System.out.println("Algo : " + algoName);
        
        ArrayList<String> files = listFiles("./exports/"+graphName+"/", algoName);
        if(files.isEmpty()) {
            System.out.println("Aucun graphique à traiter");
            System.exit(0);
        }
        
        ArrayList<HashMap<Double, Double>> graphsValues = new ArrayList<>();
        HashMap<Integer, Double> mergedGraph = new HashMap<>();
        for(String file : files) {
            String charge = getGraphInfo(file).ChargeValue;
            Double mean = getGraphMeanLastValues(file);
            mergedGraph.put(Integer.valueOf(charge), mean);
        }
        
        GenerateGraph(mergedGraph, "exports/"+graphName+"/"+graphName+"-"+algoName+"-MoyenneValeursFinales.csv");

        System.out.println(files.size() + " graphique(s) traité(s)");
    }
    
    private static void GenerateGraph(HashMap<Integer, Double> points, String path) throws IOException {
        // Tri par x
        Comparator<Integer> comparator;
        comparator = (Integer o1, Integer o2) -> (int) (o1 - o2);    	
    	SortedSet<Integer> keys = new TreeSet<>(comparator);
    	keys.addAll(points.keySet());
        
        // Création du fichier et des répertoires
        File f = new File(path); 
        f.getParentFile().mkdirs(); 
        f.createNewFile();
        FileOutputStream fos = new FileOutputStream(f);
        
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
    	for(Integer key : keys) {
            bw.write(key+";"+points.get(key).intValue());
            bw.newLine();
    	}

        bw.close();
    }	
}
