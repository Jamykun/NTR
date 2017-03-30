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

public class GraphMoyenneValeursFinales {    
    public static double getGraphMeanLastValues(String file, int nbLastValue) {
        double out = 0;
        List<String> lines = new ArrayList<>();
        
        try {
            Files.lines(Paths.get(file)).forEach(x -> lines.add(x));
            
            //Moyenne des dernieres valeur du fichier
            double sum = 0;
            int i = lines.size() - nbLastValue;
            if(i < 0) {
                i = 0;
            }
            int nbElem = lines.size() - i;
            for (i = i; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(";");
                sum += Double.parseDouble(parts[1]);	
            }
            out = (sum / (double)nbElem);
        } 
        catch (IOException e) {
                System.err.println("Erreur " + file + " : " + e.getMessage());
        }
        return out;
    }
    
    private static GraphInfo getGraphInfo(String file) {
        GraphInfo gi = new GraphInfo();
        String[] raw = file.split("-");
        gi.Graph = raw[0];
        gi.Algo = raw[1];
        gi.Charge = raw[2];
        if(raw.length == 5) {
            gi.Distance = raw[3].toLowerCase();
        }
        return gi;
    } 
    
    private static ArrayList<String> listFiles(String path) {
        File folder = new File(path);
        ArrayList<String> files = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                GraphInfo gi = getGraphInfo(fileEntry.getName());       
                files.add(fileEntry.getPath());
            }
        }
        return files;
    }    

    public static void main(String[] args) throws IOException {
        String graphName = "";
        int nbLastValue;
        
        if(args.length == 2) {
            graphName = args[0];
            nbLastValue = Integer.parseInt(args[1]);
        }
        else {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
            while(graphName.equals("")) {
                System.out.println("Nom du graphe à fusionner :");        
                graphName = buffer.readLine();
            }

            System.out.println("Nombre de derniers resultats à fusionner :");  
            nbLastValue = Integer.parseInt(buffer.readLine());            
        }
        if(args.length != 2) { // Si on n'est pas en mode automatique
            System.out.println("Graphique : " + graphName);
            System.out.println("Nombre de derniers resultats à fusionner : " + nbLastValue);
        }
        
        ArrayList<String> files = listFiles("./exports/"+graphName+"/");
        if(files.isEmpty()) {
            System.out.println("Aucun graphique à traiter");
            System.exit(0);
        }
        
        ArrayList<HashMap<Double, Double>> graphsValues = new ArrayList<>();
        HashMap<Integer, Double> mergedGraph_RR = new HashMap<>();
        HashMap<Integer, Double> mergedGraph_MaxSNR = new HashMap<>();
        HashMap<Integer, Double> mergedGraph_RR_Loin = new HashMap<>();
        HashMap<Integer, Double> mergedGraph_MaxSNR_Loin = new HashMap<>();
        
        for(String file : files) {
            GraphInfo gi = getGraphInfo(file);
            Double mean = getGraphMeanLastValues(file, nbLastValue);
            
            if(gi.Algo.toLowerCase().equals("rr")) {
                if(gi.Distance == null || gi.Distance.equals("proche")) {
                    mergedGraph_RR.put(Integer.valueOf(gi.Charge), mean);
                } else {
                    mergedGraph_RR_Loin.put(Integer.valueOf(gi.Charge), mean);
                }
            } else if(gi.Algo.toLowerCase().equals("maxsnr")) {
                if(gi.Distance == null || gi.Distance.equals("proche")) {
                    mergedGraph_MaxSNR.put(Integer.valueOf(gi.Charge), mean);
                } else {
                    mergedGraph_MaxSNR_Loin.put(Integer.valueOf(gi.Charge), mean);
                }
            } else {
                System.err.println("L'algorithme " + gi.Algo +" n'est pas supporté");
                System.exit(-1);
            }
        }
        
        String[] newFilename = graphName.split("_");
        GenerateGraph(mergedGraph_RR, mergedGraph_MaxSNR, mergedGraph_RR_Loin, mergedGraph_MaxSNR_Loin, "exports/Graph_Charge_"+newFilename[2]+".csv");

        System.out.println(files.size() + " graphique(s) traité(s) pour exports/Graph_Charge_"+newFilename[2]+".csv");
    }
    
    private static void GenerateGraph(HashMap<Integer, Double> points_RR, HashMap<Integer, Double> points_MaxSNR, HashMap<Integer, Double> points_RR_Loin, HashMap<Integer, Double> points_MaxSNR_Loin, String path) throws IOException {
        // Tri par x
        Comparator<Integer> comparator;
        comparator = (Integer o1, Integer o2) -> (int) (o1 - o2);  
        
    	SortedSet<Integer> keys_RR = new TreeSet<>(comparator);
    	keys_RR.addAll(points_RR.keySet());
        
        /*SortedSet<Integer> keys_MaxSNR = new TreeSet<>(comparator);
    	keys_MaxSNR.addAll(points_MaxSNR.keySet());*/
        
        // Création du fichier et des répertoires
        File f = new File(path); 
        f.getParentFile().mkdirs(); 
        f.createNewFile();
        FileOutputStream fos = new FileOutputStream(f);
        
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        if(points_MaxSNR_Loin.size() != 0 && points_RR_Loin.size() != 0) {
            bw.write(";RR Proche;RR Loin;MaxSNR Proche;MaxSNR Loin");
        } else {
            bw.write(";RR;MaxSNR");
        }
        bw.newLine(); 

    	for(Integer key : keys_RR) {   
            if(points_MaxSNR_Loin.size() != 0 && points_RR_Loin.size() != 0) {
                bw.write("\""+key+"\";\""+points_RR.get(key).toString().replace(".", ",")+"\";\""+points_RR_Loin.get(key).toString().replace(".", ",").replace("NaN", "0,0")+"\";\""+points_MaxSNR.get(key).toString().replace(".", ",")+"\";\""+points_MaxSNR_Loin.get(key).toString().replace(".", ",").replace("NaN", "0,0")+"\"");
            } else {
                bw.write("\""+key+"\";\""+points_RR.get(key).toString().replace(".", ",")+"\";\""+points_MaxSNR.get(key).toString().replace(".", ",")+"\"");
            }            
            bw.newLine();
    	}

        bw.close();
    }	
}
