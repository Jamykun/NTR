package allocation;


import java.util.List;



public interface algorithme {

	public ur allouerUR(ur u);
	
	/*
	 * Initialiser la liste des utilisateurs
	 * */
	
	public void init(List<utilisateur> users);
	
	/*
	 * Get & Set utilisateurs
	 * */
	
	public List<utilisateur> getUtilisateurs();
	
	public void setUsers(List<utilisateur> users);
	
	public int getMknmoyenneProche();

	public int getMknmoyenneLoin();


	/**
	 * Get nom d'algorithme
	 */
	public String getName();
}
