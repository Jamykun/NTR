package algorithme;

import java.util.List;

import simulation.*;

public class RR implements Algorithme{
	public List<Utilisateur> utilisateurs;

	@Override
	public UR allouerUR(UR u) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Utilisateur> getUtilisateurs() {
		return utilisateurs;
	}
	
	@Override
	public void setUtilisateurs(List<Utilisateur> users) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {		
		return this.getClass().getSimpleName();
	}

	@Override
	public int getMknmoyenProche() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMknmoyenLoin() {
		// TODO Auto-generated method stub
		return 0;
	}
}
