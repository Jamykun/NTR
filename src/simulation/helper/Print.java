package simulation.helper;

import algorithme.Algorithme;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import simulation.Paquet;
import simulation.Simulation;
import simulation.UR;
import simulation.Utilisateur;

public class Print {
    private static LinkedHashMap<Utilisateur, ArrayList<UR>> affectationUR = new LinkedHashMap<>();

    public static synchronized void affectationUR(Algorithme algo, Utilisateur util, UR ur) {
        if(Simulation.PRINT) {
            if(ur.getId() == Simulation.NB_PORTEUSES - 1) {
                System.out.println("\nAffectations d'UR effectuées par " + algo.getName() + " :");
                
                for(Map.Entry<Utilisateur, ArrayList<UR>> set : affectationUR.entrySet()) {                    
                    System.out.print("Utilisateur " + set.getKey().getId() + " > ");
                    for(UR set_ur : set.getValue()) {
                        System.out.print(set_ur.getId() /*+ "("+set_ur.getNbBits()+")"*/+"\t");                        
                    }
                    System.out.print("\n");
                }
                System.out.print("\n");
            }
            else {
                if(!affectationUR.containsKey(util)) {                    
                    affectationUR.put(util, new ArrayList<>());
                }
                affectationUR.get(util).add(ur);
            }            
        }
    }
    
    public static synchronized void paquetDebutEnvoi(UR ur, Paquet paquetActuel) {
       /* if(Simulation.PRINT) {
            System.out.println("Utilisateur " + ur.getUtilisateur().getId() + " > [ ] Paquet " + paquetActuel.getId() +" : Début d'emission (UR" + ur.getId() + ") - Taille: "+ paquetActuel.getNbBitOrigine()+" bit(s)");
        } */            
    }
    
    public static synchronized void paquetEnvoye(UR ur, Paquet paquetActuel, Algorithme algo) {
        if(Simulation.PRINT) {
            System.out.println("Utilisateur " + ur.getUtilisateur().getId() + " > Paquet " + paquetActuel.getId() +" : Fin d'emission (UR" + paquetActuel.getUrUtilisees() + ") - Délai: " + paquetActuel.getDelai() + " tick(s)");
        }             
    }
     
    public static synchronized void print(String str) {
        if(Simulation.PRINT) {
            System.out.println(str);
        }
    }
    
    public static void changerTimeslot() {
        affectationUR.clear();
    }

    public static void creationUtil(Utilisateur util) {
        if(Simulation.PRINT) {
            System.out.println("Utilisateur " + util.getId() + " > Création de l'utilisateur - Distance: " + util.getDistance());
        }
    }
}
