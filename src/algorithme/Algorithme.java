package algorithme;

import java.util.List;

import simulation.*;

public interface Algorithme {
	public UR allouerUR(UR u);
	
	/*
	 * Initialiser la liste des utilisateurs
	 * */	
	public void setUtilisateurs(List<Utilisateur> users);
	
	/*
	 * Get & Set utilisateurs
	 */
	public List<Utilisateur> getUtilisateurs();	
	public int getMknmoyenProche();
	public int getMknmoyenLoin();

	/*
	 * Get nom d'algorithme
	 */
	public String getName();
}
