package simulation.helper;

public class Rnd {
    public static int rndint(int min, int max) {	
        return (int) (min + (Math.random() * (max - min)));
    }

    /*public static float rndFloat(float min, float max) {
        Random r = new Random();
        return min + r.nextFloat() * (max - min);	
    }*/
}
