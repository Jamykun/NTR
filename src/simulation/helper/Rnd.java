package simulation.helper;

import java.util.Random;

public class Rnd {
    public static int rndint(int min, int max) {
        return (int) (min + (Math.random() * (max - min)));
    }
    
    /*public static void main(String[] args) {
    	int[] tab = new int[8];
    	for(int i = 0; i < 7; i++) {
    		tab[i] = 0;
    	}
    	
    	int sum = rndint(1, 8);
    	for(int i = 0; i < 10000000; i++) {
    		int n =rndint(1, 8);
    		tab[n]++;
    		sum += n;
    	}
    	System.out.println((sum/(float)10000000));
    	
    	for(int i = 0; i < 7; i++) {
    		System.out.println(i + " > " + tab[i]);
    	}
    }*/
}
