package auto_correct;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AutoCorrectResponse {
	JSONObject json;
	HashMap<String, String> replacementsMap;
	
	public AutoCorrectResponse(JSONObject json) {
		this.json = json;
		replacementsMap = new HashMap<String, String>();
	}
	
	public HashMap<String, String> getReplacements() {
		try {
			JSONArray wordsToReplace = json.getJSONArray("flaggedTokens");
			for (int i = 0; i < wordsToReplace.length(); i++) {
				JSONObject wordData = wordsToReplace.getJSONObject(i);
				String originalWord = wordData.getString("token");
				JSONArray suggestedWords = wordData.getJSONArray("suggestions");
				String newWord = getHighestScoringWord(suggestedWords);
				replacementsMap.put(originalWord, newWord);
			}	
		}
		catch (JSONException e) {}
		
		return replacementsMap;
	}
	
	private String getHighestScoringWord(JSONArray suggestedWords) {
		String word = null;
		int highestScore = -1;
		for (int i = 0; i < suggestedWords.length(); i++) {
			try {
				JSONObject suggestionData = suggestedWords.getJSONObject(i);
				int score = suggestionData.getInt("score");
				String suggestedWord = suggestionData.getString("suggestion");
				if (score == -1) {
					highestScore = score;
					word = suggestionData.getString("suggestion");
				}
				else if (score > highestScore) {
					word = suggestedWord;
				}
			}
			catch(JSONException e) {}
			
		}
		return word;
	}
	
}
