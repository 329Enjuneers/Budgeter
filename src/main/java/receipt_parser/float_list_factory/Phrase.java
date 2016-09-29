package receipt_parser.float_list_factory;

import java.util.ArrayList;

public class Phrase {
	private ArrayList<String> words; 
	
	public Phrase(ArrayList<String> words) {
		this.words = words;
	}
	
	public String make() {
		String phrase = "";
		for(String word : words) {
			phrase += (word + " ");
		}
		return phrase.trim();
	}
	
	public boolean isFloat() {
		String text = make();
		return text.contains("$") || text.contains(".") || isAllNumbers() || majorityIsNumbers();
	}
	
	public String makeStringifiedFloat() {
		if (!isFloat()) {
			return null;
		}
		
		String str = getDigitsAndPeriods();
		if (str == "") {
			return null;
		}
		return str;
	}

	private String getDigitsAndPeriods() {
		String str = "";
		String text = make();
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (Character.isDigit(c) || c == '.') {
				str += c;
			}
		}
		return str;
	}
	
	public String onlyDigits() {
		return getDigits();
	}
	
	private String getDigits() {
		String str = "";
		String text = make();
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (Character.isDigit(c)) {
				str += c;
			}
		}
		return str;
	}
	
	private boolean isAllNumbers() {
		String text = make();
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean majorityIsNumbers() {
		float numbersCount = 0;
		String text = make();
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (Character.isDigit(c)) {
				numbersCount ++;
			}
		}
		return (float) (numbersCount / (float) text.length()) > 0.5;
	}
}
