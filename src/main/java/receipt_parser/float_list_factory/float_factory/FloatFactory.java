package receipt_parser.float_list_factory.float_factory;

import java.util.ArrayList;

import exceptions.NotAFloatException;
import receipt_parser.MyFloat;
import receipt_parser.float_list_factory.Phrase;

public class FloatFactory {
	private int location;
	private ArrayList<Phrase> localFloat;
	private Phrase integer;
	private Phrase fractional;
	
	public FloatFactory(int location, ArrayList<Phrase> givenFloat) {
		this.location = location;
		this.localFloat = givenFloat;
		integer = null;
		fractional = null;
	}
	
	public MyFloat make() throws NotAFloatException {
		float f = makeFloat();
		if (f == -1) {
			throw new NotAFloatException();
		}
		return new MyFloat(location, f);
	}
	
	private float makeFloat() {
		makeIntegerPart();
		makeFractionalPart();
		String floatString = makeFloatString();
		return getLiteralFloat(floatString);
	}
	
	private void makeIntegerPart() {
		IntegerPartFinder finder = new IntegerPartFinder(localFloat);
		integer = finder.find();
	}
	
	
	private void makeFractionalPart() {
		FractionalPartFinder finder = new FractionalPartFinder(localFloat);
		finder.phraseToIgnore = integer;
		fractional = finder.find();
	}
	
	private String makeFloatString() {
		FloatJoiner joiner = new FloatJoiner(integer, fractional);
		return joiner.join();
	}
	
	private float getLiteralFloat(String floatString) {
		StringToFloatConverter converter = new StringToFloatConverter(floatString);
		return converter.convert();
	}
	
}
