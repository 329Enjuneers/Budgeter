package ocr_reader;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Line {
	public JSONObject json;
	HashMap<String, Object> dataChunk;
	
	public Line(JSONObject json) {
		this.json = json;
		dataChunk = new HashMap<String, Object>();
	}
	
	public HashMap<String, Object> getContent() throws JSONException {
		dataChunk.clear();
		JSONArray jsonWords = json.getJSONArray("Words");
		addWordsFrom(jsonWords);
		addLocationFrom(jsonWords);
		return dataChunk;
	}
	
	private void addWordsFrom(JSONArray jsonWords) {
		ArrayList<String> words = getWords(jsonWords);
		dataChunk.put("words", words);
	}
	
	private void addLocationFrom(JSONArray jsonWords) {
		int location = getLocation(jsonWords);
		dataChunk.put("location", location);
	}
	
	private ArrayList<String> getWords(JSONArray jsonWords) {
		ArrayList<String> words = new ArrayList<String>();
		for (int i = 0; i < jsonWords.length(); i++) {
			String word;
			try {
				word = jsonWords.getJSONObject(i).getString("WordText");
			} catch (JSONException e) {
				continue;
			}
			words.add(word);
		}
		return words;
	}
	
	private int getLocation(JSONArray jsonWords) {
		int sum = 0;
		for (int i = 0; i < jsonWords.length(); i++) {
			int location;
			try {
				location = jsonWords.getJSONObject(i).getInt("Top");
			} catch (JSONException e) {
				location = 0;
			}
			sum += location;
		}
		int averageLocation = sum / jsonWords.length();
		return averageLocation;
	}
}
