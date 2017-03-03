package algorithme;

import java.util.ArrayList;
import java.util.List;

import simulation.*;

public interface Algorithme {
	public Utilisateur allouerUR(UR u);
	
	/*
	 * Initialiser la liste des utilisateurs
	 * */	
	public void setUtilisateurs(ArrayList<Utilisateur> users);
	
	/*
	 * Get & Set utilisateurs
	 */
	public List<Utilisateur> getUtilisateurs();	
	/*
	 * Get nom d'algorithme
	 */
	public String getName();

	int getMknmoyen(Utilisateur u);

	void setUtilisateur(Utilisateur user);

	void allouerUR(UR ur, Utilisateur util);
}
