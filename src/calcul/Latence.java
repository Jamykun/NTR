package calcul;

import java.util.List;

import simulation.Cellule;
import simulation.Paquet;
import simulation.UR;
import simulation.Utilisateur;

public class Latence {
	private static int latence;
	
	// Latence pour l'envoie de tous les paquets
	/*public static void CalculLatenceGlobal(Cellule cellule){
		int nbUtilisateurs=0;
		int latenceActuelle =0;
		Utilisateur u= null;
		for (UR ur : cellule.getUr()){
			u = ur.getUtilisateur();
		}
		if (u!=null){
			nbUtilisateurs++;
			List<Paquet> ps = u.getPaquetsAenvoyer();
			
			latenceActuelle =	(int)ps.stream().mapToInt(x->x.getFinEnvoie()-x.getDebutEnvoie()).count();
			
			if(ps.size()>0){
				latenceActuelle = latenceActuelle/ps.size();
			}
		
		}
		if(nbUtilisateurs>0){
			latenceActuelle = latenceActuelle/nbUtilisateurs;
			latence = latence + latenceActuelle;
		}			
	}
	
	public static int CalculLatence(Utilisateur u){
		List<Paquet> ps = u.getPaquetsAenvoyer();		
		int latenceActuelle = (int)ps.stream().mapToInt(x->x.getFinEnvoie()-x.getDebutEnvoie()).count();		
		if(ps.size()>0){
			latenceActuelle = latenceActuelle/ps.size();
		}	
		return latenceActuelle;
	}*/
}
