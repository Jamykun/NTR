package simulation;

import java.util.Random;

public class Helper {
	public static int rndint(int a, int b) {
		Random randomGenerator = new Random();
	    return randomGenerator.nextInt(b) + a;	
	}
	
	public static synchronized void print(String str) {
		System.out.println(str);
	}
}
