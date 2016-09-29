package receipt_parser.float_list_factory.float_factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import exceptions.NotAFloatException;
import receipt_parser.MyFloat;
import receipt_parser.float_list_factory.Phrase;

public class FloatMap {
	private HashMap<Integer, ArrayList<Phrase>> map;


	public FloatMap() {
		map = new HashMap<Integer, ArrayList<Phrase>>();
	}

	public void add(int givenLocation, Phrase phrase) {
		Location location = new Location(givenLocation);
		boolean brokeEarly = false;
		for(int i = location.lowerBound(); i <= location.upperBound(); i++) {
			if (map.containsKey(i)) {
				map.get(i).add(phrase);
				brokeEarly = true;
				break;
			}
		}
		if (!brokeEarly) {
			ArrayList<Phrase> phraseList = new ArrayList<Phrase>();
			phraseList.add(phrase);
			map.put(location.value, phraseList);
		}
	}

	public ArrayList<MyFloat> getFloats() {
		ArrayList<MyFloat> floats = new ArrayList<MyFloat>();
		for(Entry<Integer, ArrayList<Phrase>> entry : map.entrySet()) {
			int location = entry.getKey();
			ArrayList<Phrase> phrases = entry.getValue();
			FloatFactory factory = new FloatFactory(location, phrases);
			try {
				MyFloat f = factory.make();
				floats.add(f);
			} catch (NotAFloatException e) {
			}
		}
		return floats;
	}
}

class Location {
	public int value;

	private static final int LOCATION_THRESHOLD = 10;

	public Location(int location) {
		value = location;
	}

	public int upperBound() {
		return value + LOCATION_THRESHOLD;
	}

	public int lowerBound() {
		return value - LOCATION_THRESHOLD;
	}
}
