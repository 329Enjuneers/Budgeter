package receipt_parser;

import java.util.ArrayList;
import java.util.HashMap;

import receipt.PurchasedItem;
import receipt_parser.float_list_factory.Phrase;

public class PurchasedItemFactory {
	
	private static final int MAX_ATTEMPTS = 5;
	
	private HashMap<Integer, Phrase> map;
	private ArrayList<MyFloat> floats;
	
	public PurchasedItemFactory(HashMap<Integer, Phrase> map, ArrayList<MyFloat> floats) {
		this.map = map;
		this.floats = floats;
	}
	
	public ArrayList<PurchasedItem> makePuchasedItems() {
		ArrayList<PurchasedItem> items = new ArrayList<PurchasedItem>();
		for (MyFloat myFloat : floats) {
			int location = getFinalLocation(myFloat);
			if (location >= 0) {
				PurchasedItem p = makeItem(location, myFloat);
				items.add(p);
			}
		}
		return items;
	}
	
	private int getFinalLocation(MyFloat myFloat) {
		for (int i = 0; i < MAX_ATTEMPTS; i++) {
			Location location = new Location(myFloat.location, i);
			int numericLocation = getLocationWithMatchingWords(location, myFloat);
			if (numericLocation >= 0) {
				return numericLocation;
			}
		}
		return -1;
	}
	
	private int getLocationWithMatchingWords(Location location, MyFloat myFloat) {
		for (int i = location.lowerBound(); i <= location.upperBound(); i++) {
			if (map.containsKey(i)) {
				return i;
			}
		}
		return -1;
	}
	
	private PurchasedItem makeItem(int finalLocation, MyFloat myFloat) {
		Phrase p = map.get(finalLocation);
		return new PurchasedItem(p.make(), myFloat.value);
	}
}

class Location {
	private int value;
	private int looseness;
	private static final int LOCATION_THRESHOLD = 10;
	
	public Location(int value, int looseness) {
		this.value = value;
		this.looseness = looseness;
	}
	
	public int upperBound() {
		return value + LOCATION_THRESHOLD + looseness;
	}
	
	public int lowerBound() {
		return value - LOCATION_THRESHOLD - looseness;
	}
}