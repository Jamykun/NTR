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
        private int distancePointAcces;
        
        public Utilisateur(int id, Cellule cellule) {
            this.id = id;
            this.cellule = cellule;
            this.distancePointAcces = Helper.rndint(1, 15);
        }
		
        public Utilisateur(int id, Cellule cellule, int distancePointAcces) {
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
        
        public int getDistancePointAcces() {
            return this.distancePointAcces;
        }
        
        /**
         * Rapport signal / bruit
         * @return 
         */
        public float getSNR() {
            return this.distancePointAcces / Helper.rndFloat((float)0.1, 1);
        }
        
        @Override
        public String toString() {
            return "Util" + id + " Cel" + cellule.getNumero() + " Distance : " + distancePointAcces;
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
