package allocation;

public class ur {
	
	private utilisateur utilisateur;
	
	private PointAcces pA;
	
	private int id;
	
	// le constructeur de point d'acces une ur à un id et est ds un et un point d'acces   
	
	public ur(int id, PointAcces pointAcces) {

		this.pA = pointAcces;
		this.id = id;
		
}
	// l'affictation de UR à un utilisateur donnee.
	public void URaffectation( utilisateur x) {
		this.utilisateur=x;

	}
	 // liberation de UR après chaque utilisation 
	public void finUtilisation(){
		this.utilisateur=null;
}

}
