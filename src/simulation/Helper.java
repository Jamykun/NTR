package simulation;

import java.util.Random;

public class Helper {
	public static int rndint(int min, int max) {
         Random randomGenerator = new Random();
	    return randomGenerator.nextInt(max) + min;	
	}
        
        public static float rndFloat(float min, float max) {
            Random r = new Random();
            return min + r.nextFloat() * (max - min);	
	}
	
	public static synchronized void print(String str) {
		if(Simulation.PRINT) {
            System.out.println(str);
		}
	}
}
