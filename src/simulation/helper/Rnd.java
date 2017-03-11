package simulation.helper;

import java.util.Random;

public class Rnd {
    public static int rndint(int min, int max) {
        Random randomGenerator = new Random();
        return randomGenerator.nextInt(max) + min;	
    }

    public static float rndFloat(float min, float max) {
        Random r = new Random();
        return min + r.nextFloat() * (max - min);	
    }
}
