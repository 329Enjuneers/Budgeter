package receipt_parser.float_list_factory.float_factory;

import java.util.ArrayList;

import receipt_parser.float_list_factory.Phrase;

public class FractionalPartFinder {
	public Phrase phraseToIgnore;
	
	private ArrayList<Phrase> possibleFractionals;
	
	public FractionalPartFinder(ArrayList<Phrase> possibleFractionals) {
		this.possibleFractionals = possibleFractionals;
		phraseToIgnore = null;
	}
	
	public Phrase find() {
		for (Phrase phrase: possibleFractionals) {
			if (!phrase.equals(phraseToIgnore) && isFractionalPart(phrase)) {
				return phrase;
			}
		}
		return null;
	}
	
	private boolean isFractionalPart(Phrase phrase) {
		String text = phrase.make();
		return text.contains(".");
	}
}
