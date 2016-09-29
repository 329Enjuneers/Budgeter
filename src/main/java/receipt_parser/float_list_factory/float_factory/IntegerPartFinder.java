package receipt_parser.float_list_factory.float_factory;

import java.util.ArrayList;

import receipt_parser.float_list_factory.Phrase;

public class IntegerPartFinder {
	private ArrayList<Phrase> possibleIntegers;
	
	public IntegerPartFinder(ArrayList<Phrase> possibleIntegers) {
		this.possibleIntegers = possibleIntegers;
	}
	
	public Phrase find() {
		for (Phrase phrase: possibleIntegers) {
			if (isIntegerPart(phrase)) {
				return phrase;
			}
		}
		return null;
	}
	
	private boolean isIntegerPart(Phrase phrase) {
		String text = phrase.make();
		return text.startsWith("$");
	}
}
