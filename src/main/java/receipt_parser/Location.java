package receipt_parser;

public abstract class Location {
	protected static final int LOCATION_THRESHOLD = 10;
	public int value;
	
	public Location(int value) {
		this.value = value;
	}
	
	public abstract int upperBound();
	public abstract int lowerBound();
}
