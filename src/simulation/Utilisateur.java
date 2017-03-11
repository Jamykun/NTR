package simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utilisateur {	
        private int id;
        private List<Paquet> paquetAenvoyer = new ArrayList<>();
        private List<UR> urRecues = new ArrayList<>();
        private Cellule cellule;
        private DistancePointAcces distance;
        
        public Utilisateur(int id, Cellule cellule) {
            this.id = id;
            this.cellule = cellule;
            this.distance = rndDistance();
        }
        
        public Utilisateur(int id, Cellule cellule, DistancePointAcces distance) {
            this.id = id;
            this.cellule = cellule;
            this.distance = distance;
        }		

        private DistancePointAcces rndDistance() {
            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(2);
            switch (randomInt) {
                case 0: return DistancePointAcces.PROCHE;
                default: return DistancePointAcces.LOIN;
            }
        }
        
        public int getId() {
            return id;
        }
        
        public DistancePointAcces getDistance() {
            return this.distance;
        }
		
        public void affecterUR(UR ur) {			
            urRecues.add(ur);
        }
                
        public void clearURrecues() {
            urRecues.clear();
        }        
        
        public int getMkn() {
            int res =0;
            if(this.distance == DistancePointAcces.PROCHE)
                    res= (int)( Math.random()*( 5 ) );
            else
                    res= (int)( Math.random()*( 9 ) );

            return res;
        }
		
        /*public UR peekUR() {
                if(urRecues.size() > 0) {
                        UR u = urRecues.get(0);
                        urRecues.remove(0);
                        return u;
                }
                return null;
        }*/
        
        /*public int getDebitCourrant() {
                return debitCourrant;
        }


        public void setDebitCourrant(int debitCourrant) {
                this.debitCourrant = debitCourrant;
        }*/

//		public int getMoyenBruit() {
//			return moyenBruit;
//		}
//
//		public void setMoyenBruit(int moyenBruit) {
//			this.moyenBruit = moyenBruit;
//		}
//
//		public int getMoyenPuissance() {
//			return moyenPuissance;
//		}
//
//		public void setMoyenPuissance(int moyenPuissance) {
//			this.moyenPuissance = moyenPuissance;
//		}
        
//		public void setMoyenProche(int moyenProche) {
//			this.moyenProche = moyenProche;
//		}

}
