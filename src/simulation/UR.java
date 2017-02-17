package simulation;

public class UR {	
	private Utilisateur utilisateur;	
	private Cellule cellule;	
	private int id;
	
	// le constructeur de point d'acces une ur a un id et est ds un et un point d'acces   
	
	public UR(int id, Cellule cellule) {
		this.cellule = cellule;
		this.id = id;		
	}
	
	// l'affictation de UR a un utilisateur donnee.
	public void setAffectation(Utilisateur utilsateur) {
		this.utilisateur = utilsateur;

	}
	 // liberation de UR apres chaque utilisation 
	public void finUtilisation(){
		this.utilisateur = null;
	}
	
	public boolean estLibre(){
		return (this.utilisateur == null);
	}
	
	public String toString() {
		return "UR" + id + " " + ((estLibre() ? "libre" : "non libre : ") + (!estLibre() ? "utilisateur " + this.utilisateur.getId() : ""));
	}
	
	public int getId() {
		return id;
	}
}
