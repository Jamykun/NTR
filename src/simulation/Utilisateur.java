package simulation;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Utilisateur {	
        private int id;
        private List<Paquet> paquetAenvoyer = new ArrayList<>();
        private List<UR> urRecues = new ArrayList<>();
        private Cellule cellule;
        /*private int debitCourrant;
        private int moyenBruit;
        private int moyenPuissance;*/
        private DistancePointAcces distancePointAcces;
        
        public Utilisateur(int id, Cellule cellule) {
            this.id = id;
            this.cellule = cellule;
            this.distancePointAcces = rndDistance();
        }
		
        public Utilisateur(int id, Cellule cellule, DistancePointAcces distancePointAcces) {
            this.id = id;
            this.cellule = cellule;
            this.distancePointAcces = distancePointAcces;
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
		
        public void affecterUR(UR ur) {			
            urRecues.add(ur);
        }
                
        public void clearURrecues() {
            urRecues.clear();
        }
        
        public DistancePointAcces getDistancePointAcces() {
            return this.distancePointAcces;
        }
        
        /*public int getSNR() {
            return moyenBruit / moyenPuissance;
        }*/
		
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
