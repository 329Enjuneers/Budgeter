package receipt_parser.float_list_factory.float_factory;

import receipt_parser.Phrase;

public class FloatJoiner {
	
	private Phrase integer;
	private Phrase fractional;
	
	public FloatJoiner(Phrase integer, Phrase fractional) {
		this.integer = integer;
		this.fractional = fractional;
	}
	
	public String join() {
		if (integerAndFractionalAreValid()) {
			return integerAndFractional();
		}
		else if(integerOnlyValid()) {
			return integerOnly();
		}
		else if (fractionalOnlyValid()) {
			return fractionalOnly();
		}
		return null;
	}
	
	private boolean integerAndFractionalAreValid() {
		return integer != null && fractional != null;
	}
	
	private String integerAndFractional() {
		String prettyIntegerPart = integer.onlyDigits();
		String prettyDecimalPart = fractional.onlyDigits();
		return prettyIntegerPart + "." + prettyDecimalPart.substring(0, 2);
	}
	
	private boolean integerOnlyValid() {
		return integer != null;
	}
	
	private String integerOnly() {
		return integer.make().replace("$", "");
	}
	
	private boolean fractionalOnlyValid() {
		return fractional != null;
	}
	
	private String fractionalOnly() {
		String text = fractional.makeStringifiedFloat();
		if (!text.contains(".")) {
			text = "0." + text;
		}
		return text;
	}
}
