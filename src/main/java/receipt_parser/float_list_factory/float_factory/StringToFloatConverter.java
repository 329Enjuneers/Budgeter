package receipt_parser.float_list_factory.float_factory;

public class StringToFloatConverter {
	
	public String floatToConvert;
	
	public static final float INVALID_FLOAT = -1;
	
	public StringToFloatConverter(String floatToConvert) {
		this.floatToConvert = floatToConvert;
	}
	
	public float convert() {
		if (!stringIsValid()) {
			return INVALID_FLOAT;
		}
		return makeFloat();
	}
	
	private boolean stringIsValid() {
		return floatToConvert != "" && floatToConvert != null;
	}
	
	private float makeFloat() {
		try {
			return Float.parseFloat(floatToConvert);
		}
		catch(Exception e) {
			return INVALID_FLOAT;
		}
	}
}
