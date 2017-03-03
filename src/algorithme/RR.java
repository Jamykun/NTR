package algorithme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import simulation.*;

public class RR implements Algorithme{
	private ArrayList<Utilisateur> utilisateurs;
	private Cellule cellule;
	private int i = 0;
	private int nbBitsTransmis = 0;
	private HashMap<Utilisateur, Integer> nbBitsTransmisParUtil;
	private HashMap<Utilisateur, UR> associationsURparUtilisateur;
	private int lastTime = 0;
	
	public RR(Cellule cellule) {
		this.cellule = cellule;
		nbBitsTransmisParUtil = new HashMap<>();
		this.utilisateurs = new ArrayList<>();
	}

	@Override
	public void allouerUR(UR ur, Utilisateur util) {
		associationsURparUtilisateur.put(util, ur);
	}
	
	public void envoyerUR() {
		// Envoyer toutes les UR a la fin des associations
	}
	
	public Utilisateur envoyerUR(UR ur) {
		// TODO Ancien : Récuperer entre 1 et 10 bits du buffer de l'util
				// Créer l'UR à partir des bits récupérer
				// Enlever les bits récup dans le buffer 
				// Envoyer l'UR à l'util	
		
		if(i >= utilisateurs.size()) {
			i = 0;
		}
		
		if(utilisateurs.size() > 0) {
			Utilisateur util = utilisateurs.get(i);
			ur.setDebutEnvoie();
			rndPause();
			utilisateurs.get(i).URrecu(ur);			
			ur.setFinEnvoie();
			
			System.out.println("UR" + ur.getId() + ">Util"+ util.getId() + " - NbBits = " + ur.getNbBits() + " Délai = " + ur.getDelai());
			
			int nbBits = ur.getNbBits();
			if(nbBitsTransmisParUtil.containsKey(util)) {
				nbBits += nbBitsTransmisParUtil.get(util);
			}
			nbBitsTransmisParUtil.put(util, nbBits);
			i++;
			return utilisateurs.get(i-1);
		}
		return null;		
	}
	
	public void rndPause() {
		Random randomGenerator = new Random();
		try {
			Thread.sleep(randomGenerator.nextInt(1500) + 500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Utilisateur> getUtilisateurs() {
		return utilisateurs;
	}
	
	@Override
	public void setUtilisateurs(ArrayList<Utilisateur> users) {
		this.utilisateurs = users;
	}
	
	public void setUtilisateur(Utilisateur user) {
		this.utilisateurs.add(user);
	}

	@Override
	public String getName() {		
		return this.getClass().getSimpleName();
	}

	@Override
	public int getMknmoyen(Utilisateur u) {
		switch (u.getProcheLoin()) {
			case PROCHE : return 6;
			default : return 4;
		}
	}
	public int getDebit(Utilisateur u){
		int time = Simulation.getTemps();
		int debit = nbBitsTransmisParUtil.get(u) / (time - lastTime);
		lastTime = time;
		nbBitsTransmisParUtil.put(u, 0);
		return debit;
	}
}

