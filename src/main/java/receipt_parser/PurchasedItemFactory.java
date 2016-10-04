package receipt_parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import budgeter.PurchasedItem;

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
		items.addAll(makeItemsWithCosts());
		items.addAll(makeItemsWithoutCosts());
		return items;
	}
	
	private ArrayList<PurchasedItem> makeItemsWithCosts() {
		ArrayList<PurchasedItem> items = new ArrayList<PurchasedItem>();
		for (MyFloat myFloat : floats) {
			int location = getFinalLocation(myFloat);
			if (location >= 0) {
				PurchasedItem p = makeItem(location, myFloat);
				p.autoCorrect();
				items.add(p);
				map.remove(location);
			}
		}
		return items;
	}
	
	private int getFinalLocation(MyFloat myFloat) {
		for (int i = 0; i < MAX_ATTEMPTS; i++) {
			MyLocation location = new MyLocation(myFloat.location, i);
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
	
	private ArrayList<PurchasedItem> makeItemsWithoutCosts() {
		ArrayList<PurchasedItem> items = new ArrayList<PurchasedItem>();
		for (Entry<Integer, Phrase> entry : map.entrySet()) {
			String text = entry.getValue().make();
			PurchasedItem p = new PurchasedItem(text, (float) 0.0);
			p.autoCorrect();
			items.add(p);
		}
		return items;
	}
}

class MyLocation extends Location {
	private int looseness;
	
	public MyLocation(int value, int looseness) {
		super(value);
		this.looseness = looseness;
	}
	
	public int upperBound() {
		return value + LOCATION_THRESHOLD + looseness;
	}
	
	public int lowerBound() {
		return value - LOCATION_THRESHOLD - looseness;
	}
}