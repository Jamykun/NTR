package simulation;

public class UR {	
	private Utilisateur utilisateur = null;	
	private Cellule cellule;	
	private int id;
	private int nbBits;
	private boolean finDePasquet = false;
	
	public UR(int id, Cellule cellule) {
		this.cellule = cellule;
		this.id = id;		
	}
	
	// Nb de bits que peut contenir l'UR (Entre [1;8[)
	public void setNbBits(int nbBits) {
		this.nbBits = nbBits;
	}
	
	// l'affictation de UR a un utilisateur donnee.
	public void setAffectation(Utilisateur utilsateur) {
		this.utilisateur = utilsateur;

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
	
	public int getNbBits() {
		return nbBits;
	}
	
	public Utilisateur getUtilisateur(){
		return this.utilisateur;
	}
}
