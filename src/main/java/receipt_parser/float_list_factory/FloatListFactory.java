package receipt_parser.float_list_factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import receipt_parser.MyFloat;
import receipt_parser.Phrase;
import receipt_parser.float_list_factory.float_factory.FloatMap;

public class FloatListFactory {
	
	private HashMap<Integer, Phrase> allPhrasesMap;
	
	public FloatListFactory() {
		allPhrasesMap = new HashMap<Integer, Phrase>();
	}
	
	public void addData(int location, ArrayList<String> words) {
		Phrase phrase = new Phrase(words);
		allPhrasesMap.put(location, phrase);
	}
	
	public ArrayList<MyFloat> getAssociatedFloats() {
		FloatMap floatMap = associateFloats();
		return floatMap.getFloats();
	}
	
	private FloatMap associateFloats() {
		FloatMap floatMap = new FloatMap();
		for (Entry<Integer, Phrase> entry : allPhrasesMap.entrySet()) {
			int location = entry.getKey();
			Phrase phrase = entry.getValue();
			if (phrase.isFloat()) {
				floatMap.add(location, phrase);
			}
		}
		return floatMap;
	}
	
	public HashMap<Integer, Phrase> getLocationToWordMapWithoutFloats() {
		HashMap<Integer, Phrase> phraseMap = new HashMap<Integer, Phrase>();
		for (Entry<Integer, Phrase> entry : allPhrasesMap.entrySet()) {
			int location = entry.getKey();
			Phrase phrase = entry.getValue();
			if (!phrase.isFloat()) {
				phraseMap.put(location, phrase);
			}
		}
		return phraseMap;
	}
}
