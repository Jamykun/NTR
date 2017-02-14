package algorithme;


import java.util.List;

import simulation.*;



public interface Algorithme {

	public UR allouerUR(UR u);
	
	/*
	 * Initialiser la liste des utilisateurs
	 * */
	
	public void init(List<Utilisateur> users);
	
	/*
	 * Get & Set utilisateurs
	 * */
	
	public List<Utilisateur> getUtilisateurs();
	
	public void setUsers(List<Utilisateur> users);
	
	public int getMknmoyenneProche();

	public int getMknmoyenneLoin();


	/**
	 * Get nom d'algorithme
	 */
	public String getName();
}
