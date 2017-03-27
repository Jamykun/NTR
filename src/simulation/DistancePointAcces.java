package simulation;

public enum DistancePointAcces {PROCHE, LOIN}

class DistancePA {
	public static DistancePointAcces change(DistancePointAcces dist) {
		switch (dist) {
			case LOIN : return DistancePointAcces.PROCHE;
			default : return DistancePointAcces.LOIN;
		}		
	}
}
