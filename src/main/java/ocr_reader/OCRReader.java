package ocr_reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OCRReader {
	public String imageUrl;
	
	private static final int LOCATION_THRESHOLD = 10;
	private Map<Integer, PurchasedItem> locationToWordMap;
	private ArrayList<Word> floatList;
	private Map<Integer, ArrayList<String>> locationToFloatMap;
	private ArrayList<LocalFloat> filteredFloatList;
	private JSONObject json;
	
	public OCRReader(String imageUrl) {
		this.imageUrl = imageUrl;
		locationToWordMap = new HashMap<Integer, PurchasedItem>();
		locationToFloatMap = new HashMap<Integer, ArrayList<String>>();
		floatList = new ArrayList<Word>();
		filteredFloatList = new ArrayList<LocalFloat>();
		json = null;
	}
	
	public OCRResult read() throws Exception {
		OCRReaderRequest request = new OCRReaderRequest(imageUrl);
		json = request.sendReadRequest();
		if (json == null) {
			// TODO specify it
			throw new Exception();
		}
		// TODO this should return a list of WordCluster (to be created).
		OCRResult result = makeResult();
		return result;
	}
	
	private OCRResult makeResult() {
		gatherWords();
		gatherFloats();
		filterFloats();
		combineWordsAndFloats();
		for (Entry<Integer, PurchasedItem> entry : locationToWordMap.entrySet()) {
			System.out.println(entry.getKey() + " :: " + entry.getValue().name + " :: " + entry.getValue().cost);
		}
		System.out.println(locationToWordMap.toString());
		return new OCRResult();
	}
	
	private void gatherWords() {
		JSONArray lines = getLines();
		for (int i = 0; i < lines.length(); i++) {
			try {
				int sum = 0;
				JSONObject lineDict = lines.getJSONObject(i);
				JSONArray jsonWords = lineDict.getJSONArray("Words");
				String itemName = ""; 
				for (int j = 0; j < jsonWords.length(); j++) {
					JSONObject jsonText = jsonWords.getJSONObject(j);
					int top = jsonText.getInt("Top");
					String text = jsonText.getString("WordText");
					Word word = new Word(text, top);
					itemName += word.text + " ";
					if (word.isPotentialFloat()) {
						floatList.add(word);
						continue;
					}
					sum += top;
				}
				PurchasedItem item = new PurchasedItem(itemName.trim());
				if (!item.isValid()) {
					continue;
				}
				int avgHeight = (int) (sum / jsonWords.length());
				locationToWordMap.put(avgHeight, item);
			} catch (JSONException e) {
			}
		}
	}
	
	private JSONArray getLines() {
		try {
			return json.getJSONArray("ParsedResults").getJSONObject(0).getJSONObject("TextOverlay").getJSONArray("Lines");
		} catch (JSONException e) {
			return new JSONArray();
		}
	}
	
	private void gatherFloats() {
		for (Word word : floatList) {
			int floor = word.location - LOCATION_THRESHOLD;
			int ceiling = word.location + LOCATION_THRESHOLD;
			boolean brokeEarly = false;
			for (int i = floor; i <= ceiling; i++) {
				if (locationToFloatMap.containsKey(i)) {
					locationToFloatMap.get(i).add(word.text);
					brokeEarly = true;
					break;
				}
			}
			if (!brokeEarly) {
				ArrayList<String> floatList = new ArrayList<String>();
				floatList.add(word.text);
				locationToFloatMap.put(word.location, floatList);
			}
		}
	}
	
	private void filterFloats() {
		for (Entry<Integer, ArrayList<String>> entry : locationToFloatMap.entrySet()) {
		    int location = entry.getKey();
		    ArrayList<String> floatChunks = entry.getValue();
		    RawFloat raw = new RawFloat(floatChunks);
		    float myFloat = raw.makeFloat();
		    if (myFloat != -1) {
		    	LocalFloat local = new LocalFloat(myFloat, location);
		    	filteredFloatList.add(local);
		    }
		}
	}
	
	private void combineWordsAndFloats() {
		for (LocalFloat myFloat : filteredFloatList) {
			boolean doBreak = false;
			for (int i = 0; i < 5; i++) {
				int extraThreshold = i * 10;
				System.out.println("========================================");
				System.out.println(myFloat.location);
				System.out.println(myFloat.value);
				int upperBound = myFloat.location + LOCATION_THRESHOLD + extraThreshold;
				int lowerBound = myFloat.location - LOCATION_THRESHOLD - extraThreshold;
				for (int j = lowerBound; j <= upperBound; j++) {
					if (locationToWordMap.containsKey(j)) {
						locationToWordMap.get(j).cost = myFloat.value;
						doBreak = true;
						break;
					}
				}
				if (doBreak) {
					break;
				}
			}
		}
	}
}

class RawFloat {
	private ArrayList<String> floatChunks;
	private String realNumber;
	private String decimal;
	
	public RawFloat(ArrayList<String> floatChunks) {
		this.floatChunks = floatChunks;
		realNumber = null;
		decimal = null;
	}
	
	public float makeFloat() {
		makeRealNumberAndDecimal();
		String str = makeFloatString();
		if (str == "") {
			return -1;
		}
		float madeFloat;
		try {
			madeFloat = Float.parseFloat(str);
		}
		catch(Exception e) {
			madeFloat = -1;
		}
		return madeFloat;
	}
	
	private void makeRealNumberAndDecimal() {
		for (String word : floatChunks) {
			if (realNumber == null && word.startsWith("$")) {
				realNumber = word;
			}
			else if (decimal == null || word.contains(".")) {
				Word wordObject = new Word(word);
				if (wordObject.isPotentialFloat()) {
					String strFloat = wordObject.toFloatString();
					if (strFloat != null) {
						decimal = strFloat;
					}
				}
			}
		}
	}
	
	private String makeFloatString() {
		String totalFloat = null;
		if (realNumber != null && decimal != null) {
			Word localRealNumber = new Word(realNumber);
			String filteredReal = localRealNumber.onlyDigits();
			String filteredDecimal = decimal.replace(".", "");
			totalFloat = filteredReal + "." + filteredDecimal.substring(0, 2);
		}
		else if(realNumber != null) {
			String filteredReal = realNumber.replace("$", "");
			totalFloat = filteredReal;
		}
		else if (decimal != null) {
			if (!decimal.contains(".")) {
				decimal = "0." + decimal;
			}
			totalFloat = decimal;
		}
		return totalFloat;
	}
	
	
}

class PurchasedItem {
	public String name;
	public float cost;
	
	public PurchasedItem(String name) {
		this.name = name;
		this.cost = 0;
	}
	
	public boolean isValid() {
		float numValidChars = 0;
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			if (Character.isLetter(c)) {
				numValidChars ++;
			}
		}
		return numValidChars / (float) name.length() > 0.5;
	}
}

class Word {
	
	public String text;
	public int location;
	
	public Word(String text) {
		this.text = text;
		this.location = 0;
	}
	
	public Word(String text, int location) { 
		this.text = text;
		this.location = location;
	}
	
	public boolean isPotentialFloat() {
		return text.contains("$") || text.contains(".") || isAllNumbers();
	}
	
	private boolean isAllNumbers() {
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}
	
	public String toFloatString() {
		String str = "";
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (Character.isDigit(c) || c == '.') {
				str += c;
			}
		}
		if (str == "") {
			return null;
		}
		return str;
	}
	
	public String onlyDigits() {
		String str = "";
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (Character.isDigit(c)) {
				str += c;
			}
		}
		return str;
	}
}

class LocalFloat {
	public float value;
	public int location;
	
	public LocalFloat(float f, int location) {
		value = f;
		this.location = location;
	}
}

